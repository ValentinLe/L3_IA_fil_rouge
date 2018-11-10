
package ppc;

import java.util.*;
import representations.*;

/**
 * This classe is the backtracking search methode
 *
 */
public class Backtracking {

    private Set<Variable> variables;
    private List<Constraint> constraints;
    private HeuristicVariable heuristic;
    private int nbNode = 0;

    /**
     * Builds an instance of backtracking
     * @param variables all variables possibles
     * @param constraints constraints of the probleme
     * @param heuristic the heuristic of backtracking
     */
    public Backtracking(Set<Variable> variables, List<Constraint> constraints, HeuristicVariable heuristic){
        this.constraints = constraints;
        this.variables = variables;
        this.heuristic = heuristic;
    }

    /**
     * Creates a String representation of constraints in backtrack
     * @return the string representation of constraints
     */
    public String getStringConstraints() {
        String ch = "";
        // prints all constraints implicated in backtracking
        for(int i = 1; i<(constraints.size()+1); i++) {
            ch += "c" + i + " : " + constraints.get(i-1) + "\n";
        }
        return ch;
    }

    /**
     * Getter method of heuristic of backtracking
     * @return the heuristic of backtracking
     */
    public HeuristicVariable getHeuristic() {
        return this.heuristic;
    }

    /**
     * Setter method of heuristic of backtracking
     * @param heuristic the heuristic you want to use
     */
    public void setHeuristic(HeuristicVariable heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * Getter method of the number of nodes traveled in backtracking algorithm
     * @return the number of nodes
     */
    public int getNbNode() {
        return this.nbNode;
    }

    /**
     * Chooses a variable not definied in the car according
     * to the heuristic of backtracking
     * @param voiture the car
     * @param mapDom the map of variable and its domain
     * @return one of variables not definied in the car with the best
     * heuristic value
     */
    public Variable choiceVar(Map<Variable, String> voiture, Map<Variable, Set<String>> mapDom) {
        // initialize the variable not assigned and its occurence in the constraints
        // this will be the variable not assigned with the best occurence
        Variable varMax = null;
        int valueOcc = Integer.MIN_VALUE;
        int currentValue; // initialize the current value of occurence
        for (Variable var : mapDom.keySet()) {
            if (!voiture.containsKey(var)) {
                // if the variable isn't definied in the car
                if (varMax == null) {
                    // if the max variable isn't initialize with a variable
                    varMax = var;
                    valueOcc = this.heuristic.heuristicValue(this.constraints, var, mapDom.get(var));
                } else {
                    currentValue = this.heuristic.heuristicValue(this.constraints, var, mapDom.get(var));
                    if (currentValue > valueOcc) {
                        // if the value of the current variable's heuristic
                        // is better than the last maximum found, we replace it
                        valueOcc = currentValue;
                        varMax = var;
                    }
                }
            }
        }
        return varMax;
    }

    /**
     * Test if all variables are in a car
     * @param voiture the car
     * @param mapVar map of all variables with its domain
     * @return the result of the test
     */
    public boolean isComplete(Map<Variable, String> voiture, Map<Variable, Set<String>> mapVar) {
        for(Variable var : mapVar.keySet()) {
            // for all variables in the map
            if (!voiture.containsKey(var)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Test if the car satisfies all constraints
     * @param voiture the car of the test
     * @return the result of the test
     */
    public boolean isCompatible(Map<Variable, String> voiture) {
        for(Constraint c : this.constraints) {
            if (!c.isSatisfiedBy(voiture)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Copy a map like (Variable, String)
     * @param map the map for the copy
     * @return a copy of the map
     */
    public Map<Variable, String> copyMap(Map<Variable, String> map) {
        return new HashMap<>(map);
    }

    /**
     * Copy a map like (Variable, Set(String))
     * @param map the map for the copy
     * @return a copy of the map
     */
    public Map<Variable, Set<String>> copyMapDomain(Map<Variable, Set<String>> map) {
        return new HashMap<>(map);
    }

    /**
     * Builds a map of variable with a copy of its domain
     * @param setVar the set of variables you want to transform
     * @return the map built
     */
    public Map<Variable, Set<String>> transformToMap(Set<Variable> setVar) {
        Map<Variable, Set<String>> mapRes = new HashMap<>();
        for (Variable var : setVar) {
            // puts variables with its copied domain
            mapRes.put(var, new HashSet<>(var.getDomaine()));
        }
        return mapRes;
    }

    /**
     * filters variables' domain
     * @param voiture the car for the filter
     * @param mapDom the map of variables' domain to be or not to be filtered
     * @return true if there was a filtering
     */
    public boolean filtering(Map<Variable, String> voiture, Map<Variable, Set<String>> mapDom) {
        for (Constraint c : this.constraints) {
            if (c.filtrer(voiture, mapDom)) {
                // if filtering occures, return a recursivity or true to get
                // all filtering and the boolean for the final return
                return filtering(voiture, mapDom) || true;
            }
        }
        return false;
    }

    /**
     * Get a solution of car without filtering
     * @return the first solution found or null if the solution doesn't exist
     */
    public Map<Variable, String> solution() {
        this.nbNode = 0;
        return backtracking(new HashMap<>(), transformToMap(this.variables));
    }

    /**
     * Get a set of all solution cars without filtering
     * @return a set of all solution cars or null if there aren't any solutions
     */
    public Set<Map<Variable, String>> solutions() {
        this.nbNode = 0;
        Set<Map<Variable, String>> solutions = new HashSet<>();
        backtrackingSols(solutions, new HashMap<>(), transformToMap(this.variables));
        return (solutions.isEmpty() ? null : solutions);
    }

    /**
     * Get a solution of car with filtering
     * @return the first solution found or null if the solution doesn't exist
     */
    public Map<Variable, String> solutionFilter() {
        this.nbNode = 0;
        return backtrackingFilter(new HashMap<>(), transformToMap(this.variables));
    }

    /**
     * Get a set of all solution cars with filtering
     * @return a set of all solution cars or null if there aren't any solutions
     */
    public Set<Map<Variable, String>> solutionsFilter() {
        this.nbNode = 0;
        Set<Map<Variable, String>> solutions = new HashSet<>();
        backtrackingSolsFilter(solutions, new HashMap<>(), transformToMap(this.variables));
        return (solutions.isEmpty() ? null : solutions);
    }

    /**
     * Backtracking algorithm, returns the first solution found without filtering
     * @param voiture the car for the build
     * @param mapDom the map of variable with a copied of its domain
     * @return the first solution car found
     */
    public Map<Variable, String> backtracking(Map<Variable, String> voiture, Map<Variable, Set<String>> mapDom) {

        this.nbNode += 1; // node counter

        if (this.isComplete(voiture, mapDom)) {
            // return the solution
            return voiture;
        }
        // Choosesa a variable not definied in the car
        Variable var = choiceVar(voiture, mapDom);
        Map<Variable, String> backVoiture;
        for (String value : var.getDomaine()) {
            // for all values in variable's domain
            // add variable with the value in the car
            voiture.put(var, value);
            if (this.isCompatible(voiture)) {
                // if all constraints are satisfied by the car
                backVoiture = backtracking(voiture, mapDom);
                if (backVoiture != null) {
                    // if the recursivity finds a solution, return this solution
                    return backVoiture;
                }
            }
            // remove variable with the value in the car, it's a wrong value
            voiture.remove(var);
        }
        // solution not found
        return null;
    }

    /**
     * Backtracking algorithm, fill the set of cars with a copy of all solution
     * cars found without filtering
     * @param solutions the set will be fill
     * @param voiture the car build for the backtracking
     * @param mapDom mapDom the map of variable with a copy of its domain
     */
    public void backtrackingSols(Set<Map<Variable, String>> solutions,
                    Map<Variable, String> voiture, Map<Variable, Set<String>> mapDom) {

        this.nbNode += 1; // node counter

        if (this.isComplete(voiture, mapDom)) {
            // add the car in the set of solutions
            solutions.add(copyMap(voiture));
            // block the following of the function's call
            return;
        }
        // Chooses variable not definied in the car
        Variable var = choiceVar(voiture, mapDom);
        for (String value : var.getDomaine()) {
            // for all values in variable's domain
            // add variable with the value in the car
            voiture.put(var, value);
            if (this.isCompatible(voiture)) {
                // if all constraints are satisfied by the car
                backtrackingSols(solutions, copyMap(voiture), mapDom);
            }
            // remove variable with the value in the car, it's a wrong value
            voiture.remove(var);
        }
    }

    /**
     * Backtracking algorithm, returns the first solution found with filtering
     * @param voiture the car for the build
     * @param mapDom the map of variable with a copy of its domain
     * @return the first solution car found
     */
    public Map<Variable, String> backtrackingFilter(Map<Variable, String> voiture, Map<Variable, Set<String>> mapDom) {

        this.nbNode += 1; // node counter

        if (this.isComplete(voiture, mapDom)) {
            // returns the solution
            return copyMap(voiture);
        }
        // Chooses a variable not definied in the car
        Variable var = choiceVar(voiture, mapDom);
        Map<Variable, Set<String>> copyMapDomain;
        Map<Variable, String> backVoiture;
        for (String value : mapDom.get(var)) {
            // for all variable in filter domain
            // add variable with the value in the car
            voiture.put(var, value);
            if (this.isCompatible(voiture)) {
                // if all constraints are satisfied by the car
                copyMapDomain = copyMapDomain(mapDom);

                // reduce the domaine of variable to {value}
                Set<String> restrictedDom = new HashSet<>();
                restrictedDom.add(value);
                copyMapDomain.put(var, restrictedDom);

                // filtering of variables' domain
                filtering(voiture, copyMapDomain);
                backVoiture = backtrackingFilter(copyMap(voiture), copyMapDomain);
                if (backVoiture != null) {
                    // if the recursivity finds a solution, return this solution
                    return backVoiture;
                }
            }
            // remove variable with the value in the car, it's a wrong value
            voiture.remove(var);
        }
        // solution not found
        return null;
    }

    /**
     * Backtracking algorithm, fill the set of cars with a copy of all
     * solution cars found with filtering
     * @param solutions the set will be fill
     * @param voiture the car build for the backtracking
     * @param mapDom mapDom the map of variable with a copy of its domain
     */
    public void backtrackingSolsFilter(Set<Map<Variable, String>> solutions,
                    Map<Variable, String> voiture, Map<Variable, Set<String>> mapDom) {

        this.nbNode += 1; // node counter

        if (this.isComplete(voiture, mapDom)) {
            // add the car in the set of solutions
            solutions.add(copyMap(voiture));
            // block the following of the function's call
            return;
        }
        // Chooses a variable not definied in the car
        Variable var = choiceVar(voiture, mapDom);
        Map<Variable, Set<String>> copyMapDomain;
        for (String value : mapDom.get(var)) {
            // for all variable in filter domain
            // add variable with the value in the car
            voiture.put(var, value);
            if (this.isCompatible(voiture)) {
                // if all constraints are satisfied by the car
                copyMapDomain = copyMapDomain(mapDom);

                // reduce the domaine of variable to {value}
                Set<String> restrictedDom = new HashSet<>();
                restrictedDom.add(value);
                copyMapDomain.put(var, restrictedDom);

                // filtering of variables' domain
                filtering(voiture, copyMapDomain);
                backtrackingSolsFilter(solutions, copyMap(voiture), copyMapDomain);
            }
        }
        // remove variable with the value in the car, it's a wrong value
        voiture.remove(var);
    }
}
