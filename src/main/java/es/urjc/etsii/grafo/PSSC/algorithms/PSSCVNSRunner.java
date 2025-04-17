package es.urjc.etsii.grafo.PSSC.algorithms;

import es.urjc.etsii.grafo.PSSC.constructives.PSSCGreedyConstructive;
import es.urjc.etsii.grafo.PSSC.model.*;
import es.urjc.etsii.grafo.PSSC.model.neigh.*;
import es.urjc.etsii.grafo.PSSC.shake.ExamplePSSCShake;
import es.urjc.etsii.grafo.algorithms.Algorithm;
import es.urjc.etsii.grafo.solution.neighborhood.Neighborhood;

import java.util.List;
import java.util.Optional;

/**
 * Stand‑alone Variable Neighborhood Search (Drop → Swap → Add) compatible with
 * Grafo 0.21 API (ExploreResult exposes moves()).
 */
public class PSSCVNSRunner
        extends Algorithm<PSSCSolution, PSSCInstance> {

    /* configuration */

    private static final List<Neighborhood<?, PSSCSolution, PSSCInstance>> NHOODS = List.of(
            new RemoveNeighborhood(),
            new SwapNeighborhood(),
            new AddNeighborhood()
    );
    private static final int MAX_PLATEAU = 200;

    private final ExamplePSSCShake shake = new ExamplePSSCShake();

    public PSSCVNSRunner(String name) {
        super(name);
    }

    /* main loop */

    @Override
    public PSSCSolution algorithm(PSSCInstance instance) {

        PSSCSolution current = new PSSCGreedyConstructive()
                                   .construct(new PSSCSolution(instance));

        int bestScore = current.getScore();
        int plateau   = 0;
        int k         = 0;                         // neighbourhood index

        while (plateau < MAX_PLATEAU) {

            var neigh   = NHOODS.get(k);
            Optional<?> optMove = neigh.explore(current)
                                       .moves()        // Stream<M>
                                       .findFirst();   // Optional<M>

            if (optMove.isPresent()) {             // first‑improvement
                ((PSSCBaseMove) optMove.get()).execute(current);

                if (current.getScore() < bestScore) {
                    bestScore = current.getScore();
                    k = 0;
                    plateau = 0;
                } else {
                    k = (k + 1) % NHOODS.size();
                    plateau++;
                }

            } else {                               // neighbourhood empty
                k = (k + 1) % NHOODS.size();
                if (k == 0) {                      // full cycle without gains
                    current = shake.shake(current, 1);
                }
                plateau++;
            }
        }
        return current;
    }
}
