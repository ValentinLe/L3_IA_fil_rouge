
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
     * Creee une instances de diagnoser avec un ensemble de variables, un ensemble
     * de contraintes et une heuristic sur les variables pour creer un backtrack
     * @param variables l'ensemble de variables
     * @param constraints l'ensemble de contraintes
     * @param heuristic l'heuristic a utiliser
     */
    public Diagnoser(Set<Variable> variables, Set<Constraint> constraints, HeuristicVariable heuristic) {
        this(new Backtracking(variables, constraints, heuristic));
    }
    
    /**
     * Creee une instance de diagnoser avec un ensemble de variables, un ensemble
     * de contraintes qui va creer un backtrack avec une heuristic sur la domaine
     * minimal par default
     * @param variables l'ensemble de variables
     * @param constraints l'ensemble de contraintes
     */
    public Diagnoser(Set<Variable> variables, Set<Constraint> constraints) {
        this(variables, constraints, new DomainMinHeuristic());
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
        Map<Variable, String> copyChoices = new HashMap<>(choices);
        for (Variable var : copyChoices.keySet()) {
            // for all choice in the set of choices
            // reduce the domain too the value assigned in the choices
            reduceDomain = new HashSet<>();
            reduceDomain.add(copyChoices.get(var));
            copyDomain.put(var, reduceDomain);
        }
        // add the new choix with the domain reduce to their value
        reduceDomain = new HashSet<>();
        reduceDomain.add(value);
        copyDomain.put(variable, reduceDomain);
        // add the new value in the choices copy
        copyChoices.put(variable, value);
        // try to find a solution
        Map<Variable, String> solution = this.backtrack.backtrackingFilter(copyChoices, copyDomain);
        // solution = null <=> no solution found
        return solution == null;
    }
    
    /**
     * trouve l'explication minimale pour l'inclusion d'une variable avec une valeur
     * dans un ensemble de choix deja predefinis definis
     * @param choices l'ensemble de choix predefinis
     * @param variable la variable
     * @param value sa valeur
     * @return l'explication minimale pour l'inclusion
     */
    public Map<Variable, String> findMinimalInclusionExplanation(Map<Variable, String> choices, Variable variable, String value) {
        Map<Variable, String> explanation = new HashMap<>(choices);
        Map<Variable, String> ePrime;
        for(Variable var : choices.keySet()) {
            // pour toutes les variables des choix
            // on fait une copie de l'explication courrente et on enleve
            // la variable de la copie de l'explication
            ePrime = new HashMap<>(explanation);
            ePrime.remove(var);
            if (this.isExplanation(ePrime, variable, value)) {
                // si c'est une explication on remplace la variable d'explication
                // par une 
                explanation = ePrime;
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

    private Set<Map<Variable, String>> findExplanations(Map<Variable, String> choices, Variable variable, String value, boolean allExplanation) {
        Set<Map<Variable, String>> finalRes = new HashSet<>();
        Set<Map<Variable, String>> res = new HashSet<>();
        Set<Map<Variable, String>> resPrec;
        Map<Variable, String> copyPart;
        Map<Variable, String> minimalExplication = new HashMap<>(choices);
        res.add(minimalExplication);

        if (allExplanation) {
            finalRes.add(minimalExplication);
        }

        for (int size = choices.size(); size>0; size--) {
            resPrec = new HashSet<>(res);
            res = new HashSet<>();
            for (Map<Variable, String> part : resPrec) {
                for (Variable var : part.keySet()) {
                    copyPart = new HashMap<>(part);
                    copyPart.remove(var);
                    if (!copyPart.isEmpty() && this.isExplanation(copyPart, variable, value)) {
                        res.add(copyPart);
                        if (!allExplanation && copyPart.size() < minimalExplication.size()) {
                            minimalExplication = copyPart;
                        }
                    }
                }
            }
            finalRes.addAll(res);
        }

        if (!allExplanation) {
            finalRes = new HashSet<>();
            finalRes.add(minimalExplication);
        }
        return finalRes;
    }
}
