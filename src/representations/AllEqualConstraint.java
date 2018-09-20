
package representations;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AllEqualConstraint implements Constraint {

    private Set<Variable> variables;
    private boolean not;

    public AllEqualConstraint(Set<Variable> variables, boolean not){
        this.variables = variables;
        this.not = not;
    }

    public AllEqualConstraint(Set<Variable> variables){
        this(variables,false);
    }


    /*
    Return variables
    */
    @Override
    public Set<Variable> getScope() {
        return this.variables;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, String> voiture) {

        Iterator<Variable> iter = this.variables.iterator();

        Variable var = iter.next();
        String pastValue = voiture.get(var);
        if (pastValue==null) {
                return this.not;
        }

        String value;
        do {
            var = iter.next();
            value = voiture.get(var);
            if (value==null) {
                return this.not;
            }
            if (!pastValue.equals(value)) {
                return this.not;
            }
        } while (iter.hasNext());
        return !this.not;
    }

    @Override
    public String toString(){
        String ch = "";
        if (this.not) {
            ch += "!(";
        }
        Iterator<Variable> iter = this.variables.iterator();

        while(iter.hasNext()) {
            ch += iter.next();
            if (iter.hasNext()) {
                ch += " = ";
            }
        }
        if (this.not) {
            ch += ")";
        }
        return ch;
    }
}
