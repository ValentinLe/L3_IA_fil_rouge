
package examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import representations.*;

public class Examples {
    
    private Set<String> domaineCouleur;
    private Set<String> domaineComposants;
    private Set<Variable> couleur;
    
    public Examples() {
        this.domaineCouleur = new HashSet<>(Arrays.asList("rouge", "noir", "blanc"));
        this.domaineComposants = new HashSet<>(Arrays.asList("toit", "capot", "hayon"));
        this.couleur = new HashSet<>(Arrays.asList(
                new Variable("toit",domaineCouleur), 
                new Variable("capot",domaineCouleur),
                new Variable("hayon",domaineCouleur)));
    }

    public Set<String> getDomaineCouleur() {
        return domaineCouleur;
    }

    public Set<String> getDomaineComposants() {
        return domaineComposants;
    }

    public Set<Variable> getCouleur() {
        return couleur;
    }
    
    // Voitures
    
    public Map<Variable, String> getVoiture1() {
        Map<Variable,String> voiture = new HashMap<>();
        
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("blanc", "noir", "rouge"));

        Iterator iter = this.couleur.iterator();
        int i = 0;
        while(iter.hasNext()){
            voiture.put((Variable) iter.next(), valeurs.get(i));
            i++;
        }
        return voiture;
    }
    
    public Map<Variable, String> getVoiture2() {
        Map<Variable,String> voiture = new HashMap<>();
        
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("blanc", "blanc", "rouge"));

        Iterator iter = this.couleur.iterator();
        int i = 0;
        while(iter.hasNext()){
            voiture.put((Variable) iter.next(), valeurs.get(i));
            i++;
        }
        return voiture;
    }
    
    public Map<Variable, String> getVoiture3() {
        Map<Variable,String> voiture = new HashMap<>();
        
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("blanc", "blanc", "blanc"));

        Iterator iter = this.couleur.iterator();
        int i = 0;
        while(iter.hasNext()){
            voiture.put((Variable) iter.next(), valeurs.get(i));
            i++;
        }
        return voiture;
    }
    
    // Rules
    
    public Rule getRule1() {
        // premisse
        
        Map<Variable,String> premisse = new HashMap<>();
        
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("blanc", "noir","rouge"));

        Iterator iter2 = couleur.iterator();
        int j = 0;
        while(iter2.hasNext()){
            premisse.put((Variable) iter2.next(), valeurs.get(j));
            j++;
        }
        
        // Conclusion
        
        Map<Variable,String> conclusion = new HashMap<>();
        
        ArrayList<String> valeurs2 = new ArrayList<>(Arrays.asList("blanc", "rouge","rouge"));

        Iterator iter3 = couleur.iterator();
        int k = 0;
        while(iter3.hasNext()){
            conclusion.put((Variable) iter3.next(), valeurs2.get(k));
            k++;
        }
        return new Rule(premisse, conclusion);
    }
    
    public Rule getRule2() {
        // premisse
        
        Map<Variable,String> premisse = new HashMap<>();
        
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("blanc", "noir","rouge"));

        Iterator iter2 = couleur.iterator();
        int j = 0;
        while(iter2.hasNext()){
            premisse.put((Variable) iter2.next(), valeurs.get(j));
            j++;
        }
        
        // Conclusion
        
        Map<Variable,String> conclusion = new HashMap<>();
        
        ArrayList<String> valeurs2 = new ArrayList<>(Arrays.asList("blanc", "noir","rouge"));

        Iterator iter3 = couleur.iterator();
        int k = 0;
        while(iter3.hasNext()){
            conclusion.put((Variable) iter3.next(), valeurs2.get(k));
            k++;
        }
        return new Rule(premisse, conclusion);
    }
    
    public Rule getRule3() {
        // premisse
        
        Map<Variable,String> premisse = new HashMap<>();
        
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("blanc", "noir","rouge"));

        Iterator iter2 = couleur.iterator();
        int j = 0;
        while(iter2.hasNext()){
            premisse.put((Variable) iter2.next(), valeurs.get(j));
            j++;
        }
        
        // Conclusion
        
        Map<Variable,String> conclusion = new HashMap<>();
        
        ArrayList<String> valeurs2 = new ArrayList<>(Arrays.asList("blanc", "noir","rouge"));

        Iterator iter3 = couleur.iterator();
        int k = 0;
        while(iter3.hasNext()){
            conclusion.put((Variable) iter3.next(), valeurs2.get(k));
            k++;
        }
        return new Rule(premisse, conclusion);
    }
    
    // Disjonctions
    
    public Disjunction getDisjunction1() {
        Map<Variable,String> conclusion = new HashMap<>();
        
        ArrayList<String> valeurs2 = new ArrayList<>(Arrays.asList("blanc", "noir","rouge"));

        Iterator iter3 = couleur.iterator();
        int k = 0;
        while(iter3.hasNext()){
            conclusion.put((Variable) iter3.next(), valeurs2.get(k));
            k++;
        }
        return new Disjunction(conclusion);
    }
    
    // IncopabilityConstrain
    
    public IncompatibilityConstraint getIncompatibility1() {
        Map<Variable,String> premisse = new HashMap<>();
        
        ArrayList<String> valeurs2 = new ArrayList<>(Arrays.asList("blanc", "noir","rouge"));

        Iterator iter3 = couleur.iterator();
        int k = 0;
        while(iter3.hasNext()){
            premisse.put((Variable) iter3.next(), valeurs2.get(k));
            k++;
        }
        return new IncompatibilityConstraint(premisse);
    }
}
