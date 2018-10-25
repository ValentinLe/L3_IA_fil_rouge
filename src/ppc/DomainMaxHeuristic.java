
package ppc;

import java.util.*;
import representations.*;

/**
 * this heuristic give the size of the domain
 * 
 */
public class DomainMaxHeuristic extends AbstractHeuristic {
    
    /**
     * Build the heuristic with a list of constraints
     * @param constraints the list of constraints
     */
    public DomainMaxHeuristic(List<Constraint> constraints) {
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
        return domaine.size();
    }
    
    /**
     * String name of the heuristic
     * @return the heuristic's name
     */
    @Override
    public String toString() {
        return "Domain max heuristic";
    }
}
