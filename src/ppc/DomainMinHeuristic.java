
package ppc;

import java.util.*;
import representations.*;

/**
 * this heuristic give the opposite of the variable's size domain, the minimum
 * in the list of variables is the opposite of the maximum value
 */
public class DomainMinHeuristic implements Heuristic {

    /**
     * Calculates the value of the variable or/and this domain
     * @param var the variable
     * @param domaine the domain of the variable possibly filtered
     * @return the value of the variable
     * @see DomainMaxHeuristic#heuristicValue
     */
    @Override
    public int heuristicValue(List<Constraint> constraints, Variable var, Set<String> domaine) {
        return -domaine.size();
    }

    /**
     * String name of the heuristic
     * @return the heuristic's name
     */
    @Override
    public String toString() {
        return "Domain min heuristic";
    }
}
