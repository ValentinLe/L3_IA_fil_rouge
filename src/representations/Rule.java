
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

    public Boolean getPartSatisfied(Map<Variable, String> voiture, Map<Variable, String> part, boolean testPart) {
        if (part != null) {
            for (Variable var : part.keySet()) {
                if (voiture.get(var) == null) {
                    return null;
                }
                if (testPart) {
                    if(!voiture.get(var).equals(part.get(var))) {
                        testPart = false;
                    }
                } else {
                    if(voiture.get(var).equals(part.get(var))){
                        testPart = true;
                    }
                }
            }
        }
        return testPart;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable,String> voiture) {
        Boolean p = true;
        Boolean c = false;

        if (voiture.isEmpty()) {
            return !this.not;
        }

        p = getPartSatisfied(voiture, this.premisse, p);
        if (p == null) {
            return !this.not;
        }

        c = getPartSatisfied(voiture, this.conclusion, c);
        if (c == null) {
            return !this.not;
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
