
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

    private ArrayList<String> composants;
    private ArrayList<String> composants2;

    private ArrayList<Variable> couleur;
    private ArrayList<Variable> boolVariable;

    private Set<String> domaineCouleur;
    private Set<String> domaineBool;

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
    }
    
    public Set<Variable> getVariables() {
        Set<Variable> vars = new HashSet<>();
        vars.addAll(this.couleur);
        vars.addAll(this.boolVariable);
        return vars;
    }

    public Set<String> getComposants() {
        return new HashSet<>(this.composants);
    }

    public Set<String> getComposants2() {
        return new HashSet<>(this.composants2);
    }

    public Set<String> getDomaineCouleur() {
        return this.domaineCouleur;
    }

    public Set<String> getDomaineBool() {
        return this.domaineBool;
    }

    // Voitures

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

    //--------------------------------------------------------------//

    public Map<Variable, String> getVoiture1() {
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
        ArrayList<String> valeurs = new ArrayList<>(Arrays.asList("blanc", "noir", "noir","rouge","blanc","true","true"));

        for(int i = 0; i<comp.size(); i++) {
            voiture.put(comp.get(i), valeurs.get(i));
        }
        return voiture;
    }

    //--------------------------------------------------------------//

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

    //--------------------------------------------------------------//

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

    //--------------------------------------------------------------//

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

    // exemples ecmapus

    public AllEqualConstraint getExemple1() {
      Set<Variable> allEqual = new HashSet<>(Arrays.asList(
                new Variable("toit",domaineCouleur),
                new Variable("capot",domaineCouleur),
                new Variable("hayon",domaineCouleur)));
      return new AllEqualConstraint(allEqual);
    }

    //--------------------------------------------------------------//


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

    //--------------------------------------------------------------//

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


    //--------------------------------------------------------------//

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
}
