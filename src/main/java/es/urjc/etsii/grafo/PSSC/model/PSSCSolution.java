package es.urjc.etsii.grafo.PSSC.model;

import es.urjc.etsii.grafo.solution.Solution;
import es.urjc.etsii.grafo.util.DoubleComparator;
import es.urjc.etsii.grafo.util.collections.BitSet;

public class PSSCSolution extends Solution<PSSCSolution, PSSCInstance> {

    public static final double MIN_COVERAGE = 0.9;

    BitSet chosenSets;

    public PSSCSolution(PSSCInstance instance) {
        super(instance);
        this.chosenSets = new BitSet(instance.getnSets());
    }

    public PSSCSolution(PSSCSolution solution) {
        super(solution);
        this.chosenSets = solution.chosenSets.clone();
    }

    public int getScore() {
        return chosenSets.size();
    }

    public boolean isCovered() {
        return DoubleComparator.isGreaterOrEquals(coverage(), MIN_COVERAGE);
    }

    public double coverage() {
        var instance = getInstance();
        BitSet coveredPoints = new BitSet(instance.getnPoints());
        for (int set : this.chosenSets) {
            coveredPoints.or(instance.getCoveredPoints(set));
        }
        return coveredPoints.size() / (double) instance.getnPoints();
    }

    public void addSet(int set) {
        chosenSets.add(set);
    }

    public void removeSet(int set) {
        chosenSets.remove(set);
    }

    public BitSet getChosenSets() {
        return chosenSets;
    }

    @Override
    public PSSCSolution cloneSolution() {
        return new PSSCSolution(this);
    }

    @Override
    public String toString() {
        return chosenSets.size() + ": " + chosenSets;
    }
}
