
package ppc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import representations.*;

/**
 * This classe is the backtracking search methode
 *
 */
public class Backtracking {

    private ArrayList<Variable> variables;
    private ArrayList<Constraint> constraints;

    /**
     * Build a instance of backtracking
     * @param variables all variables possibles
     * @param constraints constraints of the probleme
     */
    public Backtracking(Set<Variable> variables, ArrayList<Constraint> constraints){
        this.constraints = constraints;
        this.variables = this.getSortVariable(variables);
        
        for(int i = 1; i<(constraints.size()+1); i++) {
            System.out.println("c" + i + " : " + constraints.get(i-1));
        }
        System.out.println();
    }

    /**
     * Test if all variables is in a car
     * @param voiture the car
     * @param sortVar all variables
     * @return the result of the test
     */
    public boolean isComplete(Map<Variable, String> voiture, ArrayList<Variable> sortVar) {
        for(Variable var : sortVar) {
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
    public int heuristic(Variable var) {
        int cpt = 0;
        for(Constraint c : this.constraints) {
            if (c.getScope().contains(var)) {
                cpt += 1;
            }
        }
        return cpt;
    }

    /**
     * Sort a set of variable in decreasing order on their occurences
     * @param variablesNontriees the set of variable to sort
     * @return a copy of the list ordered
     */
    public ArrayList<Variable> getSortVariable(Set<Variable> variablesNontriees) {
        ArrayList<Variable> listVar = new ArrayList<>();
        Iterator<Variable> iter = variablesNontriees.iterator();
        while(iter.hasNext()) {
            Variable var = iter.next();
            int valueVar = heuristic(var);
            var.setOccurences(valueVar);
            listVar.add(var);
        }
        Collections.sort(listVar);
        Collections.reverse(listVar);
        return listVar;
    }

    /**
     * choice a var not in the car
     * @param voiture the car
     * @param sortVar the list of variable
     * @return A variable not in the car
     */
    public Variable choiceVar(Map<Variable, String> voiture, ArrayList<Variable> sortVar) {
        for (Variable var : sortVar) {
            if (!voiture.containsKey(var)) {
                return var;
            }
        }
        return null;
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
        for(Variable var : this.variables) {
            if (!voiture.containsKey(var)) {
                map.put(var, new HashSet<>(var.getDomaine()));
            }
        }
        return map;
    }

    /**
     * Get a solution of car
     * @return the solution or null if the solution doesn't exist
     */
    public Map<Variable, String> solution() {
        return backtracking(new HashMap<>(), this.variables);
    }

    /**
     * The Backtracking Algorithm
     * @param voiture the car
     * @param sortVar list of variables
     * @return the solution or null if the solution doesn't exist
     */
    public Map<Variable, String> backtracking(Map<Variable, String> voiture, ArrayList<Variable> sortVar) {
        if (this.isComplete(voiture, sortVar)) {
            return voiture;
        }
        Variable var = choiceVar(voiture, sortVar);
        Map<Variable, String> backVoiture;
        for (String value : var.getDomaine()) {
            voiture.put(var, value);
            if (this.isCompatible(voiture)) {
                backVoiture = backtracking(copyMap(voiture), sortVar);
                if (backVoiture != null) {
                    return backVoiture;
                }
            }
            voiture.remove(var);
        }
        return null;
    }
    
}
