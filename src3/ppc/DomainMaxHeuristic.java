
package ppc;

import java.util.*;
import representations.*;

/**
 * This heuristic give the size of the domain
 *
 */
public class DomainMaxHeuristic implements HeuristicVariable {

    /**
     * Calculates the size of the variable's domain
     * @param constraints list of constraints
     * @param var the variable
     * @param domaine the domain of the variable possibly filtered
     * @return the value of the variable
     */
    @Override
    public int heuristicValue(List<Constraint> constraints, Variable var, Set<String> domaine) {
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