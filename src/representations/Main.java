
package representations;

import java.util.*;
import examples.*;

/**
 * Classe de test sur la satisfaction de contraintes
 * 
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Examples ex = new Examples();

        List<Map<Variable, String>> voitures = new ArrayList<>();
        voitures.add(ex.getVoiture1());
        voitures.add(ex.getVoiture2());
        voitures.add(ex.getVoiture3());
        voitures.add(ex.getVoiture4());

        List<Constraint> constrains = new ArrayList<>();
        constrains.add(ex.getExemple1());
        constrains.add(ex.getExemple2());
        constrains.add(ex.getExemple3());
        constrains.add(ex.getExemple4());

        // affichage des contraintes
        System.out.println("\nContraintes :");
        int n = 1;
        for (Constraint c : constrains) {
            System.out.println("c" + n + " : " + c);
            n++;
        }

        // affichage des voitures et de leur satisfaction sur chaque contraintes
        for (Map<Variable, String> voiture : voitures) {
            System.out.println("\n-----------------------------");
            System.out.println("Voiture : " + voiture + "\n");
            n = 1;
            System.out.println("Satisfaction : ");
            for (Constraint c : constrains) {
                System.out.println("c" + n + " : " + c.isSatisfiedBy(voiture));
                n++;
            }
        }

    }
}
