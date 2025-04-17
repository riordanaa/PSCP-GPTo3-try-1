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
 * DROP neighbourhood: generates a move for every selected set whose removal
 * keeps coverage ≥ 90 %.
 */
public class RemoveNeighborhood
        extends Neighborhood<RemoveNeighborhood.RemoveMove, PSSCSolution, PSSCInstance> {

    @Override
    public ExploreResult<RemoveMove, PSSCSolution, PSSCInstance> explore(PSSCSolution sol) {

        List<RemoveMove> moves = new ArrayList<>();
        PSSCInstance ins = sol.getInstance();

        // Build current covered points once
        BitSet covered = new BitSet(ins.getnPoints());
        for (int s : sol.getChosenSets()) {
            covered.or(ins.getCoveredPoints(s));
        }

        // For each selected set, check if it is redundant
        for (int s : sol.getChosenSets()) {

            BitSet afterRemoval = covered.clone();
            afterRemoval.andNot(ins.getCoveredPoints(s));   // points still covered if we drop 's'

            double coverageAfter = afterRemoval.size() / (double) ins.getnPoints();
            if (coverageAfter >= PSSCSolution.MIN_COVERAGE) {
                moves.add(new RemoveMove(sol, s));
            }
        }
        return ExploreResult.fromList(moves);
    }

    /* ---------- Move ---------- */

    public static class RemoveMove extends PSSCBaseMove {

        private final int setId;

        public RemoveMove(PSSCSolution sol, int setId) {
            super(sol);
            this.setId = setId;
        }

        @Override
        protected PSSCSolution _execute(PSSCSolution solution) {
            solution.removeSet(setId);
            solution.notifyUpdate();
            return solution;
        }

        @Override public double getScoreChange() { return -1; }

        @Override public String toString() { return "Drop{" + setId + '}'; }

        @Override public boolean equals(Object o) {
            return (o instanceof RemoveMove m) && m.setId == this.setId;
        }

        @Override public int hashCode() { return Integer.hashCode(setId); }
    }
}
