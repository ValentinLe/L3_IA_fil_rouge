
package representations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Set<String> domaineCouleur = new HashSet<>(Arrays.asList("rouge", "noir","blanc"));
        Set<String> domaineComposants = new HashSet<>(Arrays.asList("toit", "capot","hayon"));
        Set<Variable> couleur = new HashSet<>(Arrays.asList(
                new Variable("toit",domaineCouleur), 
                new Variable("capot",domaineCouleur),
                new Variable("hayon",domaineCouleur)));
        
        // voiture
        Map<Variable,String> voiture = new HashMap<>();
        
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("blanc", "noir","rouge"));

        Iterator iter = couleur.iterator();
        int i = 0;
        while(iter.hasNext()){
            voiture.put((Variable) iter.next(), valeurs.get(i));
            i++;
        }
        
        System.out.println("Voiture: " + voiture);
        
        // rule
        Map<Variable,String> premisse = new HashMap<>();
        
        ArrayList<String> valeurs2 = new ArrayList<>(Arrays.asList("blanc", "noir","rouge"));

        Iterator iter2 = couleur.iterator();
        int j = 0;
        while(iter2.hasNext()){
            premisse.put((Variable) iter2.next(), valeurs2.get(j));
            j++;
        }
        
        System.out.println("Premisse: " + premisse);
        
        // conclusion
        Map<Variable,String> conclusion = new HashMap<>();
        
        ArrayList<String> valeurs3 = new ArrayList<>(Arrays.asList("blanc", "noir","rouge"));

        Iterator iter3 = couleur.iterator();
        int k = 0;
        while(iter3.hasNext()){
            conclusion.put((Variable) iter3.next(), valeurs3.get(k));
            k++;
        }
        
        System.out.println("conclusion: " + conclusion);
        
        Rule r1 = new Rule(premisse, conclusion);
        System.out.println(r1.isSatisfiedBy(voiture));
        
        
        // Test de AllEqualConstraint
        
        // voiture
        Map<Variable,String> voiture2test = new HashMap<>();
        
        ArrayList<String> valeurs2test = new ArrayList<>(Arrays.asList("blanc", "blanc","rouge"));

        Iterator iter2test = couleur.iterator();
        int l = 0;
        while(iter2test.hasNext()){
            voiture2test.put((Variable) iter2test.next(), valeurs2test.get(l));
            l++;
        }
        
        // voiture
        Map<Variable,String> voiture3test = new HashMap<>();
        
        ArrayList<String> valeurs3test = new ArrayList<>(Arrays.asList("blanc", "blanc","blanc"));

        Iterator iter3test = couleur.iterator();
        int m = 0;
        while(iter3test.hasNext()){
            voiture3test.put((Variable) iter3test.next(), valeurs3test.get(m));
            m++;
        }
        
        System.out.println("Voiture: " + voiture2test);
        System.out.println("Voiture: " + voiture3test);
        
        AllEqualConstraint equalConstraint = new AllEqualConstraint(couleur);
        System.out.println(equalConstraint);
        System.out.println("\nLa voiture suivante ne devrait pas satisfaire la contrainte:");
        System.out.print(equalConstraint.isSatisfiedBy(voiture2test));
        System.out.println("\nCette seconde voiture devrait satisfaire la contrainte:");
        System.out.print(equalConstraint.isSatisfiedBy(voiture3test)+"\n");
        
    }
    
}
