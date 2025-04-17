package es.urjc.etsii.grafo.PSSC.constructives.grasp;

import es.urjc.etsii.grafo.PSSC.model.PSSCBaseMove;
import es.urjc.etsii.grafo.PSSC.model.PSSCInstance;
import es.urjc.etsii.grafo.PSSC.model.PSSCSolution;
import es.urjc.etsii.grafo.create.grasp.GRASPListManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Candidate list manager for the GRASP constructive method.
 */
public class PSSCListManager extends GRASPListManager<PSSCListManager.PSSCGRASPMove, PSSCSolution, PSSCInstance> {

    /**
     * Generate initial candidate list.
     * @param solution Current solution
     * @return an UNSORTED candidate list
     */
    @Override
    public List<PSSCGRASPMove> buildInitialCandidateList(PSSCSolution solution) {
        var list = new ArrayList<PSSCGRASPMove>();

        // TODO Generate a list with all valid movements for current solution
        // GRASP constructive method ends when CL is empty

        return list;
    }

    /**
     * Update candidate list after each movement. The list will be sorted by the constructor.
     * @param solution Current solution, move has been already applied
     * @param move     Chosen move
     * @param index index of the chosen move in the candidate list
     * @param candidateList original candidate list
     * @return an UNSORTED candidate list, where the best candidate is on the first position and the worst in the last
     */
    @Override
    public List<PSSCGRASPMove> updateCandidateList(PSSCSolution solution, PSSCGRASPMove move, List<PSSCGRASPMove> candidateList, int index) {
        // List can be partially updated / modified if required for performance
        // Recalculating from scratch is OK and can be optimized later if necessary
        // Do NOT prematurely optimize, split the code in small methods and profile first to see if this is necessary
        return buildInitialCandidateList(solution);
    }

    public static class PSSCGRASPMove extends PSSCBaseMove {
        public PSSCGRASPMove(PSSCSolution solution) {
            super(solution);
        }

        @Override
        protected PSSCSolution _execute(PSSCSolution solution) {
            // TODO Apply changes to solution when this method is called
            // Return the modified solutions.
            // It is up to the implementation to decide if the original solution is modified
            // in place or a new one is created by cloning the original solution and then applying the changes.
            // NOTE: Calling this method multiple times with a solution and its clones must return the same result
            throw new UnsupportedOperationException("_execute() in PSSCListManager not implemented yet");
        }

        public double getScoreChange() {
            // TODO How much does o.f. value change if we apply this movement?
            throw new UnsupportedOperationException("getValue() in PSSCListManager not implemented yet");
        }

        @Override
        public String toString() {
            // TODO Use IDE to generate this method after all properties are defined
            throw new UnsupportedOperationException("toString() in PSSC not implemented yet");
        }

        @Override
        public boolean equals(Object o) {
            // TODO Use IDE to generate this method after all properties are defined
            throw new UnsupportedOperationException("equals() in PSSC not implemented yet");
        }

        @Override
        public int hashCode() {
            // TODO Use IDE to generate this method after all properties are defined
            throw new UnsupportedOperationException("hashCode() in PSSC not implemented yet");
        }
    }
}
