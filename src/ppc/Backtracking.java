
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
        CONSTRAINT_MAX,
        CONSTRAINT_MIN,
        DOMAINES_MIN,
        DOMAINES_MAX,
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

        for(int i = 1; i<(constraints.size()+1); i++) {
            System.out.println("c" + i + " : " + constraints.get(i-1));
        }
        System.out.println();
    }
    
    public int getNbNode() {
        return this.nbNode;
    }

    /**
     * Test if all variables is in a car
     * @param voiture the car
     * @param setVar all variables
     * @return the result of the test
     */
    public boolean isComplete(Map<Variable, String> voiture, Set<Variable> setVar) {
        for(Variable var : setVar) {
            if (!voiture.containsKey(var)) {
                return false;
            }
        }
        return true;
    }

    public boolean isComplete(Map<Variable, String> voiture, Map<Variable, Set<String>> mapVar) {
        for(Variable var : mapVar.keySet()) {
            if (!voiture.containsKey(var)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculate the occurence in constraints of a variable
     * @param var the variable
     * @return the number of constraints contains
     */
    public int heuristic(Variable var, Set<String> domain) {
        if (this.heuristic == Heuristic.CONSTRAINT_MAX) {
            return heuristicConstraintMax(var);
        } else if (this.heuristic == Heuristic.CONSTRAINT_MIN) {
            return heuristicConstraintMin(var);
        } else if (this.heuristic == Heuristic.DOMAINES_MAX) {
            return heuristicDomainMaxSize(var, domain);
        } else if (this.heuristic == Heuristic.DOMAINES_MIN) {
            return heuristicDomainMinSize(var, domain);
        } else {
            return 0;
        }
    }
    
    public int heuristicConstraintMax(Variable var) {
        int cpt = 0;
        for(Constraint c : this.constraints) {
            if (c.getScope().contains(var)) {
                cpt += 1;
            }
        }
        return cpt;
    }
    
    public int heuristicConstraintMin(Variable var) {
        return this.constraints.size() - heuristicConstraintMax(var);
    }
    
    public int heuristicDomainMaxSize(Variable var, Set<String> domain) {
        return domain.size();
    }
    
    public int heuristicDomainMinSize(Variable var, Set<String> domain) {
        Iterator<Variable> iter = this.variables.iterator();
        int sizeDom = iter.next().getDomaine().size();
        return sizeDom - domain.size();
    }

    /**
     * choice a var not in the car
     * @param voiture the car
     * @param setVar the list of variable
     * @return A variable not in the car
     */
    public Variable choiceVar(Map<Variable, String> voiture, Set<Variable> setVar) {
        for (Variable var : setVar) {
            if (!voiture.containsKey(var)) {
                return var;
            }
        }
        return null;
    }

    public Variable choiceVar(Map<Variable, String> voiture, Map<Variable, Set<String>> mapDom) {
        // initialize the variable not assigned and its occurence in the constraints
        // this will be the variable not assigned with the best occurence
        Variable varMax = null;
        int valueOcc = 0;
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
                        // if the occurence of variable if better than the
                        // last maximum find we replace it
                        valueOcc = currentValue;
                        varMax = var;
                    }
                }
            }
        }
        return varMax;
    }

    /**
     * Test if the car satisfies all constraints
     * @param voiture the car
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

    public Map<Variable, String> copyMap(Map<Variable, String> map) {
        return new HashMap<>(map);
    }

    public Map<Variable, Set<String>> copyMapDomain(Map<Variable, Set<String>> map) {
        return new HashMap<>(map);
    }

    public Map<Variable, Set<String>> getMapVariableNotAssigned(Map<Variable, String> voiture) {
        Map<Variable, Set<String>> map = new HashMap<>();
        for (Variable var : this.variables) {
            if (!voiture.containsKey(var)) {
                map.put(var, new HashSet<>(var.getDomaine()));
            }
        }
        return map;
    }

    public Map<Variable, Set<String>> transformToMap(Set<Variable> setVar) {
        Map<Variable, Set<String>> mapRes = new HashMap<>();
        for (Variable var : setVar) {
            mapRes.put(var, new HashSet<>(var.getDomaine()));
        }
        return mapRes;
    }

    public boolean filtering(Map<Variable, String> voiture, Map<Variable, Set<String>> mapDom) {
        for (Constraint c : this.constraints) {
            if (c.filtrer(voiture, mapDom)) {
                return filtering(voiture, mapDom) || true;
            }
        }
        return false;
    }

    /**
     * Get a solution of car
     * @return the solution or null if the solution doesn't exist
     */
    public Map<Variable, String> solution() {
        this.nbNode = 0;
        return backtracking(new HashMap<>(), this.variables);
    }

    public Set<Map<Variable, String>> solutions() {
        this.nbNode = 0;
        Set<Map<Variable, String>> solutions = new HashSet<>();
        backtrackingSols(solutions, new HashMap<>(), this.variables);
        return solutions;
    }
    
    public Map<Variable, String> solutionFilter() {
        this.nbNode = 0;
        return backtrackingFilter(new HashMap<>(), transformToMap(this.variables));
    }
    
    public Set<Map<Variable, String>> solutionsFilter() {
        this.nbNode = 0;
        Set<Map<Variable, String>> solutions = new HashSet<>();
        backtrackingSolsFilter(solutions, new HashMap<>(), transformToMap(this.variables));
        return solutions;
    }
    


    public Map<Variable, String> backtracking(Map<Variable, String> voiture, Set<Variable> setVar) {
        
        this.nbNode += 1;
        
        if (this.isComplete(voiture, setVar)) {
            return voiture;
        }
        Variable var = choiceVar(voiture, setVar);
        Map<Variable, String> backVoiture;
        for (String value : var.getDomaine()) {
            voiture.put(var, value);
            if (this.isCompatible(voiture)) {
                backVoiture = backtracking(voiture, setVar);
                if (backVoiture != null) {
                    return backVoiture;
                }
            }
            voiture.remove(var);
        }
        return null;
    }
    
    public Map<Variable, String> backtrackingSols(Set<Map<Variable, String>> solutions, 
                    Map<Variable, String> voiture, Set<Variable> setVar) {
        
        this.nbNode += 1;
        
        if (this.isComplete(voiture, setVar)) {
            solutions.add(copyMap(voiture));
            return null;
        }
        Variable var = choiceVar(voiture, setVar);
        for (String value : var.getDomaine()) {
            voiture.put(var, value);
            if (this.isCompatible(voiture)) {
                backtrackingSols(solutions, copyMap(voiture), setVar);
            }
            voiture.remove(var);
        }
        return null;
    }
    
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
    
    public Map<Variable, String> backtrackingSolsFilter(Set<Map<Variable, String>> solutions, 
                    Map<Variable, String> voiture, Map<Variable, Set<String>> mapDom) {
        
        this.nbNode += 1;
        
        if (this.isComplete(voiture, mapDom)) {
            solutions.add(copyMap(voiture));
            return null;
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
        return null;
    }

}
