package es.urjc.etsii.grafo.PSSC.shake;

import es.urjc.etsii.grafo.PSSC.model.*;
import es.urjc.etsii.grafo.PSSC.model.neigh.RemoveNeighborhood;
import es.urjc.etsii.grafo.shake.Shake;
import es.urjc.etsii.grafo.util.random.RandomManager;

import java.util.List;

/**
 * Shake: add 1 random unselected set, then drop any newly redundant sets.
 */
public class ExamplePSSCShake extends Shake<PSSCSolution, PSSCInstance> {

    private final RemoveNeighborhood drop = new RemoveNeighborhood();

    @Override
    public PSSCSolution shake(PSSCSolution solution, int k) {

        var rnd = RandomManager.getRandom();
        PSSCInstance ins = solution.getInstance();

        // 1. Add k random unselected sets (k is small, usually 1)
        for (int i = 0; i < k; i++) {
            int tries = 0;
            int sel;
            do {
                sel = rnd.nextInt(ins.getnSets());
            } while (solution.getChosenSets().contains(sel) && ++tries < 10);

            if (!solution.getChosenSets().contains(sel)) {
                solution.addSet(sel);
            }
        }

        // 2. Drop redundant sets once (first‑improvement style)
        var dropMoves = drop.explore(solution).moves().findFirst();
        while (dropMoves.isPresent()) {
            ((PSSCBaseMove) dropMoves.get()).execute(solution);
            dropMoves = drop.explore(solution).moves().findFirst();
        }
        return solution;
    }
}
