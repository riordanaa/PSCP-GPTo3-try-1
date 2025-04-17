package es.urjc.etsii.grafo.PSSC.model;

import es.urjc.etsii.grafo.solution.SolutionValidator;
import es.urjc.etsii.grafo.solution.ValidationResult;

/**
 * Validate that a solution is valid for the PSSC problem.
 * Validation is always run after the algorithms executes, and can be run in certain algorithm stages to verify
 * that the current solution is valid.
 */
public class PSSCSolutionValidator extends SolutionValidator<PSSCSolution, PSSCInstance> {

    @Override
    public ValidationResult validate(PSSCSolution solution) {

        var validationResult = ValidationResult.ok();
        if(!solution.isCovered()){
            validationResult = ValidationResult.fail("Current coverage (%s) < required coverage (%s)".formatted(solution.coverage(), PSSCSolution.MIN_COVERAGE));
        }

        return validationResult;
    }
}
