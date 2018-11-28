
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
                // si c'est une explication on remplace la variable de l'ancienne 
                // explication par la nouvelle trouvee
                explanation = ePrime;
            }
        }
        return explanation;
    }
    
    /**
     * trouve l'explication minimale au sens de la cardinalite
     * @param choices l'ensemble de choix predefinis
     * @param variable la variable
     * @param value sa valeur
     * @return l'explication minimale au sens de la cardinalite
     */
    public Map<Variable, String> findMinimalCardinalExplanation(Map<Variable, String> choices, Variable variable, String value) {
        // recupere un set avec 1 explication qui est l'explication minimale au sens de
        // la cardinalite
        Set<Map<Variable, String>> solution = this.findExplanations(choices, variable, value, false);
        // recuperation de l'explication, iter.next ne renverra pas une exception
        // car il y aura au moins un element dans l'ensemble donc pas nessessaire
        // de tester si l'ensemble est vide
        Iterator<Map<Variable, String>> iter = solution.iterator();
        return iter.next();
    }
    
    /**
     * enumere toutes les explications d'un ensemble de choix
     * @param choices les choix predefinis
     * @param variable la variable que l'on voudrai ajouter au choix
     * @param value la valeur de la variable que l'on voudrai ajouter
     * @return l'ensemble de toutes les explications
     */
    public Set<Map<Variable, String>> findAllExplanations(Map<Variable, String> choices, Variable variable, String value) {
        return this.findExplanations(choices, variable, value, true);
    }
    
    /**
     * enumere toutes les explications ou bien renvoi l'explication minimale au
     * sens de la cardinalite selon la valeur du booleen
     * @param choices les choix predefinis
     * @param variable la variable que l'on voudrai ajouter aux choix
     * @param value la valeur a mettre a la variable dans les choix
     * @param allExplanations true si on veut enumerer toutes les explications et
     * false si on veut juste recuperer juste un ensemble avec juste l'explication
     * minimale au sens de la cardinalite
     * @return l'ensembles de toutes les explications ou un ensemble avec l'explication
     * minimale au sens de la cardinalite
     */
    private Set<Map<Variable, String>> findExplanations(Map<Variable, String> choices, Variable variable, String value, boolean allExplanations) {
        // Pour cet algo on part de l'ensemble des choix et pour chaque choix on
        // le retirera pour avoir une explication de taille choix.size()-1. On ne
        // part pas de l'ensemble vide pour aller jusqu'au choix car on a une monotonicité
        // si A et B distincts, on a A et B qui ne sont pas une explication mais
        // cela ne nous garantie pas que AuB n'est pas une explication
        
        // l'ensemble que l'on retournera
        Set<Map<Variable, String>> finalRes = new HashSet<>();
        // les explication a l'etape k
        Set<Map<Variable, String>> res = new HashSet<>();
        // les explications a l'etape k-1
        Set<Map<Variable, String>> resPrec;
        // variable qui servira de copie de chaque explications de l'etape k-1 pour
        // pouvoir generer ceux de l'etape k
        Map<Variable, String> copyPart;
        // initialisation de l'explication minimale au sens de  l'inclusion aux
        // choix predefinis qui sont une explication
        Map<Variable, String> minimalExplication = new HashMap<>(choices);
        finalRes.add(minimalExplication); // ajout de l'explication de la taille maximale
        res.add(minimalExplication); // pour genereer les explication de taille inferieures

        for (int size = choices.size(); size>0; size--) {
            // parcours inverse on part des choix pour arriver jusqu'au singletons
            // on effectue une copie des k-1
            resPrec = new HashSet<>(res);
            res = new HashSet<>(); // initialise la variable des k
            for (Map<Variable, String> part : resPrec) {
                // pour toutes les parties des k-1
                for (Variable var : part.keySet()) {
                    // pour toutes les variables de cette partie
                    // on fait une copie de la partie où l'on retire la variable
                    copyPart = new HashMap<>(part);
                    copyPart.remove(var);
                    if (this.isExplanation(copyPart, variable, value)) {
                        // si la partie construite est une explication on l'ajoute
                        // dans l'ensemble de l'etage k
                        res.add(copyPart);
                        if (!allExplanations && copyPart.size() < minimalExplication.size()) {
                            // si on veut que la methode nous donne l'explication
                            // minimale au sens de la cardinalite on test si l'explication
                            // a une plus petite taille que l'explication qui est
                            // affectee si c'est le cas on remplace l'ancinne explication
                            // par la nouvelle
                            minimalExplication = copyPart;
                        }
                    }
                } // fin for var dans part
            } // fin for part dans resPrec
            if (allExplanations) {
                // si on veut toutes les explications on ajoute toutes les 
                // explications de l'etage k trouvees
                finalRes.addAll(res);
            }
        } // fin for avec variable size

        if (!allExplanations) {
            // si on veut l'explication minimal on modifie la variable de retour
            // on la met a l'ensemble vide et on lui ajoute l'explication minimale
            finalRes = new HashSet<>();
            finalRes.add(minimalExplication);
        }
        return finalRes;
    }
}
