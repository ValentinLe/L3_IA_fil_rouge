
package ppc;

import java.util.*;
import representations.*;

/**
 * this heuristic give the opposite value of the ConstraintMaxHeuristic, the
 * max value of a list of variable will be the min value with this heuristic
 */
public class ConstraintMinHeuristic extends AbstractHeuristic {
    
    /**
     * Build the heuristic with a list of constraints
     * @param constraints the list of constraints
     */
    public ConstraintMinHeuristic(List<Constraint> constraints) {
        super(constraints);
    }
    
    /**
     * Calculates the value of the variable or/and this domain
     * @param var the variable
     * @param domaine the domain of the variable possibly filtered
     * @return the value of the variable
     * @see ConstraintMaxHeuristic.heuristicValue
     */
    @Override
    public int heuristicValue(Variable var, Set<String> domaine) {
        Heuristic constraintMax = new ConstraintMaxHeuristic(this.constraints);
        // the min value is the opposite of the max value
        return -constraintMax.heuristicValue(var, domaine);
    }
    
    /**
     * String name of the heuristic
     * @return the heuristic's name
     */
    @Override
    public String toString() {
        return "Constraint min heuristic";
    }
}
