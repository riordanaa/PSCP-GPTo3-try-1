package es.urjc.etsii.grafo.PSSC.model;

import es.urjc.etsii.grafo.io.Instance;
import es.urjc.etsii.grafo.util.collections.BitSet;

public class PSSCInstance extends Instance {

    private final BitSet[] coverage;
    private final int nSets;
    private final int nPoints;

    public PSSCInstance(int nSets, int nPoints, BitSet[] coverage, String name){
        super(name);
        this.nSets = nSets;
        this.nPoints = nPoints;
        this.coverage = coverage;

        setProperty("nSets", nSets);
        setProperty("nPoints", nPoints);
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

    public BitSet[] getCoverage() {
        return coverage;
    }

    public int getnSets() {
        return nSets;
    }

    public int getnPoints() {
        return nPoints;
    }

    public BitSet getCoveredPoints(int set) {
        return coverage[set];
    }
}
