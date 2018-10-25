
package ppc;

import java.util.*;
import representations.*;

/**
 * this heuristic counts the occurence of the variable in constraints 
 * 
 */
public class ConstraintMaxHeuristic extends AbstractHeuristic {
    
    /**
     * Build the heuristic with a list of constraints
     * @param constraints the list of constraints
     */
    public ConstraintMaxHeuristic(List<Constraint> constraints) {
        super(constraints);
    }
    
    /**
     * Calculates the value of the variable or/and this domain
     * @param var the variable
     * @param domaine the domain of the variable possibly filtered
     * @return the value of the variable
     */
    @Override
    public int heuristicValue(Variable var, Set<String> domaine) {
        int cpt = 0;
        for(Constraint c : this.constraints) {
            // for all constraints
            if (c.getScope().contains(var)) {
                // if variable is in the constraint's scope
                cpt += 1;
            }
        }
        return cpt;
    }
    
    /**
     * String name of the heuristic
     * @return the heuristic's name
     */
    @Override
    public String toString() {
        return "Constraint max heuristic";
    }
}
