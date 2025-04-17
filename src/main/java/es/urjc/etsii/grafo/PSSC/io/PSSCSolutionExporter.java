package es.urjc.etsii.grafo.PSSC.io;

import es.urjc.etsii.grafo.PSSC.model.PSSCInstance;
import es.urjc.etsii.grafo.PSSC.model.PSSCSolution;
import es.urjc.etsii.grafo.annotation.SerializerSource;
import es.urjc.etsii.grafo.executors.WorkUnitResult;
import es.urjc.etsii.grafo.io.serializers.AbstractSolutionSerializerConfig;
import es.urjc.etsii.grafo.io.serializers.SolutionSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Export Covering Set solutions to an easy-to-understand text format.
 */
public class PSSCSolutionExporter extends SolutionSerializer<PSSCSolution, PSSCInstance> {

    protected PSSCSolutionExporter(PSSCSolutionExporterConfig config) {
        super(config);
    }

    /**
     * Export the solution to a text file. Example, if we have 3 nodes in the solution, identified as 7, 10 and 17,
     * the file will contain the following text:
     * 3
     * 7 10 17
     *
     * @param writer Output
     * @param result Solution to export
     * @throws IOException if anything goes wrong while writing
     */
    @Override
    public void export(BufferedWriter writer, WorkUnitResult<PSSCSolution, PSSCInstance> result) throws IOException {
        var solution = result.solution();
        writer.write(solution.getScore());
        writer.newLine();
        for(var set: solution.getChosenSets()){
            writer.write(set + " ");
        }
    }

    @SerializerSource
    @ConfigurationProperties(prefix = "serializers.node-list")
    public static class PSSCSolutionExporterConfig extends AbstractSolutionSerializerConfig {}
}
