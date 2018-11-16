
package diagnosis;

import java.util.*;
import representations.*;
import ppc.*;
import examples.*;

public class Main {
    public static void main(String[] agrs) {

        Examples ex = new Examples();

        Set<Variable> variables = ex.getVariables();

        Constraint c1 = ex.getExemple1();
        Constraint c2 = ex.getExemple2();
        Constraint c3 = ex.getExemple3();
        Constraint c4 = ex.getExemple4();

        Set<Constraint> constraints = new HashSet<>();
        constraints.add(c1);
        constraints.add(c2);
        constraints.add(c3);
        constraints.add(c4);

        HeuristicVariable heuristic = new ConstraintMaxHeuristic();

        Backtracking back = new Backtracking(variables, constraints, heuristic);

        Diagnoser diagnoser = new Diagnoser(back);

        Map<Variable, String> choices = new HashMap<>();
        choices.put(ex.getVariableWithName("capot"), "rouge");
        choices.put(ex.getVariableWithName("droit"), "noir");
        choices.put(ex.getVariableWithName("hayon"), "rouge");

        Map<Variable, String> explication = diagnoser.findMinimalInclusionExplanation(choices, ex.getVariableWithName("gauche"), "noir");
        System.out.println("minimal inclusion explication : " + explication); // droit=noir
        
        Set<Map<Variable, String>> set = diagnoser.findAllExplanations(choices, ex.getVariableWithName("gauche"), "noir");
        System.out.println("\nall explanations : " + set);
        
        Map<Variable, String> minC = diagnoser.findMinimalCardinalExplanation(choices, ex.getVariableWithName("gauche"), "noir");
        System.out.println("\nminimal cardinal explanation : " + minC);
    }
}
