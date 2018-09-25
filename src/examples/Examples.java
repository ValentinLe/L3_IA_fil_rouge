
package examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import representations.*;

/**
 * This class is a build constraints and cars examples
 * 
 */
public class Examples {
    /** components of color domain */
    private ArrayList<String> composants;
    /** component of boolean domain */
    private ArrayList<String> composants2;

    /** variables of components with color domain */
    private ArrayList<Variable> couleur;
    /** variables of components with boolean domain */
    private ArrayList<Variable> boolVariable;

    /** domain of color */
    private Set<String> domaineCouleur;
    /** domain of boolean */
    private Set<String> domaineBool;

    /**
     * Build all components on their values possible of a car
     */
    public Examples() {
        // composants
        this.composants = new ArrayList<>(Arrays.asList("toit", "capot", "hayon","droit","gauche","porte"));
        this.composants2 = new ArrayList<>(Arrays.asList("toit ouvrant", "sono"));

        // domaines
        this.domaineCouleur = new HashSet<>(Arrays.asList("rouge", "noir", "blanc"));
        this.domaineBool = new HashSet<>(Arrays.asList("true","false"));

        // Set<Variable>
        this.couleur = new ArrayList<>();
        this.boolVariable = new ArrayList<>();
        for(String str : composants) {
          this.couleur.add(new Variable(str,new HashSet<>(domaineCouleur)));
        }

        for(String str : composants2) {
          this.boolVariable.add(new Variable(str,new HashSet<>(domaineBool)));
        }
    }
    
    /**
     * Get all variables with color and boolean domain
     * @return set of variables
     */
    public Set<Variable> getVariables() {
        Set<Variable> vars = new HashSet<>();
        vars.addAll(this.couleur);
        vars.addAll(this.boolVariable);
        return vars;
    }
    
    /**
     * Get components with color domain
     * @return set of components
     */
    public Set<String> getComposants() {
        return new HashSet<>(this.composants);
    }

    /**
     * Get components with boolean domain
     * @return set of components
     */
    public Set<String> getComposants2() {
        return new HashSet<>(this.composants2);
    }

    /**
     * Get domain of color
     * @return the color dommain
     */
    public Set<String> getDomaineCouleur() {
        return this.domaineCouleur;
    }

    /**
     * Get domain of boolean
     * @return the boolean domain
     */
    public Set<String> getDomaineBool() {
        return this.domaineBool;
    }

    // Voitures

    /**
     * Initialize all varaiables to null in a car
     * @return example the car initialized
     */
    public Map<Variable,String> initVoiture() {
        Map<Variable,String> voiture = new HashMap<>();

        for(int i = 0; i<this.couleur.size(); i++) {
            voiture.put(this.couleur.get(i), null);
        }
        for(int i = 0; i<this.boolVariable.size(); i++) {
            voiture.put(this.boolVariable.get(i), null);
        }
        return voiture;
    }

    /**
     * Get Example of car
     * @return example of car
     */
    public Map<Variable, String> getVoiture1() {
        Map<Variable,String> voiture = initVoiture();

        ArrayList<Variable> comp = new ArrayList<>(Arrays.asList(
                new Variable("toit",this.domaineCouleur),
                new Variable("hayon",this.domaineCouleur),
                new Variable("droit",this.domaineCouleur),
                new Variable("porte",this.domaineCouleur),
                new Variable("gauche",this.domaineCouleur),
                new Variable("toit ouvrant",this.domaineBool),
                new Variable("sono",this.domaineBool)
        ));
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("noir", "noir","blanc","rouge","blanc","true","true"));

