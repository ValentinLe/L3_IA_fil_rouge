
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
        ArrayList<Constraint> cons = new ArrayList<>(constraints);
        for(int i = 1; i<(cons.size()+1); i++) {
            System.out.println("c" + i + " : " + cons.get(i-1));
        }
        System.out.println();
    }

    public boolean isComplete(Map<Variable, String> voiture, ArrayList<Variable> sortVar) {
        for(Variable var : sortVar) {
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

    public Variable choiceVar(Map<Variable, String> voiture, ArrayList<Variable> sortVar) {
        for (Variable var : sortVar) {
            if (!voiture.containsKey(var)) {
                return var;
            }
        }
        return null;
    }

    public boolean isCompatible(Map<Variable, String> voiture) {
        for(Constraint c : this.constraints) {
            if (!c.isSatisfiedBy(voiture)) {
                return false;
            }
        }
        return true;
    }

    public Map<Variable, String> solution() {
        return backtracking(new HashMap<>(), getSortVariable());
    }
    
    public Map<Variable, String> backtracking(Map<Variable, String> voiture, ArrayList<Variable> sortVar) {
        if (this.isComplete(voiture, sortVar)) {
            return voiture;
        }
        Variable var = choiceVar(voiture, sortVar);
        Map<Variable, String> backVoiture;
        for (String value : var.getDomaine()) {
            voiture.put(var, value);
            if (this.isCompatible(voiture)) {
                backVoiture = backtracking(voiture, sortVar);
                if (backVoiture != null) {
                    return backVoiture;
                }
            } else {
                voiture.remove(var);
            }
        }
        return null;
    }
}
