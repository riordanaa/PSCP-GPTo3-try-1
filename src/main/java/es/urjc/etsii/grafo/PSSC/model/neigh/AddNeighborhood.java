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
 * Neighborhood that tries to add a single set.
 * Only generates moves that actually cover at least one currently uncovered point.
 */
public class AddNeighborhood
        extends Neighborhood<AddNeighborhood.AddMove, PSSCSolution, PSSCInstance> {

    @Override
    public ExploreResult<AddMove, PSSCSolution, PSSCInstance> explore(PSSCSolution sol) {

        // Recalculate currently covered points once
        BitSet covered = new BitSet(sol.getInstance().getnPoints());
        for (int s : sol.getChosenSets()) {
            covered.or(sol.getInstance().getCoveredPoints(s));
        }

        List<AddMove> moves = new ArrayList<>();
        int nSets = sol.getInstance().getnSets();

        for (int s = 0; s < nSets; s++) {
            if (sol.getChosenSets().contains(s)) continue;

            BitSet candidate = sol.getInstance().getCoveredPoints(s);
            BitSet gain = candidate.clone();
            gain.andNot(covered);

            if (!gain.isEmpty()) {
                moves.add(new AddMove(sol, s));
            }
        }
        return ExploreResult.fromList(moves);
    }

    /* ---------- Move ---------- */

    public static class AddMove extends PSSCBaseMove {

        private final int setId;

        public AddMove(PSSCSolution solution, int setId) {
            super(solution);
            this.setId = setId;
        }

        @Override
        protected PSSCSolution _execute(PSSCSolution solution) {
            solution.addSet(setId);
            solution.notifyUpdate();
            return solution;
        }

        @Override public double getScoreChange() { return +1; }

        @Override public String toString() { return "Add{" + setId + '}'; }

        @Override public boolean equals(Object o) {
            return (o instanceof AddMove m) && m.setId == this.setId;
        }

        @Override public int hashCode() { return Integer.hashCode(setId); }
    }
}
