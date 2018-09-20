
package representations;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Rule implements Constraint {

    private Set<Variable> scope;
    private Map<Variable,String > premisse;
    private Map<Variable, String> conclusion;
    private boolean not;

    public Rule(Set<Variable> scope, Map<Variable, String> premisse, Map<Variable,String> conclusion, boolean not) {
        this.scope = scope;
        this.premisse = premisse;
        this.conclusion = conclusion;
        this.not = not;
    }

    public Rule(Set<Variable> scope, Map<Variable, String> premisse, Map<Variable,String> conclusion) {
        this(scope, premisse, conclusion, false);
    }

    @Override
    public Set<Variable> getScope() {
      return this.scope;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable,String> voiture) {
        boolean p = true;
        boolean c = false;

        for(Variable var : voiture.keySet()) {
            if(this.premisse != null && voiture.get(var) != null && this.premisse.get(var)!= null && !voiture.get(var).equals(this.premisse.get(var))){
                p = false;
            }
            if(this.conclusion != null && voiture.get(var) != null && this.conclusion.get(var)!= null && voiture.get(var).equals(this.conclusion.get(var))){

                c = true;
            }
        }
        if (this.not) {
            return p && !c;
        } else {
            return !p || c;
        }
    }

    public String getStringMap(Map<Variable, String> map, String separator) {
        String ch = "";
        if (map != null) {
           Iterator<Variable> iter = map.keySet().iterator();
           while(iter.hasNext()){
               Variable var = iter.next();
               ch += var.getName()+ " = " + map.get(var);
               if (iter.hasNext()) {
                   ch += " " + separator + " ";
               }
           }
        }
        return ch;
    }

    @Override
    public String toString() {
        String ch = "";
        if (this.conclusion == null && !this.not) {
            ch += "!(";
        }

        ch += this.getStringMap(this.premisse, "&&");

        if (this.premisse != null && this.conclusion != null) {
            ch += " -> ";
        }

        ch += this.getStringMap(this.conclusion, "||");

        if (this.conclusion == null && !this.not) {
            ch += ")";
        }
        return ch;
    }

}
