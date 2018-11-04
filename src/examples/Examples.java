
package examples;

import java.util.*;
import representations.*;

/**
 * This class builds constraints and car examples
 *
 */
public class Examples {

    /** this variables contains components with color domain */
    private ArrayList<Variable> couleur;
    /** this variables contains components with boolean domain */
    private ArrayList<Variable> boolVariable;

    /** color's domain */
    private Set<String> domaineCouleur;
    /** boolean's domain */
    private Set<String> domaineBool;
    /** map of variables accessed by name */
    private Map<String, Variable> variables;

    /**
     * Builds all components possible of a car
     */
    public Examples() {

        /** components of color's domain */
        ArrayList<String> composants = new ArrayList<>(Arrays.asList("toit", "capot", "hayon","droit","gauche"));
        /** component of boolean's domain */
        ArrayList<String> composants2 = new ArrayList<>(Arrays.asList("toit ouvrant", "sono"));

        // domains
        this.domaineCouleur = new HashSet<>(Arrays.asList("noir", "blanc", "rouge"));
        this.domaineBool = new HashSet<>(Arrays.asList("true","false"));

        // Set<Variable>
        this.couleur = new ArrayList<>();
        this.boolVariable = new ArrayList<>();

        for(String str : composants) {
            // add the color variables
            this.couleur.add(new Variable(str,new HashSet<>(domaineCouleur)));
        }

        for(String str : composants2) {
            // add the boolean variables
            this.boolVariable.add(new Variable(str,new HashSet<>(domaineBool)));
        }

        // map of variables accessed by name
        this.variables = new HashMap<>();
        for (Variable var : this.couleur) {
            // add color variables into the map
            this.variables.put(var.getName(), var);
        }

        for(Variable var : this.boolVariable) {
            // add boolean variables into the map
            this.variables.put(var.getName(), var);
        }
    }

    /**
     * Get all variables with color and boolean domain
     * @return set of variables
     */
    public Set<Variable> getVariables() {
        Set<Variable> vars = new HashSet<>();
        // add all color variables
        vars.addAll(this.couleur);
        // add all boolean variables
        vars.addAll(this.boolVariable);
        return vars;
    }

    /**
     * Get domain of color
     * @return the color's domain
     */
    public Set<String> getDomaineCouleur() {
        return this.domaineCouleur;
    }

    /**
     * Get boolean's domain
     * @return the boolean's domain
     */
    public Set<String> getDomaineBool() {
        return this.domaineBool;
    }

    // Cars

    /**
     * Get Example of a car
     * @return example of a car
     */
    public Map<Variable, String> getVoiture1() {
        Map<Variable,String> voiture = new HashMap<>();

        // all names of variables present in a car
        ArrayList<String> comp = new ArrayList<>(Arrays.asList(
                "droit","toit"
        ));
        // values of futur variables
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("noir","rouge"));

