package es.urjc.etsii.grafo.PSSC.model.neigh;

import es.urjc.etsii.grafo.PSSC.model.PSSCBaseMove;
import es.urjc.etsii.grafo.PSSC.model.PSSCInstance;
import es.urjc.etsii.grafo.PSSC.model.PSSCSolution;
import es.urjc.etsii.grafo.solution.neighborhood.ExploreResult;
import es.urjc.etsii.grafo.solution.neighborhood.Neighborhood;
import es.urjc.etsii.grafo.util.collections.BitSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Swaps one selected set with one unselected set, preserving feasibility.
 */
public class SwapNeighborhood
        extends Neighborhood<SwapNeighborhood.SwapMove, PSSCSolution, PSSCInstance> {

    @Override
    public ExploreResult<SwapMove, PSSCSolution, PSSCInstance> explore(PSSCSolution sol) {

        BitSet covered = new BitSet(sol.getInstance().getnPoints());
        for (int s : sol.getChosenSets()) {
            covered.or(sol.getInstance().getCoveredPoints(s));
        }

        List<SwapMove> moves = new ArrayList<>();

        for (int out : sol.getChosenSets()) {
            BitSet outCover = sol.getInstance().getCoveredPoints(out);

            // Points uniquely covered by 'out'
            BitSet unique = outCover.clone();
            BitSet coveredMinusOut = covered.clone();
            coveredMinusOut.andNot(outCover);
            unique.andNot(coveredMinusOut);
            if (unique.isEmpty()) continue; // Set already redundant

            // Try every unselected set to compensate 'unique'
            for (int in = 0; in < sol.getInstance().getnSets(); in++) {
                if (sol.getChosenSets().contains(in)) continue;

                BitSet inCover = sol.getInstance().getCoveredPoints(in);

                BitSet missing = unique.clone();
                missing.andNot(inCover);
                if (missing.isEmpty()) {              // Feasible swap
                    moves.add(new SwapMove(sol, out, in));
                }
            }
        }
        return ExploreResult.fromList(moves);
    }

    /* ---------- Move ---------- */

    public static class SwapMove extends PSSCBaseMove {

        private final int outSet;
        private final int inSet;

        public SwapMove(PSSCSolution solution, int outSet, int inSet) {
            super(solution);
            this.outSet = outSet;
            this.inSet  = inSet;
        }

        @Override
        protected PSSCSolution _execute(PSSCSolution solution) {
            solution.removeSet(outSet);
            solution.addSet(inSet);
            solution.notifyUpdate();
            return solution;
        }

        @Override public double getScoreChange() { return 0; }

        @Override public String toString() { return "Swap{" + outSet + "→" + inSet + '}'; }

        @Override public boolean equals(Object o) {
            return (o instanceof SwapMove m) && m.outSet == outSet && m.inSet == inSet;
        }

        @Override public int hashCode() { return 31 * outSet + inSet; }
    }
}
