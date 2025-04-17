package es.urjc.etsii.grafo.PSSC.constructives;

import es.urjc.etsii.grafo.PSSC.model.PSSCInstance;
import es.urjc.etsii.grafo.PSSC.model.PSSCSolution;
import es.urjc.etsii.grafo.create.Constructive;
import es.urjc.etsii.grafo.util.random.RandomManager;
import es.urjc.etsii.grafo.util.collections.BitSet;

/**
 * Greedy constructive for the Partial Set Covering Problem (PSCP).
 * <p>
 * Two phases:
 * <ol>
 *     <li><strong>Greedy add</strong> – iteratively insert the set that covers the
 *         maximum number of <em>currently uncovered</em> points until 90 % of the
 *         universe is covered.</li>
 *     <li><strong>Redundancy removal</strong> – once feasible, scan the selected
 *         sets and drop any whose removal keeps the coverage ≥ 90 %.</li>
 * </ol>
 * <p>
 * The implementation keeps an <code>int[] coverCount</code> so coverage checks are
 * incremental.  All random choices use {@link RandomManager} for reproducibility.
 * <p>
 * <strong>Important implementation note</strong>: The custom {@code BitSet}
 * provided by Mork throws an {@code IndexOutOfBoundsException} if
 * {@code nextSetBit(idx)} is called with {@code idx == capacity}.  Therefore we
 * guard every increment and stop the iteration once we reach
 * {@code nPoints‑1}/{@code nSets‑1}.
 */
public class PSSCGreedyConstructive extends Constructive<PSSCSolution, PSSCInstance> {

    @Override
    public PSSCSolution construct(PSSCSolution solution) {
        var rnd = RandomManager.getRandom();
        PSSCInstance instance = solution.getInstance();

        final int nPoints = instance.getnPoints();
        final int nSets   = instance.getnSets();
        final int minCoveredRequired = (int) Math.ceil(PSSCSolution.MIN_COVERAGE * nPoints);

        // Coverage bookkeeping ---------------------------------------------------------
        int[] coverCount = new int[nPoints]; // how many selected sets cover each point
        int   covered    = 0;               // number of currently covered points

        BitSet chosen = solution.getChosenSets();

        // --------------------------------------------------------------------------
        // 1. Greedy phase – add sets until coverage goal reached
        // --------------------------------------------------------------------------
        while (covered < minCoveredRequired) {
            int bestSet  = -1;
            int bestGain = -1;

            for (int s = 0; s < nSets; s++) {
                if (chosen.contains(s)) continue;

                int gain = 0;
                BitSet covers = instance.getCoveredPoints(s);

                int p = covers.nextSetBit(0);
                while (p >= 0 && p < nPoints) {
                    if (coverCount[p] == 0) gain++;
                    if (p == nPoints - 1) break;          // avoid capacity fault
                    p = covers.nextSetBit(p + 1);
                }

                if (gain > bestGain) {
                    bestGain = gain;
                    bestSet  = s;
                } else if (gain == bestGain && gain > 0 && rnd.nextBoolean()) {
                    bestSet = s; // random tie‑break
                }
            }

            // Degenerate safeguard: if no positive gain, pick first unselected set
            if (bestSet == -1) {
                for (int s = 0; s < nSets; s++) {
                    if (!chosen.contains(s)) { bestSet = s; break; }
                }
            }

            // Add set and update counts
            solution.addSet(bestSet);
            BitSet covers = instance.getCoveredPoints(bestSet);

            int p = covers.nextSetBit(0);
            while (p >= 0 && p < nPoints) {
                if (coverCount[p] == 0) covered++;
                coverCount[p]++;
                if (p == nPoints - 1) break;
                p = covers.nextSetBit(p + 1);
            }
        }

        // --------------------------------------------------------------------------
        // 2. Redundancy elimination – drop sets while feasible
        // --------------------------------------------------------------------------
        int s = chosen.nextSetBit(0);
        while (s >= 0 && s < nSets) {
            BitSet covers = instance.getCoveredPoints(s);

            int wouldLose = 0;
            int p = covers.nextSetBit(0);
            while (p >= 0 && p < nPoints) {
                if (coverCount[p] == 1) {
                    wouldLose++;
                    if (covered - wouldLose < minCoveredRequired) break; // early exit
                }
                if (p == nPoints - 1) break;
                p = covers.nextSetBit(p + 1);
            }

            if (covered - wouldLose >= minCoveredRequired) {
                // Safe to remove this set
                solution.removeSet(s);

                p = covers.nextSetBit(0);
                while (p >= 0 && p < nPoints) {
                    coverCount[p]--;
                    if (coverCount[p] == 0) covered--;
                    if (p == nPoints - 1) break;
                    p = covers.nextSetBit(p + 1);
                }
            }

            if (s == nSets - 1) break;                      // avoid capacity fault
            s = chosen.nextSetBit(s + 1);
        }

        solution.notifyUpdate(); // refresh objective & caches
        return solution;
    }
}
