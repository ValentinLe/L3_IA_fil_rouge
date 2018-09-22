
package representations;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AllEqualConstraint extends AllCompareConstraint implements Constraint {

    public AllEqualConstraint(Set<Variable> variables, boolean not){
        super(variables, not);
    }

    public AllEqualConstraint(Set<Variable> variables){
        super(variables,false);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, String> voiture) {
        if (voiture.isEmpty() || voiture.size()==1) {
            return true;
        } else {
            Iterator<Variable> iter = this.variables.iterator();
            String values = null;
            String currentValue = null;
            while(iter.hasNext()) {
                Variable var = iter.next();
                currentValue = voiture.get(var);
                if (currentValue != null) {
                    if (values == null) {
                        values = currentValue;
                    } else {
                        if (!values.equals(currentValue)) {
                            return this.not;
                        }
                        values = currentValue;
                    }
                }
            }
            return !this.not;
        }
    }

    @Override
    public String getSeparator() {
        return " = ";
    }
}
