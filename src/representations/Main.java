
package representations;

import java.util.Map;
import examples.Examples;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Examples ex = new Examples();
        
        // voiture
        Map<Variable,String> voiture = ex.getVoiture1();
        
        System.out.println("Voiture: " + voiture);
        
        // rule
        Rule r1 = ex.getRule1();
        System.out.println(r1);
        System.out.println(r1.isSatisfiedBy(voiture));
        
        // Test de AllEqualConstraint
        
        // voiture
        Map<Variable,String> voiture2test = ex.getVoiture2();
        
        // voiture
        Map<Variable,String> voiture3test = ex.getVoiture3();
        
        System.out.println("Voiture: " + voiture2test);
        System.out.println("Voiture: " + voiture3test);
        
        AllEqualConstraint equalConstraint = new AllEqualConstraint(ex.getCouleur());
        System.out.println(equalConstraint);
        System.out.println("\nLa voiture suivante ne devrait pas satisfaire la contrainte:");
        System.out.print(equalConstraint.isSatisfiedBy(voiture2test));
        System.out.println("\nCette seconde voiture devrait satisfaire la contrainte:");
        System.out.print(equalConstraint.isSatisfiedBy(voiture3test)+"\n");
        
    }
    
}
