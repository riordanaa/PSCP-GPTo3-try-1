package es.urjc.etsii.grafo.PSSC;

import es.urjc.etsii.grafo.PSSC.model.*;
import es.urjc.etsii.grafo.solution.Objective;
import es.urjc.etsii.grafo.solver.Mork;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static final Objective<PSSCBaseMove, PSSCSolution, PSSCInstance> OBJECTIVE =
            Objective.ofMinimizing("sets", PSSCSolution::getScore, PSSCBaseMove::getScoreChange);


    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("help")) {
            System.out.println("Usage to validate a file: java -jar PSSC.jar validate path/to/instance.txt path/to/solution.txt");
            System.out.println("Usage to start solver: java -jar PSSC.jar");
        } else if (args.length > 0 && args[0].equals("validate")) {
            if(args.length != 3) {
                System.out.println("Wrong usage, do: java -jar PSSC.jar validate path/to/instance.txt path/to/solution.txt");
                return;
            }
            validate(args[1], args[2]);
        } else {
            // start solver engine
            Mork.start(args, OBJECTIVE);
        }
    }

    private static void validate(String instancePath, String solutionPath) {
        var validator = new PSSCSolutionValidator();
        var instanceIO = new PSSCInstanceImporter();
        var instance = instanceIO.importInstance(instancePath);
        var solution = new PSSCSolution(instance);
        try (Scanner sc = new Scanner(Path.of(solutionPath))){
            int nSets = sc.nextInt();
            for (int i = 0; i < nSets; i++) {
                int set = sc.nextInt();
                solution.addSet(set);
            }
        } catch (IOException e){
            System.out.println("Error reading solution file: " + e.getMessage());
            return;
        }
        var validationResult = validator.validate(solution);
        if (validationResult.isValid()) {
            System.out.println("Solution is feasible, current coverage: " + solution.coverage());
        } else {
            System.out.println("Solution is NOT feasible, reasons: \n" + validationResult.getReasonFailed());
        }
    }
}
