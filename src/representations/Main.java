
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
        
    }
    
}