        for(int i = 0; i<comp.size(); i++) {
            voiture.put(comp.get(i), valeurs.get(i));
        }
        return voiture;
    }

    /**
     * Get Example of car
     * @return example of car
     */
    public Map<Variable, String> getVoiture2() {
        Map<Variable,String> voiture = initVoiture();

        ArrayList<Variable> comp = new ArrayList<>(Arrays.asList(
                new Variable("toit",this.domaineCouleur),
                new Variable("droit",this.domaineCouleur),
                new Variable("gauche",this.domaineCouleur),
                new Variable("capot",this.domaineCouleur),
                new Variable("hayon",this.domaineCouleur),
                new Variable("toit ouvrant",this.domaineBool),
                new Variable("sono",this.domaineBool)
        ));
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("noir", "blanc", "noir","noir","blanc","true","true"));

        for(int i = 0; i<comp.size(); i++) {
            voiture.put(comp.get(i), valeurs.get(i));
        }
        return voiture;
    }

    /**
     * Get Example of car
     * @return example of car
     */
    public Map<Variable, String> getVoiture3() {
        Map<Variable,String> voiture = initVoiture();

        ArrayList<Variable> comp = new ArrayList<>(Arrays.asList(
                new Variable("toit",this.domaineCouleur),
                new Variable("capot",this.domaineCouleur),
                new Variable("hayon",this.domaineCouleur),
                new Variable("droit",this.domaineCouleur),
                new Variable("gauche",this.domaineCouleur),
                new Variable("toit ouvrant",this.domaineBool),
                new Variable("sono",this.domaineBool)
        ));
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("noir", "noir", "blanc","balnc","blanc","true","true"));

        Iterator<Variable> iter = comp.iterator();
        int i = 0;
        while(iter.hasNext()){
            voiture.put(iter.next(), valeurs.get(i));
            i++;
        }
        return voiture;
    }

    /**
     * Get Example of car
     * @return example of car
     */
    public Map<Variable, String> getVoiture4() {
        Map<Variable,String> voiture = initVoiture();

        ArrayList<Variable> comp = new ArrayList<>(Arrays.asList(
                new Variable("toit",this.domaineCouleur),
                new Variable("capot",this.domaineCouleur),
                new Variable("hayon",this.domaineCouleur),
                new Variable("droit",this.domaineCouleur),
                new Variable("gauche",this.domaineCouleur),
                new Variable("toit ouvrant",this.domaineBool)
        ));
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("rouge", "blanc", "rouge","noir","noir","false","true"));

        for(int i = 0; i<comp.size(); i++) {
            voiture.put(comp.get(i), valeurs.get(i));
        }
        return voiture;
    }

    // constraints
    
    /**
     * Get Example of constraint : toit = hayon = capot
     * @return example of constraint
     */
    public AllEqualConstraint getExemple1() {
      Set<Variable> allEqual = new HashSet<>(Arrays.asList(
                new Variable("toit",domaineCouleur),
                new Variable("capot",domaineCouleur),
                new Variable("hayon",domaineCouleur)));
      return new AllEqualConstraint(allEqual);
    }

    /**
     * Get Example of constraint : (gauche = toit) ou (droit = toit)
     * @return example of constraint
     */
    public ConstraintOr getExemple2() {
      Set<Variable> setEqual = new HashSet<>(Arrays.asList(
                new Variable("gauche",domaineCouleur),
                new Variable("toit",domaineCouleur)));
      Set<Variable> setEqual2 = new HashSet<>(Arrays.asList(
                new Variable("droit",domaineCouleur),
                new Variable("toit",domaineCouleur)));
      AllEqualConstraint all1 = new AllEqualConstraint(setEqual);
      AllEqualConstraint all2 = new AllEqualConstraint(setEqual2);
      return new ConstraintOr(all1,all2);
    }

    /**
     * Get Example of constraint : !(droit="noir" & gauche="noir")
     * @return example of constraint
     */
    public IncompatibilityConstraint getExemple3(){

        Map<Variable,String> constraint = new HashMap<>();

        ArrayList<Variable> comp = new ArrayList<>();
        comp.add(new Variable("droit",this.domaineCouleur));
        comp.add(new Variable("gauche",this.domaineCouleur));

        ArrayList<String> val = new ArrayList<>();
        val.add("noir");
        val.add("noir");

        constraint.put(comp.get(0), val.get(0));
        constraint.put(comp.get(1), val.get(1));

        IncompatibilityConstraint compNoir = new IncompatibilityConstraint(new HashSet<>(comp), constraint);

        return compNoir;
    }

    /**
     * Get Example of constraint : !(sono="true" & "toit ouvrant="true")
     * @return example of constraint
     */
    public IncompatibilityConstraint getExemple4(){
      Set<Variable> nEqual = new HashSet<>(Arrays.asList(
              new Variable("sono", this.domaineBool),
              new Variable("toit ouvrant", this.domaineBool)
      ));

      Map<Variable,String> conclusion = new HashMap<>();
      Iterator<Variable> iter = nEqual.iterator();

      while(iter.hasNext()){
        conclusion.put(iter.next(), "true");
      }

      return new IncompatibilityConstraint(nEqual,conclusion);
    }
    
    /**
     * Get Example of constraint : hayon = gauche
     * @return example of constraint
     */
    public AllEqualConstraint getExemple5(){
     
      Set<Variable> allEqual = new HashSet<>(Arrays.asList(
                new Variable("hayon",domaineCouleur),
                new Variable("gauche",domaineCouleur)));
      AllEqualConstraint all = new AllEqualConstraint(allEqual);
      
      return all;
    }
    
    /**
     * Get Example of constraint : hayon != droit != porte
     * @return example of constraint
     */
    public AllDifferentConstraint getExemple6(){
     
      Set<Variable> allEqual = new HashSet<>(Arrays.asList(
                new Variable("hayon",domaineCouleur),
                new Variable("droit",domaineCouleur),
                new Variable("porte",domaineCouleur)));
      AllDifferentConstraint all = new AllDifferentConstraint(allEqual);
      
      return all;
    }
}
