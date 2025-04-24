package es.urjc.etsii.grafo.PSSC.model;

import es.urjc.etsii.grafo.solution.Solution;
import es.urjc.etsii.grafo.util.collections.BitSet;

public class PSSCSolution extends Solution<PSSCSolution, PSSCInstance> {

    /**
     * Minimum % coverage required to be considered feasible.
     */
    public static final double MIN_COVERAGE = 0.9;

    /**
     * Minimum number of points that must be covered to be considered feasible.
     */
    final int minCoveredRequired;

    /**
     * Set of sets chosen whose points will be covered
     */
    BitSet chosenSets;

    /**
     * Number of times each point is covered by each set. An uncovered point value is 0.
     */
    int[] coverCount;

    public PSSCSolution(PSSCInstance instance) {
        super(instance);
        this.chosenSets = new BitSet(instance.getnSets());
        this.minCoveredRequired = (int) Math.ceil(instance.getnPoints() * MIN_COVERAGE);
        this.coverCount = new int[instance.getnPoints()];
    }

    public PSSCSolution(PSSCSolution solution) {
        super(solution);
        this.chosenSets = solution.chosenSets.clone();
        this.minCoveredRequired = solution.minCoveredRequired;
        this.coverCount = solution.coverCount.clone();
    }

    /**
     * Returns the number of sets chosen in this solution. We must minimize the number of sets chosen.
     * @return number of sets chosen
     */
    public int getScore() {
        return chosenSets.size();
    }

    /**
     *
     * @return true if the minimum coverage is reached
     */
    public boolean isCovered() {
        return coveredPoints().size() >= minCoveredRequired;
    }

    /**
     * Compute all covered and uncovered points
     * @return set that contains all points covered by the chosen sets
     */
    public BitSet coveredPoints() {
        var instance = getInstance();
        BitSet coveredPoints = new BitSet(instance.getnPoints());
        for (int set : this.chosenSets) {
            coveredPoints.or(instance.getCoveredPoints(set));
        }
        return coveredPoints;
    }

    public double coverage() {
        return coveredPoints().size() / (double) minCoveredRequired;
    }

    /**
     * Picks a set, all its points will now be covered.
     * @param set set to add to the solution
     * @return number of new points covered by this set
     */
    public int addSet(int set) {
        chosenSets.add(set);
        int newCovered = 0;
        for(var point : getInstance().getCoveredPoints(set)){
            if(coverCount[point] == 0){
                newCovered++;
            }
            coverCount[point]++;
        }
        return newCovered;
    }

    /**
     * Removes a set, its points may or may not be covered by other sets.
     * @param set set to remove from the solution
     * @return number of points that are no longer covered after removing this set
     */
    public int removeSet(int set) {
        chosenSets.remove(set);
        int newUncovered = 0;
        for(var point : getInstance().getCoveredPoints(set)){
            if(coverCount[point] == 1){
                newUncovered++;
            }
            coverCount[point]--;
        }
        return newUncovered;
    }

    /**
     * Returns all currently chosen sets
     * @return set of sets chosen in this solution.
     */
    public BitSet getChosenSets() {
        return chosenSets;
    }

    /**
     * Minimum points that must be covered for the solution to be feasible
     * @return
     */
    public int minCoveredRequired(){
        return minCoveredRequired;
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
