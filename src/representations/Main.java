
package representations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Set<String> couleurs = new HashSet<>(Arrays.asList("rouge", "noir","blanc"));
        
        Variable myVar = new Variable("couleur_toit",couleurs);
        System.out.println(myVar);
        
    }
    
}
