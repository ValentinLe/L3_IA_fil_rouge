/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package representations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author quentindeme
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Set<String> couleurs = new HashSet<>(Arrays.asList("rouge", "noir","blanc"));
        
        Variable myVar = new Variable("couleur_toit",couleurs);
        System.out.println(myVar);
        
    }
    
}
