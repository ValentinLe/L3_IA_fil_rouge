
package representations;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * this constraint is about the variables' values that their are all equals
 * 
 */
public class AllEqualConstraint extends AllCompareConstraint implements Constraint {

    /**
     * Build a instance of AllEqualConstraint
     * @param variables vairables of the constraint
     * @param not if you want the not of the constraint
     */
    public AllEqualConstraint(Set<Variable> variables, boolean not){
        super(variables, not);
    }

    /**
     * Build a instance of AllEqualConstraint
     * @param variables vairables of the constraint
     */
    public AllEqualConstraint(Set<Variable> variables){
        super(variables,false);
    }

    /**
     * Test if all variables is equal in a car
     * @param voiture the car
     * @return the result of the test
     */
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

    /**
     * Get the string separator of this constraint
     * @return the string separator
     */
    @Override
    public String getSeparator() {
        return " = ";
    }
}