        for(int i = 0; i<comp.size(); i++) {
            // add the variable corresponding to the name and its value affected
            voiture.put(this.variables.get(comp.get(i)), valeurs.get(i));
        }
        return voiture;
    }

    /**
     * Get an Example of a car
     * @return example of a car
     */
    public Map<Variable, String> getVoiture2() {
        Map<Variable,String> voiture = new HashMap<>();

        // all names of variables present in a car
        ArrayList<String> comp = new ArrayList<>(Arrays.asList(
                "toit","hayon","droit","gauche","toit ouvrant","sono"
        ));
        // values of futur variables
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("noir", "blanc", "noir","noir","blanc","true","true"));

        for(int i = 0; i<comp.size(); i++) {
            // add the variable corresponding to the name and its value affected
            voiture.put(this.variables.get(comp.get(i)), valeurs.get(i));
        }
        return voiture;
    }

    /**
     * Get Example of a car
     * @return example of a car
     */
    public Map<Variable, String> getVoiture3() {
        Map<Variable,String> voiture = new HashMap<>();

        // all names of variables present in a car
        ArrayList<String> comp = new ArrayList<>(Arrays.asList(
                "toit","hayon","droit","gauche","toit ouvrant","sono"
        ));
        // values of futur variables
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("noir", "noir", "blanc","balnc","blanc","true","true"));

        for(int i = 0; i<comp.size(); i++) {
            // add the variable corresponding to the name and its value affected
            voiture.put(this.variables.get(comp.get(i)), valeurs.get(i));
        }
        return voiture;
    }

    /**
     * Get Example of a car
     * @return example of a car
     */
    public Map<Variable, String> getVoiture4() {
        Map<Variable,String> voiture = new HashMap<>();

        // all names of variables present in a car
        ArrayList<String> comp = new ArrayList<>(Arrays.asList(
                "toit","hayon","droit","gauche","toit ouvrant","sono"
        ));
        // values of futur variables
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("rouge", "blanc", "rouge","noir","noir","false","true"));

        for(int i = 0; i<comp.size(); i++) {
            // add the variable corresponding to the name and its value affected
            voiture.put(this.variables.get(comp.get(i)), valeurs.get(i));
        }
        return voiture;
    }

    // constraints

    /**
     * Get Example of a constraint : toit = hayon = capot
     * @return example of a constraint
     */
    public AllEqualConstraint getExemple1() {
      Set<Variable> allEqual = new HashSet<>();

      // all names of variables present in a constraint
      ArrayList<String> comp = new ArrayList<>(Arrays.asList(
                "toit", "capot", "hayon"
        ));

      for(String str : comp) {
          // add the variable corresponding to the name
          allEqual.add(this.variables.get(str));
      }
      return new AllEqualConstraint(allEqual);
    }

    /**
     * Get an Example of a constraint : (gauche = toit) or (droit = toit)
     * @return example of a constraint
     */
    public ConstraintOr getExemple2() {
        // we need two AllEqualConstraint and a ConstraintOr between them
        Set<Variable> setEqual = new HashSet<>();
        setEqual.add(this.variables.get("gauche"));
        setEqual.add(this.variables.get("toit"));

        Set<Variable> setEqual2 = new HashSet<>();
        setEqual2.add(this.variables.get("droit"));
        setEqual2.add(this.variables.get("toit"));

        // gauche = toit
        AllEqualConstraint all1 = new AllEqualConstraint(setEqual);
        // droit = toit
        AllEqualConstraint all2 = new AllEqualConstraint(setEqual2);
        return new ConstraintOr(all1,all2);
    }

    /**
     * Get an Example of a constraint : not(droit="noir" and gauche="noir")
     * @return example of a constraint
     */
    public IncompatibilityConstraint getExemple3(){

        Map<Variable,String> premisse = new HashMap<>();

        // all variables engaged in the constraint
        ArrayList<Variable> comp = new ArrayList<>();
        comp.add(this.variables.get("droit"));
        comp.add(this.variables.get("gauche"));

        // their value will be assigned
        ArrayList<String> val = new ArrayList<>();
        val.add("noir");
        val.add("noir");

        for (int i = 0; i<comp.size(); i++) {
            // assignation of variables in the premisse
            premisse.put(comp.get(i), val.get(i));
        }
        // build a constraint
        IncompatibilityConstraint compNoir = new IncompatibilityConstraint(new HashSet<>(comp), premisse);

        return compNoir;
    }

    /**
     * Get an Example of a constraint : not(sono="true" and "toit ouvrant="true")
     * @return example of a constraint
     */
    public IncompatibilityConstraint getExemple4(){
        // all variables engaged in a constraint
        Set<Variable> nEqual = new HashSet<>();
        nEqual.add(this.variables.get("toit ouvrant"));
        nEqual.add(this.variables.get("sono"));

        // conclusion of the constraint
        Map<Variable,String> conclusion = new HashMap<>();

        Iterator<Variable> iter = nEqual.iterator();
        while(iter.hasNext()){
            // variables assigned to true
            conclusion.put(iter.next(), "true");
        }

        return new IncompatibilityConstraint(nEqual,conclusion);
    }

    /**
     * Get an Example of a constraint : hayon &#033;= droit &#033;= porte
     * @return example of a constraint
     */
    public AllDifferentConstraint getExemple5(){
      Set<Variable> allEqual = new HashSet<>();
      // add all variable in the futur AllDifferentConstraint
      allEqual.add(this.variables.get("hayon"));
      allEqual.add(this.variables.get("toit"));
      allEqual.add(this.variables.get("capot"));
      // build the constraint
      AllDifferentConstraint all = new AllDifferentConstraint(allEqual);
      return all;
    }

    /**
     * Get an Example of a constraint : droit="noir" or gauche="blanc"
     * @return example of a constraint
     */
    public Disjunction getExemple6() {
        Map<Variable, String> conclusion = new HashMap<>();

        ArrayList<Variable> comp = new ArrayList<>();
        // all variables engaged in a constraint
        comp.add(this.variables.get("droit"));
        comp.add(this.variables.get("gauche"));

        // their value
        ArrayList<String> val = new ArrayList<>();
        val.add("noir");
        val.add("blanc");

        // variable assigned to their value
        conclusion.put(comp.get(0), val.get(0));
        conclusion.put(comp.get(1), val.get(1));

        // build the constraint
        Disjunction compNoir = new Disjunction(new HashSet<>(comp), conclusion);

        return compNoir;
    }
}
