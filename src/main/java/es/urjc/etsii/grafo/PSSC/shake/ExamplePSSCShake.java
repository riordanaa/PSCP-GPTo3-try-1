package es.urjc.etsii.grafo.PSSC.shake;

import es.urjc.etsii.grafo.PSSC.model.PSSCInstance;
import es.urjc.etsii.grafo.PSSC.model.PSSCSolution;
import es.urjc.etsii.grafo.shake.Shake;

public class ExamplePSSCShake extends Shake<PSSCSolution, PSSCInstance> {

    /**
     * Shake / perturbate a feasible solution.
     * Shake methods usually have two steps:
     * 1. Modify solution following a given strategy, may make it infeasible
     * 2. Repair the solution to ensure it is feasible before returning it
     * @param solution Solution to modify
     * @param k shake strength
     * @return feasible solution after shaking (and repairing it, if necessary)
     */
    @Override
    public PSSCSolution shake(PSSCSolution solution, int k) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
