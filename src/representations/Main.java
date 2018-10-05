
package representations;

import java.util.Map;
import examples.Examples;
import java.util.ArrayList;
import java.util.Set;
import ppc.Backtracking;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Examples ex = new Examples();


        // voiture
        /*
        Map<Variable,String> voiture = ex.getVoiture1();
        System.out.println(voiture);

        AllEqualConstraint allE1 = ex.getExemple1();
        System.out.println("Voiture : " + voiture);
        System.out.println(allE1 + " --> " + allE1.isSatisfiedBy(voiture) + "\n");

        //--------------------------------------------------------------//

        Map<Variable,String> voiture2 = ex.getVoiture2();

        ConstraintOr dgt = ex.getExemple2();
        System.out.println("Voiture : " + voiture2);
        System.out.println(dgt + " --> " + dgt.isSatisfiedBy(voiture2) + "\n");

        //--------------------------------------------------------------//

        Map<Variable,String> voiture3 = ex.getVoiture3();

        IncompatibilityConstraint cNonEqu = ex.getExemple3();
        System.out.println("Voiture : " + voiture3);
        System.out.println(cNonEqu + " --> " + cNonEqu.isSatisfiedBy(voiture3) + "\n");

        //--------------------------------------------------------------//

        Map<Variable,String> voiture4 = ex.getVoiture4();

        IncompatibilityConstraint nEqu = ex.getExemple4();
        System.out.println("Voiture : " + voiture4);
        System.out.println(nEqu + " --> " + nEqu.isSatisfiedBy(voiture4) + "\n");

        */
        Set<Variable> variables = ex.getVariables();

        AllEqualConstraint c1 = ex.getExemple1();
        ConstraintOr c2 = ex.getExemple2();
        IncompatibilityConstraint c3 = ex.getExemple3();
        IncompatibilityConstraint c4 = ex.getExemple4();
        AllEqualConstraint c5 = ex.getExemple5();
        AllDifferentConstraint c6 = ex.getExemple6();
        Disjunction c7 = ex.getExemple7();

        ArrayList<Constraint> constraints = new ArrayList<>();
        constraints.add(c1);
        //constraints.add(c2);
        constraints.add(c3);
        constraints.add(c4);
        //constraints.add(c5);
        //constraints.add(c6);
        //constraints.add(c7);


        Backtracking back = new Backtracking(variables, constraints);
        /*
        Map<Variable, String> voiture1 = back.solution();
        System.out.println("backtrack : " + voiture1);
        */
        
        Set<Map<Variable, String>> sols = back.solutionsFilter();
        
        
        System.out.println("Nombre de solutions : " + sols.size());
        for (Map<Variable, String> voiture : sols) {
            System.out.println(voiture + "\n");
        }
        
        /*
        Map<Variable, String> voiture = ex.getVoiture1();
        Map<Variable, Set<String>> map = back.getMapVariableNotAssigned(voiture);
        System.out.println(voiture);
        System.out.println(map);
        System.out.println(c2.filtrer(voiture, map));
        System.out.println(map);
        System.out.println(c2.filtrer(voiture, map));
        System.out.println(map);
        */

    }

}
