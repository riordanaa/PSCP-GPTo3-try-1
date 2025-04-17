package es.urjc.etsii.grafo.PSSC.experiments;

import es.urjc.etsii.grafo.PSSC.algorithms.PSSCVNSRunner;
import es.urjc.etsii.grafo.PSSC.model.PSSCInstance;
import es.urjc.etsii.grafo.PSSC.model.PSSCSolution;
import es.urjc.etsii.grafo.algorithms.Algorithm;
import es.urjc.etsii.grafo.experiment.AbstractExperiment;

import java.util.ArrayList;
import java.util.List;

/**
 * Experiment that benchmarks our VNS implementation.
 */
public class ConstructiveExperiment
        extends AbstractExperiment<PSSCSolution, PSSCInstance> {

    @Override
    public List<Algorithm<PSSCSolution, PSSCInstance>> getAlgorithms() {

        var algorithms = new ArrayList<Algorithm<PSSCSolution, PSSCInstance>>();

        // ---- Variable‑Neighborhood‑Search (Drop → Swap → Add) ----
        algorithms.add(new PSSCVNSRunner("VNS-Plain"));

        // (Optional) re‑enable other algorithms here if you wish to compare:
        // algorithms.add(new SimpleAlgorithm<>("Random", new PSSCRandomConstructive()));
        // algorithms.add(new SimpleAlgorithm<>("Greedy", new PSSCGreedyConstructive()));

        return algorithms;
    }
}
