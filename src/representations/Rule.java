
package representations;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Rule implements Constraint {

    private Set<Variable> scope;
    private Map<Variable,String > premisse;
    private Map<Variable, String> conclusion;

    public Rule(Set<Variable> scope,Map<Variable, String> premisse, Map<Variable,String> conclusion) {
        this.scope = scope;
        this.premisse = premisse;
        this.conclusion = conclusion;
    }

    @Override
    public Set<Variable> getScope() {
      return this.scope;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable,String> contraintes) {

        boolean p = true;
        boolean c = false;

        for(Variable var : contraintes.keySet()) {
            if(this.premisse != null && !contraintes.get(var).equals(this.premisse.get(var))){
                p = false;
            }
            if(this.conclusion != null && contraintes.get(var).equals(this.conclusion.get(var))){

                c = true;
            }
        }
        return !p || c;
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

        ch += this.getStringMap(this.premisse, "&&");

        if (this.premisse != null && this.conclusion != null) {
            ch += " -> ";
        }

        ch += this.getStringMap(this.conclusion, "||");

        return ch;
    }

}
