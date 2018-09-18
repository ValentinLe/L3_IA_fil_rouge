
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
    private Set<String> domaineComposants2;
    private Set<Variable> couleur;
    private Set<String> domaineBool;
    private Set<Variable> boolVariable;

    public Examples() {
        this.domaineCouleur = new HashSet<>(Arrays.asList("rouge", "noir", "blanc"));
        this.domaineBool = new HashSet<>(Arrays.asList("true","false"));
        this.domaineComposants = new HashSet<>(Arrays.asList("toit", "capot", "hayon"));
        this.domaineComposants2 = new HashSet<>(Arrays.asList("toit ouvrant", "sono"));
        this.couleur = new HashSet<>();
        this.boolVariable = new HashSet<>();
        for(String str : domaineComposants) {
          this.couleur.add(new Variable(str,domaineComposants));
        }
        
        for(String str : domaineComposants2) {
          this.boolVariable.add(new Variable(str,domaineComposants2));
        }
        
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
    
    public Map<Variable, String> getVoiture4() {
        Map<Variable,String> voiture = new HashMap<>();

        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("true", "false"));

        Iterator iter = this.boolVariable.iterator();
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
        return new Rule(this.couleur,premisse, conclusion);
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
        return new Rule(this.couleur,premisse, conclusion);
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
        return new Rule(this.couleur,premisse, conclusion);
    }

    // Disjonctions

    public Disjunction getDisjunction1() {
        Map<Variable,String> conclusion = new HashMap<>();

        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("blanc", "rouge","blanc"));

        Iterator iter = couleur.iterator();
        int k = 0;
        while(iter.hasNext()){
            conclusion.put((Variable) iter.next(), valeurs.get(k));
            k++;
        }
        return new Disjunction(this.couleur,conclusion);
    }

    // IncopabilityConstrain

    public IncompatibilityConstraint getIncompatibility1() {
        Map<Variable,String> premisse = new HashMap<>();

        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("blanc", "noir","rouge"));

        Iterator iter = couleur.iterator();
        int k = 0;
        while(iter.hasNext()){
            premisse.put((Variable) iter.next(), valeurs.get(k));
            k++;
        }
        return new IncompatibilityConstraint(this.couleur,premisse);
    }

    public IncompatibilityConstraint getIncompatibility2() {
        Map<Variable,String> premisse = new HashMap<>();

        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("blanc", "rouge","noir"));

        Iterator iter = couleur.iterator();
        int k = 0;
        while(iter.hasNext()){
            premisse.put((Variable) iter.next(), valeurs.get(k));
            k++;
        }
        return new IncompatibilityConstraint(this.couleur, premisse);
    }

    public IncompatibilityConstraint getIncompatibility3() {
        Map<Variable,String> premisse = new HashMap<>();

        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("noir", "blanc","noir"));

        Iterator iter = couleur.iterator();
        int k = 0;
        while(iter.hasNext()){
            premisse.put((Variable) iter.next(), valeurs.get(k));
            k++;
        }
        return new IncompatibilityConstraint(this.couleur, premisse);
    }

    // exemple ecmapus

    public AllEqualConstraint getExemple1() {
      Set<Variable> allEqual = new HashSet<>(Arrays.asList(
                new Variable("toit",domaineCouleur),
                new Variable("capot",domaineCouleur),
                new Variable("hayon",domaineCouleur)));
      return new AllEqualConstraint(allEqual);
    }
      
    // 
    public Disjunction getExemple2() {
      Set<Variable> allEqual = new HashSet<>(Arrays.asList(
                new Variable("gauche",domaineCouleur),
                new Variable("toit",domaineCouleur)));
      Set<Variable> allEqual2 = new HashSet<>(Arrays.asList(
                new Variable("droit",domaineCouleur),
                new Variable("toit",domaineCouleur)));
      AllEqualConstraint all1 = new AllEqualConstraint(allEqual);
      AllEqualConstraint all2 = new AllEqualConstraint(allEqual2);
      Set<Variable> scope = all1.getScope();
      scope.containsAll(all2.getScope());
      return new Disjunction(this.couleur,null);
    }
    
    public Disjunction getExemple4(){
      Set<Variable> nEqual = new HashSet<>(Arrays.asList(
              new Variable("sono", this.domaineBool),
              new Variable("toit ouvrant", this.domaineBool)
      ));
      
      Map<Variable,String> conclusion = new HashMap<>();
      Iterator iter = nEqual.iterator();
      
      while(iter.hasNext()){
        conclusion.put((Variable) iter.next(), "true");
      }
      return new Disjunction(nEqual,conclusion);
    }
}
