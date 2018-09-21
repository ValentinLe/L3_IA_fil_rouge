
package representations;

import java.util.ArrayList;
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
        if (voiture.isEmpty() || voiture.size()==1) {
            return true;
        } else {
            ArrayList<Variable> list = new ArrayList<>(this.variables);
            String value = null;
            String currentValue;
            boolean testNull = !this.not;
            int cpt = 0;
            for (Variable var : list) {
                currentValue = voiture.get(var);
                if (currentValue != null) {
                    cpt += 1;
                    if (value==null) {
                        value = currentValue;
                    } else {
                        if(!value.equals(currentValue)) {
                            testNull = this.not;
                        } else {
                            return !this.not;
                        }
                    }
                }
            }
            if (cpt == 1) {
                return true;
            }
            return testNull;
        }
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
