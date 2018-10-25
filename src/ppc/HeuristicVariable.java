
package ppc;

import java.util.*;
import representations.*;

/**
 * interface of heuristic to the backtrack
 * 
 */
public interface HeuristicVariable {
    
    /**
     * Calculates the value of the variable or/and this domain
     * @param constraints constraints of the problem
     * @param var the variable
     * @param domaine the domain of the variable possibly filtered
     * @return the value of the variable
     */
    public int heuristicValue(List<Constraint> constraints, Variable var, Set<String> domaine);

}
