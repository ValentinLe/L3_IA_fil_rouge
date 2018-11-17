
package diagnosis;

import java.util.*;
import representations.*;
import ppc.*;

public class Diagnoser {

    private Backtracking backtrack;
    private Map<Variable, Set<String>> variablesWithDomain;

    public Diagnoser(Backtracking backtrack) {
        this.backtrack = backtrack;
        this.variablesWithDomain = this.backtrack.transformToMap(this.backtrack.getVariables());
    }

    public void add(Variable variable, String value) {
        this.backtrack.addVariable(variable);
        this.variablesWithDomain.put(variable, new HashSet<>(variable.getDomaine()));
    }

    public void remove(Variable variable) {
        this.backtrack.removeVariable(variable);
        this.variablesWithDomain.remove(variable);
    }

    public boolean isExplanation(Map<Variable, String> choices, Variable variable, String value) {
        Set<String> reduceDomain;
        Map<Variable, Set<String>> copyDomain = new HashMap<>(this.variablesWithDomain);
        for (Variable var : choices.keySet()) {
            reduceDomain = new HashSet<>();
            reduceDomain.add(choices.get(var));
            copyDomain.put(var, reduceDomain);
        }
        reduceDomain = new HashSet<>();
        reduceDomain.add(value);
        copyDomain.put(variable, reduceDomain);
        Map<Variable, String> solution = this.backtrack.backtrackingFilter(choices, copyDomain);
        return solution == null;
    }

    public Map<Variable, String> findMinimalInclusionExplanation(Map<Variable, String> choices, Variable variable, String value) {
        Map<Variable, String> explanation = new HashMap<>(choices);
        Map<Variable, String> ePrime;
        for(Variable var : choices.keySet()) {
            ePrime = new HashMap<>(explanation);
            ePrime.remove(var);
            if (this.isExplanation(ePrime, variable, value)) {
                explanation = new HashMap<>(ePrime);
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

    private Set<Map<Variable, String>> generateSingletons(Map<Variable, String> map) {
        Set<Map<Variable, String>> singletonsMap = new HashSet<>();
        Map<Variable, String> mapTemp;
        for (Variable var : map.keySet()) {
            mapTemp = new HashMap<>();
            mapTemp.put(var, map.get(var));
            singletonsMap.add(mapTemp);
        }
        return singletonsMap;
    }

    private Set<Map<Variable, String>> findExplanations(Map<Variable, String> choices, Variable variable, String value, boolean allExplanation) {
        Set<Map<Variable, String>> finalRes = new HashSet<>();
        Set<Map<Variable, String>> res = new HashSet<>();
        Set<Map<Variable, String>> resPrec;
        Map<Variable, String> copyPart;
        res.add(new HashMap<>());
        
        for (int size = 1; size<choices.size(); size++) {
            resPrec = new HashSet<>(res);
            res = new HashSet<>();
            System.out.println("size : " + size);
            for (Map<Variable, String> part : resPrec) {
                for (Variable var : choices.keySet()) {
                    copyPart = new HashMap<>(part);
                    copyPart.put(var, choices.get(var));
                    System.out.println("copyPart " + copyPart);
                    if ((part.isEmpty() || var.compareTo(Collections.max(part.keySet())) > 0) && this.isExplanation(copyPart, variable, value)) {
                        if (allExplanation) {
                            res.add(copyPart);
                        } else {
                            finalRes = new HashSet<>();
                            finalRes.add(copyPart);
                            return finalRes;
                        }
                    }
                }
            }
            finalRes.addAll(res);
        }
        finalRes.add(new HashMap<>(choices));
        return finalRes;
    }
}
