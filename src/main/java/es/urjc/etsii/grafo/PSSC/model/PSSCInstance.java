package es.urjc.etsii.grafo.PSSC.model;

import es.urjc.etsii.grafo.io.Instance;
import es.urjc.etsii.grafo.util.collections.BitSet;

public class PSSCInstance extends Instance {

    /**
     * Number of sets in the instance
     */
    private final int nSets;

    /**
     * Number of points in the instance
     */
    private final int nPoints;

    /**
     * For each set, which points does it cover
     */
    private final BitSet[] coverage;

    /**
     * Which sets should not be added to the solution
     * because they either do not cover any point,
     * or there is always a better set that includes them.
     */
    private final BitSet uselessSets;

    /**
     * BitSet which contains the sets that are needed if we must cover all points.
     * In other words, if a point is only covered by one set, that set will be included in the returned BitSet.
     * Note that in this problem, because the coverage is < 1, we do not need to cover all points, and this sets
     * do not necessarily have to be included in the solution.
     */
    private final BitSet supportSets;


    public PSSCInstance(int nSets, int nPoints, BitSet[] coverage, String name){
        super(name);
        this.nSets = nSets;
        this.nPoints = nPoints;
        this.coverage = coverage;
        this.uselessSets = new BitSet(nSets);

        for (int i = 0; i < nSets; i++) {
            var set = coverage[i];
            if (set.isEmpty()) {
                uselessSets.add(i);
            } else {
                for (int j = 0; j < nSets; j++) {
                    if(i != j && set.containsAll(coverage[j])) {
                        uselessSets.add(j);
                    }
                }
            }
        }

        supportSets = new BitSet(nSets);
        for (int i = 0; i < this.nPoints; i++) {
            BitSet coveredBy = new BitSet(nSets);
            for (int j = 0; j < this.nSets; j++) {
                if (coverage[j].get(i)) {
                    coveredBy.add(j);
                }
            }
            if(coveredBy.size() == 1){
                supportSets.add(coveredBy.nextSetBit(0));
            }
        }


        setProperty("nSets", nSets);
        setProperty("nPoints", nPoints);
        setProperty("nUselessSets", uselessSets.size());
        setProperty("nSupport", supportSets.size());
    }


    /**
     * How should instances be ordered, when listing and solving them.
     * If not implemented, defaults to lexicographic sort by instance name
     * @param other the other instance to be compared against this one
     * @return comparison result
     */
    @Override
    public int compareTo(Instance other) {
        var otherInstance = (PSSCInstance) other;
        return Integer.compare(this.nSets, otherInstance.nSets);
    }

    /**
     * Returns the set of points covered by each sets
     * @return set of points covered by each sets
     */
    public BitSet[] getCoverage() {
        return coverage;
    }

    /**
     * Returns the number of sets in the instance
     * @return number of sets
     */
    public int getnSets() {
        return nSets;
    }

    /**
     * Returns the number of points in the instance
     * @return number of points
     */
    public int getnPoints() {
        return nPoints;
    }

    /**
     * Returns the set of points covered by a set
     * @param set set id, 0 indexed.
     * @return set of points covered by the set
     */
    public BitSet getCoveredPoints(int set) {
        return coverage[set];
    }

    /**
     * Returns a collection of sets that should not be added to the solution,
     * as there will always be better options.
     * @return collection of useless sets, as a BitSet
     */
    public BitSet getUselessSets() {
        return uselessSets;
    }
}
