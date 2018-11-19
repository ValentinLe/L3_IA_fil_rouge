
package diagnosis;

import java.util.*;
import representations.*;
import ppc.*;

/**
 * Cette classe permet de repérérer une explication de non satisfactions d'un
 * groupe de contraintes
 */
public class Diagnoser {

    private Backtracking backtrack;
    private Map<Variable, Set<String>> variablesWithDomain;

    /**
     * Build an instance of Diagnoser
     * @param backtrack a instance of backtrack pour trouver des solutions a un
     * etat de voiture donnee, elle contiendra l'ensemble des contraintes et des
     * variables
     */
    public Diagnoser(Backtracking backtrack) {
        this.backtrack = backtrack;
        // utilisation de la fonction transformToMap qui permet de recuperer
        // une map des variables ainsi qu'une copie de leur domaine qu'on pourra
        // reduire
        this.variablesWithDomain = this.backtrack.transformToMap(this.backtrack.getVariables());
    }

    /**
     * Ajoute une variable dans la map des variable avec leur domaine copié et
     * l'ajoute également dans l'ensemble de variable de l'instance de Backtracking
     * @param variable la variable à ajouter
     */
    public void add(Variable variable) {
        this.backtrack.addVariable(variable);
        this.variablesWithDomain.put(variable, new HashSet<>(variable.getDomaine()));
    }
    
    /**
     * Supprime une variable dans la map des variables et dans l'ensemble de 
     * variable du backtrack
     * @param variable la variable a supprimée 
     */
    public void remove(Variable variable) {
        this.backtrack.removeVariable(variable);
        this.variablesWithDomain.remove(variable);
    }

    /**
     * Test si un ensemble de choix donnée est une explication avec l'ajout d'un 
     * choix supplémentaire. Les choix sont une explication si il n'existe pas 
     * de solution qui permet de satisfaire toutes les contraintes
     * @param choices l'ensemble de choix
     * @param variable la variable du choix supplémentaire
     * @param value la valeur de la variable du choix supplémentaire
     * @return true si le choix supplémentaire avec les autre choix ne 
     */
    public boolean isExplanation(Map<Variable, String> choices, Variable variable, String value) {
        Set<String> reduceDomain;
        Map<Variable, Set<String>> copyDomain = new HashMap<>(this.variablesWithDomain);
        for (Variable var : choices.keySet()) {
            // for all choice in the set of choices
            // reduce the domain too the value assigned in the choices
            reduceDomain = new HashSet<>();
            reduceDomain.add(choices.get(var));
            copyDomain.put(var, reduceDomain);
        }
        // add the new choix with the domain reduce to their value
        reduceDomain = new HashSet<>();
        reduceDomain.add(value);
        copyDomain.put(variable, reduceDomain);
        // try to find a solution
        Map<Variable, String> solution = this.backtrack.backtrackingFilter(choices, copyDomain);
        // solution = null <=> no solution found
        return solution == null;
    }
    
    public Map<Variable, String> findMinimalInclusionExplanation(Map<Variable, String> choices, Variable variable, String value) {
        Map<Variable, String> explanation = new HashMap<>(choices);
        Map<Variable, String> ePrime;
        for(Variable var : choices.keySet()) {
            ePrime = new HashMap<>(explanation);
            ePrime.remove(var);
            if (this.isExplanation(ePrime, variable, value)) {
                explanation = new HashMap<>(ePrime);
            }
        }
        return explanation;
    }

    public Map<Variable, String> findMinimalCardinalExplanation(Map<Variable, String> choices, Variable variable, String value) {
        Set<Map<Variable, String>> solution = this.findExplanations(choices, variable, value, false);
        Iterator<Map<Variable, String>> iter = solution.iterator();
        if (!solution.isEmpty()) {
            return iter.next();
        } else {
            return new HashMap<>();
        }
    }

    public Set<Map<Variable, String>> findAllExplanations(Map<Variable, String> choices, Variable variable, String value) {
        return this.findExplanations(choices, variable, value, true);
    }

    private Set<Map<Variable, String>> generateSingletons(Map<Variable, String> map) {
        Set<Map<Variable, String>> singletonsMap = new HashSet<>();
        Map<Variable, String> mapTemp;
        for (Variable var : map.keySet()) {
            mapTemp = new HashMap<>();
            mapTemp.put(var, map.get(var));
            singletonsMap.add(mapTemp);
        }
        return singletonsMap;
    }

    private Set<Map<Variable, String>> findExplanations(Map<Variable, String> choices, Variable variable, String value, boolean allExplanation) {
        Set<Map<Variable, String>> finalRes = new HashSet<>();
        Set<Map<Variable, String>> res = new HashSet<>();
        Set<Map<Variable, String>> resPrec;
        Map<Variable, String> copyPart;
        res.add(new HashMap<>());
        
        for (int size = 1; size<choices.size(); size++) {
            resPrec = new HashSet<>(res);
            res = new HashSet<>();
            System.out.println("size : " + size);
            for (Map<Variable, String> part : resPrec) {
                for (Variable var : choices.keySet()) {
                    copyPart = new HashMap<>(part);
                    copyPart.put(var, choices.get(var));
                    System.out.println("copyPart " + copyPart);
                    if ((part.isEmpty() || var.compareTo(Collections.max(part.keySet())) > 0) && this.isExplanation(copyPart, variable, value)) {
                        if (allExplanation) {
                            res.add(copyPart);
                        } else {
                            finalRes = new HashSet<>();
                            finalRes.add(copyPart);
                            return finalRes;
                        }
                    }
                }
            }
            finalRes.addAll(res);
        }
        finalRes.add(new HashMap<>(choices));
        return finalRes;
    }
}
