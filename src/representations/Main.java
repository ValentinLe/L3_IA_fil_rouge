
package representations;

import java.util.*;
import examples.*;

public class Main {

    /**
     * @param args the command line arguments 
     */
    public static void main(String[] args) {

        Examples ex = new Examples();


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

    }

}
