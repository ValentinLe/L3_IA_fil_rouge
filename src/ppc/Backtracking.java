
package ppc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import representations.*;

public class Backtracking {
    
    private Set<Variable> variables;
    private Set<Constraint> constraints;
    
    public Backtracking(Set<Variable> variables, Set<Constraint> constraints){
        this.variables = variables;
        this.constraints = constraints;
    }
    
    public boolean isComplete(Map<Variable, String> voiture) {
        for(Variable var : this.variables) {
            if (!voiture.containsKey(var)) {
                return false;
            }
        }
        return true;
    }
    
    public int heuristic(Variable var) {
        int cpt = 0;
        for(Constraint c : this.constraints) {
            for(Variable v : c.getScope()) {
                if (var.equals(v)) {
                    cpt += 1;
                }
            }
        }
        return cpt;
    }
    
    public ArrayList<Variable> getSortVariable() {
        ArrayList<Variable> listVar = new ArrayList<>();
        Iterator<Variable> iter = this.variables.iterator();
        while(iter.hasNext()) {
            Variable var = iter.next();
            int valueVar = heuristic(var);
            var.setValue(valueVar);
            listVar.add(var);
        }
        Collections.sort(listVar);
        Collections.reverse(listVar);
        return listVar;
    }
    
    public Variable choiceVar(ArrayList<Variable> sortVar) {
        Variable var = sortVar.get(0);
        sortVar.remove(0);
        return var;
    }
    
    public Map<Variable, String> solution() {
        return backtracking(new HashMap<>(), getSortVariable());
    }
    
    public boolean isCompatible(Variable var, String value, Map<Variable, String> voiture) {
        voiture.put(var,value);
        for(Constraint c : this.constraints) {
            if (!c.isSatisfiedBy(voiture)) {
                return false;
            }
        }
        return true;
    }
    
    public Map<Variable, String> backtracking(Map<Variable, String> voiture, ArrayList<Variable> sortVar) {
        if (this.isComplete(voiture)) {
            return voiture;
        }
        Variable var = choiceVar(sortVar);
        Set<String> csp;
        Map<Variable, String> newVoiture;
        for(String value : var.getDomaine()) {
            if (this.isCompatible(var, value, voiture)) {
                voiture.put(var, value);
                newVoiture = backtracking(voiture, sortVar);
                if (!this.isCompatible(var, value, newVoiture)) {
                    voiture.remove(var);
                } else {
                    return voiture;
                }
            }
        }
        return new HashMap<>();
    }
}
