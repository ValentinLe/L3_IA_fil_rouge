
package ppc;

import java.util.*;
import representations.*;

/**
 * This classe is the backtracking search methode
 *
 */
public class Backtracking {

    private Set<Variable> variables;
    private ArrayList<Constraint> constraints;
    private Heuristic heuristic;
    private int nbNode = 0;
    
    /**
     * enumeration of all heuristics
     */
    public enum Heuristic {
        // this heuristic take the max occurence of variables in constraint
        CONSTRAINT_MAX,
        // this heuristic take the min occurence of variables in constraint
        CONSTRAINT_MIN,
        // this heuristic take the max size of variables' domain
        DOMAINES_MAX,
        // this heuristic take the min size of variables' domain
        DOMAINES_MIN,
        // no heuristic
        NONE;
    }

    /**
     * Build a instance of backtracking
     * @param variables all variables possibles
     * @param constraints constraints of the probleme
     * @param heuristic the heuristic of backtracking
     */
    public Backtracking(Set<Variable> variables, ArrayList<Constraint> constraints, Heuristic heuristic){
        this.constraints = constraints;
        this.variables = variables;
        this.heuristic = heuristic;

        // print of all constraint implicate in the backtracking
        for(int i = 1; i<(constraints.size()+1); i++) {
            System.out.println("c" + i + " : " + constraints.get(i-1));
        }
        System.out.println();
    }
    
    /**
     * Getter of heuristic of backtracking
     * @return the heuristic of backtracking
     */
    public Heuristic getHeuristic() {
        return this.heuristic;
    }
    
