
package ppc;

import java.util.*;
import representations.*;

/**
 * This heuristic give the size of the domain, cette heuristic n'est pas efficace
 * pour le filtrage avec peu de couleur car toutes les variables ont le meme domaine
 * au depart et la variable que prendra l'heuristic ne sera pas forcement avantageuse
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
    public int heuristicValue(Set<Constraint> constraints, Variable var, Set<String> domaine) {
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
