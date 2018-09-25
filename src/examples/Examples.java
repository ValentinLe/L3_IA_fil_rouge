
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
    
    private Map<String, Variable> variables;

    /**
     * Build all components on their values possible of a car
     */
    public Examples() {
        // composants
        this.composants = new ArrayList<>(Arrays.asList("toit", "capot", "hayon","droit","gauche"));
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
        
        this.variables = new HashMap<>();
        for (Variable var : this.couleur) {
            this.variables.put(var.getName(), var);
        }
        
        for(Variable var : this.boolVariable) {
            this.variables.put(var.getName(), var);
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
        
        ArrayList<String> comp = new ArrayList<>(Arrays.asList(
                "toit","hayon","droit","gauche","toit ouvrant","sono"
        ));
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("noir", "noir","blanc","blanc","true","true"));

        for(int i = 0; i<comp.size(); i++) {
            voiture.put(this.variables.get(comp.get(i)), valeurs.get(i));
        }
        return voiture;
    }

    /**
     * Get Example of car
     * @return example of car
     */
    public Map<Variable, String> getVoiture2() {
        Map<Variable,String> voiture = initVoiture();

        ArrayList<String> comp = new ArrayList<>(Arrays.asList(
                "toit","hayon","droit","gauche","toit ouvrant","sono"
        ));
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("noir", "blanc", "noir","noir","blanc","true","true"));

        for(int i = 0; i<comp.size(); i++) {
            voiture.put(this.variables.get(comp.get(i)), valeurs.get(i));
        }
        return voiture;
    }

    /**
     * Get Example of car
     * @return example of car
     */
    public Map<Variable, String> getVoiture3() {
        Map<Variable,String> voiture = initVoiture();

        ArrayList<String> comp = new ArrayList<>(Arrays.asList(
                "toit","hayon","droit","gauche","toit ouvrant","sono"
        ));
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("noir", "noir", "blanc","balnc","blanc","true","true"));

        for(int i = 0; i<comp.size(); i++) {
            voiture.put(this.variables.get(comp.get(i)), valeurs.get(i));
        }
        return voiture;
    }

    /**
     * Get Example of car
     * @return example of car
     */
    public Map<Variable, String> getVoiture4() {
        Map<Variable,String> voiture = initVoiture();

        ArrayList<String> comp = new ArrayList<>(Arrays.asList(
                "toit","hayon","droit","gauche","toit ouvrant","sono"
        ));
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("rouge", "blanc", "rouge","noir","noir","false","true"));

        for(int i = 0; i<comp.size(); i++) {
            voiture.put(this.variables.get(comp.get(i)), valeurs.get(i));
        }
        return voiture;
    }

    // constraints
    
    /**
     * Get Example of constraint : toit = hayon = capot
     * @return example of constraint
     */
    public AllEqualConstraint getExemple1() {
      Set<Variable> allEqual = new HashSet<>();
      
      ArrayList<String> comp = new ArrayList<>(Arrays.asList(
                "toit", "capot", "hayon"
        ));
      
      for(String str : comp) {
          allEqual.add(this.variables.get(str));
      }
      return new AllEqualConstraint(allEqual);
    }

    /**
     * Get Example of constraint : (gauche = toit) ou (droit = toit)
     * @return example of constraint
     */
    public ConstraintOr getExemple2() {
      Set<Variable> setEqual = new HashSet<>();
      setEqual.add(this.variables.get("gauche"));
      setEqual.add(this.variables.get("toit"));
      
      Set<Variable> setEqual2 = new HashSet<>();
      setEqual2.add(this.variables.get("droit"));
      setEqual2.add(this.variables.get("toit"));
      
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
        comp.add(this.variables.get("droit"));
        comp.add(this.variables.get("gauche"));

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
      Set<Variable> nEqual = new HashSet<>();
      nEqual.add(this.variables.get("toit ouvrant"));
      nEqual.add(this.variables.get("sono"));

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
     
      Set<Variable> allEqual = new HashSet<>();
      allEqual.add(this.variables.get("hayon"));
      allEqual.add(this.variables.get("gauche"));
      AllEqualConstraint all = new AllEqualConstraint(allEqual);
      
      return all;
    }
    
    /**
     * Get Example of constraint : hayon != droit != porte
     * @return example of constraint
     */
    public AllDifferentConstraint getExemple6(){
     
      Set<Variable> allEqual = new HashSet<>();
      allEqual.add(this.variables.get("hayon"));
      allEqual.add(this.variables.get("droit"));
      AllDifferentConstraint all = new AllDifferentConstraint(allEqual);
      
      return all;
    }
}
