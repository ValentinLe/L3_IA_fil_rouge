
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
    private int cpt = 0;

    public Backtracking(Set<Variable> variables, Set<Constraint> constraints){
        this.variables = variables;
        this.constraints = constraints;
        ArrayList<Constraint> cons = new ArrayList<>(constraints);
        for(int i = 1; i<(cons.size()+1); i++) {
            System.out.println("c" + i + " : " + cons.get(i-1));
        }
        System.out.println("\n\n");
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

    public boolean isCompatible(Variable var, String value, Map<Variable, String> voiture,boolean test) {
        Map<Variable, String> voitureTest = new HashMap(voiture);
        voitureTest.put(var,value);
        if (test) {
            System.out.println("\n\n");
            System.out.println("Voiture : " + voitureTest);
        }
        for(Constraint c : this.constraints) {
            if (test) {
                System.out.println(c + " --> " + c.isSatisfiedBy(voitureTest));
            }
            if (!c.isSatisfiedBy(voitureTest)) {
                return false;
            }
        }
        return true;
    }

    public Map<Variable, String> solution() {
        return backtracking(new HashMap<>(), getSortVariable());
    }
    
    // toit = "noir", hayon = "noir", capot = "noir", gauche = "noir", droit = "blanc", porte = "rouge"
    public Map<Variable, String> backtracking(Map<Variable, String> voiture, ArrayList<Variable> sortVar) {
        this.cpt += 1;
        if (this.isComplete(voiture)) {
            return voiture;
        }
        Variable var = choiceVar(sortVar);
        Map<Variable, String> newVoiture;
        for(String value : var.getDomaine()) {
            System.out.println("vv : " + voiture);
            System.out.println("\n " + "i = " + cpt + " Variable : " + var + " = " + value + "\n");
            if (this.isCompatible(var, value, voiture,true)) {
                voiture.put(var, value);
                System.out.println("vv : " + voiture);
                newVoiture = backtracking(voiture, sortVar);
                if (this.isCompatible(var, value, newVoiture,true)) {
                    System.out.println(newVoiture);
                    return newVoiture;
                }
                voiture.remove(var);
            }
        }
        return new HashMap<>();
    }
}
