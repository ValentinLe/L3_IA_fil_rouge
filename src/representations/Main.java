
package representations;

import java.util.Map;
import examples.Examples;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import ppc.Backtracking;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Examples ex = new Examples();


        // voiture
        
        Map<Variable,String> voiture = ex.getVoiture1();

        AllEqualConstraint allE1 = ex.getExemple6();
        System.out.println("Voiture : " + voiture);
        System.out.println(allE1 + " --> " + allE1.isSatisfiedBy(voiture) + "\n");
        
        //--------------------------------------------------------------//
        /*
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
        AllEqualConstraint c6 = ex.getExemple6();

        Set<Constraint> constraints = new HashSet<>();
        constraints.add(c1);
        constraints.add(c2);
        constraints.add(c3);
        constraints.add(c4);
        constraints.add(c5);
        //constraints.add(c6);
        
        
        Backtracking back = new Backtracking(variables, constraints);
        Map<Variable, String> voiture1 = back.solution();
        System.out.println("backtrack : " + voiture1);

    }

}
