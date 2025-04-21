package es.urjc.etsii.grafo.PSSC.model;

import es.urjc.etsii.grafo.io.InstanceImporter;
import es.urjc.etsii.grafo.util.collections.BitSet;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Spring‑boot bean that loads OR‑Library SCP / PSCP instances.
 *
 * <pre>
 *   m  n                # points (rows), sets (columns)
 *   c1 … cn             # costs (ignored, all 1)
 *   repeat m times:
 *       k_p             # sets covering point p
 *       s1 … s_kp       # 1‑based column indices (may wrap lines)
 * </pre>
 */
@Service
public class PSSCInstanceImporter
        extends InstanceImporter<PSSCInstance> {

    /** Framework‑required method (BufferedReader + filename). */
    @Override
    public PSSCInstance importInstance(BufferedReader reader, String filename)
            throws IOException {

        try (Scanner sc = new Scanner(reader)) {

            /* ---------- 1. header ------------------------------------------ */
            int nPoints = sc.nextInt();   // m (rows)
            int nSets   = sc.nextInt();   // n (columns)

            /* ---------- 2. skip cost vector (unicost instances) ------------ */
            for (int i = 0; i < nSets; i++) sc.nextInt();

            /* ---------- 3. init coverage array ----------------------------- */
            BitSet[] coverage = new BitSet[nSets];
            for (int s = 0; s < nSets; s++) {
                coverage[s] = new BitSet(nPoints);
            }

            /* ---------- 4. read point blocks ------------------------------- */
            for (int p = 0; p < nPoints; p++) {
                if (!sc.hasNextInt()) {
                    throw new IOException("Unexpected EOF at point " + p +
                                          " in instance " + filename);
                }
                int k = sc.nextInt();                // sets covering point p
                for (int h = 0; h < k; h++) {
                    int setIdx = sc.nextInt() - 1;   // 1‑based → 0‑based
                    coverage[setIdx].add(p);
                }
            }

            return new PSSCInstance(nSets, nPoints, coverage, filename);
        }
    }
}