    /**
     * Setter of heuristic of backtracking
     * @param heuristic the heuristic you want to use
     */
    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }
    
    /**
     * Getter of the number of nodes traveled in backtracking algorithm
     * @return the number of nodes
     */
    public int getNbNode() {
        return this.nbNode;
    }

    /**
     * calculates the heuristic of the variable with the enum selected in the constructor
     * @param var the variable
     * @return the value of its heuristic
     */
    public int heuristic(Variable var, Set<String> domain) {
        if (this.heuristic == Heuristic.CONSTRAINT_MAX) {
            return heuristicConstraintMax(var);
        } else if (this.heuristic == Heuristic.CONSTRAINT_MIN) {
            return heuristicConstraintMin(var);
        } else if (this.heuristic == Heuristic.DOMAINES_MAX) {
            return heuristicDomainMaxSize(domain);
        } else if (this.heuristic == Heuristic.DOMAINES_MIN) {
            return heuristicDomainMinSize(domain);
        } else {
            return 0;
        }
    }
    
    /**
     * Calculates the occurence of variable in constraints
     * @param var the target variable
     * @return the number of this variable in constraints
     */
    public int heuristicConstraintMax(Variable var) {
        int cpt = 0;
        for(Constraint c : this.constraints) {
            if (c.getScope().contains(var)) {
                cpt += 1;
            }
        }
        return cpt;
    }
    
    /**
     * Calculates the opposite of occurence of variable, the minimum 
     * will be the maximum
     * @param var the target variable
     * @return the opposite of occurence of variable
     */
    public int heuristicConstraintMin(Variable var) {
        return -heuristicConstraintMax(var);
    }
    
    /**
     * Find the size of variable's domain
     * @param domain of variable
     * @return the size of the domain
     */
    public int heuristicDomainMaxSize(Set<String> domain) {
        return domain.size();
    }
    
    /**
     * Calculates the opposite of domain's size, the minimum will be the
     * maximum
     * @param domain the domain of variable
     * @return the opposite domain's size
     */
    public int heuristicDomainMinSize(Set<String> domain) {
        return -heuristicDomainMaxSize(domain);
    }

    /**
     * Chooses a variable not difinied int the car according 
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
                    valueOcc = heuristic(var, mapDom.get(var));
                } else {
                    currentValue = heuristic(var, mapDom.get(var));
                    if (currentValue > valueOcc) {
                        // if the value of the current variable's heuristic 
                        //is better than the last maximum find we replace it
                        valueOcc = currentValue;
                        varMax = var;
                    }
                }
            }
        }
        return varMax;
    }
    
    /**
     * Test if all variables is in a car
     * @param voiture the car
     * @param mapVar map of all variables with its domain
     * @return the result of the test
     */
    public boolean isComplete(Map<Variable, String> voiture, Map<Variable, Set<String>> mapVar) {
        for(Variable var : mapVar.keySet()) {
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
     * Build a map of variable with a copy of its domain
     * @param setVar the set of variables you want to transform
     * @return the map builded
     */
    public Map<Variable, Set<String>> transformToMap(Set<Variable> setVar) {
        Map<Variable, Set<String>> mapRes = new HashMap<>();
        for (Variable var : setVar) {
            mapRes.put(var, new HashSet<>(var.getDomaine()));
        }
        return mapRes;
    }

    /**
     * filters variables' domain
     * @param voiture the car for the filter
     * @param mapDom the map of variables' domain will be or not filter
     * @return true if there was a filtering
     */
    public boolean filtering(Map<Variable, String> voiture, Map<Variable, Set<String>> mapDom) {
        for (Constraint c : this.constraints) {
            if (c.filtrer(voiture, mapDom)) {
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
     * Get set of all solution of cars without filtering
     * @return a set of all solution cars or null if there aren't solutions
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
     * Get a set of all solution of cars with filtering
     * @return a set of all solution cars or null if there aren't solutions
     */
    public Set<Map<Variable, String>> solutionsFilter() {
        this.nbNode = 0;
        Set<Map<Variable, String>> solutions = new HashSet<>();
        backtrackingSolsFilter(solutions, new HashMap<>(), transformToMap(this.variables));
        return (solutions.isEmpty() ? null : solutions);
    }
    
    /**
     * Backtracking algorithm, return the first solution found without filtering
     * @param voiture the car for the build
     * @param mapDom the map of variable with a copy of its domain
     * @return the first solution car found
     */
    public Map<Variable, String> backtracking(Map<Variable, String> voiture, Map<Variable, Set<String>> mapDom) {
        
        this.nbNode += 1;
        
        if (this.isComplete(voiture, mapDom)) {
            return voiture;
        }
        Variable var = choiceVar(voiture, mapDom);
        Map<Variable, String> backVoiture;
        for (String value : var.getDomaine()) {
            voiture.put(var, value);
            if (this.isCompatible(voiture)) {
                backVoiture = backtracking(voiture, mapDom);
                if (backVoiture != null) {
                    return backVoiture;
                }
            }
            voiture.remove(var);
        }
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
        
        this.nbNode += 1;
        
        if (this.isComplete(voiture, mapDom)) {
            solutions.add(copyMap(voiture));
            return;
        }
        Variable var = choiceVar(voiture, mapDom);
        for (String value : var.getDomaine()) {
            voiture.put(var, value);
            if (this.isCompatible(voiture)) {
                backtrackingSols(solutions, copyMap(voiture), mapDom);
            }
            voiture.remove(var);
        }
    }
    
    /**
     * Backtracking algorithm, return the first solution found with filtering
     * @param voiture the car for the build
     * @param mapDom the map of variable with a copy of its domain
     * @return the first solution car found
     */
    public Map<Variable, String> backtrackingFilter(Map<Variable, String> voiture, Map<Variable, Set<String>> mapDom) {
        
        this.nbNode += 1;
        
        if (this.isComplete(voiture, mapDom)) {
            return copyMap(voiture);
        }
        Variable var = choiceVar(voiture, mapDom);
        Map<Variable, Set<String>> copyMapDomain;
        Map<Variable, String> backVoiture;
        for (String value : mapDom.get(var)) {
            voiture.put(var, value);
            if (this.isCompatible(voiture)) {
                copyMapDomain = copyMapDomain(mapDom);
                Set<String> restrictedDom = new HashSet<>();
                restrictedDom.add(value);
                copyMapDomain.put(var, restrictedDom);
                filtering(voiture, copyMapDomain);
                backVoiture = backtrackingFilter(copyMap(voiture), copyMapDomain);
                if (backVoiture != null) {
                    return backVoiture;
                }
            }
            voiture.remove(var);
        }
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
        
        this.nbNode += 1;
        
        if (this.isComplete(voiture, mapDom)) {
            solutions.add(copyMap(voiture));
            return;
        }
        Variable var = choiceVar(voiture, mapDom);
        Map<Variable, Set<String>> copyMapDomain;
        for (String value : mapDom.get(var)) {
            voiture.put(var, value);
            if (this.isCompatible(voiture)) {
                copyMapDomain = copyMapDomain(mapDom);
                Set<String> restrictedDom = new HashSet<>();
                restrictedDom.add(value);
                copyMapDomain.put(var, restrictedDom);
                filtering(voiture, copyMapDomain);
                backtrackingSolsFilter(solutions, copyMap(voiture), copyMapDomain);
            }
            voiture.remove(var);
        }
    }

}
