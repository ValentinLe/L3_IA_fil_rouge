
package ppc;

import java.util.*;
import representations.*;

/**
 * interface of heuristic to the backtrack
 * 
 */
public interface Heuristic {
    
    /**
     * Getter on the constraints
     * @return the constraints of the problem
     */
    public List<Constraint> getConstraints();
    
    /**
     * Setter on the constraints
     * @param constraints a list of constraints
     */
    public void setConstraints(List<Constraint> constraints);
    
    /**
     * Calculates the value of the variable or/and this domain
     * @param var the variable
     * @param domaine the domain of the variable possibly filtered
     * @return the value of the variable
     */
    public int heuristicValue(Variable var, Set<String> domaine);

}
