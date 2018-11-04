
package ppc;

import java.util.*;
import examples.*;
import representations.*;

public class Main {
    public static void main(String[] args) {

        Examples ex = new Examples();

        Set<Variable> variables = ex.getVariables();

        AllEqualConstraint c1 = ex.getExemple1();
        ConstraintOr c2 = ex.getExemple2();
        IncompatibilityConstraint c3 = ex.getExemple3();
        IncompatibilityConstraint c4 = ex.getExemple4();
        AllDifferentConstraint c5 = ex.getExemple5();
        Disjunction c6 = ex.getExemple6();

        ArrayList<Constraint> constraints = new ArrayList<>();
        constraints.add(c1);
        constraints.add(c2);
        constraints.add(c3);
        constraints.add(c4);
        //constraints.add(c5);
        //constraints.add(c6);

        HeuristicVariable heuristic = new ConstraintMaxHeuristic();
        Backtracking back = new Backtracking(variables, constraints, heuristic);
        System.out.println(back.getStringConstraints());

        Map<Variable, String> voiture1 = back.solutionFilter();
        System.out.println("backtrack : " + voiture1);

        Set<Map<Variable, String>> sols = back.solutionsFilter();

        if (sols != null) {
            for (Map<Variable, String> car : sols) {
                System.out.println(car + "\n");
            }
            System.out.println("\n/\\ SOLUTIONS TROUVEES /\\\n");
            System.out.println("Nombre de solutions : " + sols.size());
            System.out.println("Heuristic utilisée : " + back.getHeuristic());
            System.out.println("noeuds parcourus : " + back.getNbNode());
        }


        /*
        Map<Variable, String> voiture = ex.getVoiture1();
        Map<Variable, Set<String>> map = back.getMapVariableNotAssigned(voiture);
        System.out.println("VOITURE : " + voiture);
        System.out.println("MAP AV " + map);
        System.out.println(c3.filtrer(voiture, map));
        System.out.println("MAP ap1 " + map);
        System.out.println(c3.filtrer(voiture, map));
        System.out.println("MAP ap2 " + map); 
        */
    }
}
