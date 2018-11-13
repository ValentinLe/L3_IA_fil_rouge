
package diagnosis;

import java.util.*;
import representations.*;
import ppc.*;

public class Diagnoser {
  
    private Backtracking backtrack;
    private Map<Variable, Set<String>> csp;
  
    public Diagnoser(Backtracking backtrack) {
        this.backtrack = backtrack;
        this.csp = this.backtrack.transformToMap(this.backtrack.getVariables());
    }

    public boolean isExplication(Map<Variable, String> choices, Variable variable, String value) {
        Set<String> reduceDomain;
        Map<Variable, Set<String>> copyCsp = new HashMap<>(this.csp);
        for (Variable var : choices.keySet()) {
            reduceDomain = new HashSet<>();
            reduceDomain.add(choices.get(var));
            copyCsp.put(var, reduceDomain);
        }
        reduceDomain = new HashSet<>();
        reduceDomain.add(value);
        copyCsp.put(variable, reduceDomain);
        Map<Variable, String> solution = this.backtrack.backtrackingFilter(choices, copyCsp);
        return solution == null;
    }

    public Variable getFirst(Map<Variable, String> map) {
        Iterator<Variable> iter = map.keySet().iterator();
        return iter.next();
    }
    
    public Map<Variable, String> findExplication(Map<Variable, String> choices, Variable variable, String value) {
        Map<Variable, String> file = new HashMap<>(choices);
        Map<Variable, String> explication = new HashMap<>(choices);
        Map<Variable, String> ePrime;
        Variable var;
        while (!file.isEmpty()) {
            var = this.getFirst(file);
            System.out.println("\nF = " + file);
            System.out.println("Var : " + var);
            ePrime = new HashMap<>(explication);
            ePrime.remove(var);
            System.out.println("E = " + explication);
            System.out.println("E' = " + ePrime);
            System.out.println("Explication : " + this.isExplication(ePrime, variable, value));
            if (this.isExplication(ePrime, variable, value)) {
                explication = new HashMap<>(ePrime);
            }
            file.remove(var);
        }
        return explication;
    }
}
