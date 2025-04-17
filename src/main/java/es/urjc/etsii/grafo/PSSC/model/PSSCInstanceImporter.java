package es.urjc.etsii.grafo.PSSC.model;

import es.urjc.etsii.grafo.io.InstanceImporter;
import es.urjc.etsii.grafo.util.collections.BitSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class PSSCInstanceImporter extends InstanceImporter<PSSCInstance> {

    /**
     * Load instance from file. This method is called by the framework when a new instance is being loaded.
     * Note that instance load time is never considered in the total execution time.
     * @param reader Input buffer, managed by the framework.
     * @param suggestedName Suggested filename for the instance, can be ignored.
     *                      By default, the suggested filename is built by removing the path and extension info.
     *                      For example, for the path "instances/TSP/TSP-1.txt", the suggestedName would be "TSP-1"
     * @return immutable instance
     * @throws IOException If an error is encountered while the instance is being parsed
     */
    @Override
    public PSSCInstance importInstance(BufferedReader reader, String suggestedName) throws IOException {
        Scanner sc = new Scanner(reader);
        int nSets = sc.nextInt(), nPoints = sc.nextInt();

        int[] weights = new int[nPoints];
        for (int i = 0; i < nPoints; i++) {
            weights[i] = sc.nextInt();
        }

        BitSet[] coverage = new BitSet[nSets];
        for (int i = 0; i < coverage.length; i++) {
            coverage[i] = new BitSet(nPoints);
            int nPointsInSet = sc.nextInt();
            for (int j = 0; j < nPointsInSet; j++) {
                int point = sc.nextInt() - 1; // 1-based to 0-based indexing
                coverage[i].add(point);
            }
        }

        // Call instance constructor when we have parsed all the data
        var instance = new PSSCInstance(nSets, nPoints, coverage, suggestedName);

        // IMPORTANT! Remember that instance data must be immutable from this point
        return instance;
    }
}
