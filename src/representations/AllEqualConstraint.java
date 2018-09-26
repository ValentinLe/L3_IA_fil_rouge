
package representations;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * this constraint is about the variables' values that their are all equals
 * 
 */
public class AllEqualConstraint extends AllCompareConstraint {

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
                } else {
                    return true;
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
    
    @Override
    public boolean filtrer(Map<Variable, String> voiture, Map<Variable, Set<String>> domaines) {
        boolean isFilter = false;
        String value = null;
        for (Variable var : this.variables) {
            if (value == null && voiture.get(var) != null) {
                value = voiture.get(var);
            }
        }
        for (Variable var : this.variables) {
            Set<String> copyDom = new HashSet<>(var.getDomaine());
            for (String str : var.getDomaine()) {
                if (!str.equals(value)) {
                    copyDom.remove(str);
                    isFilter = true;
                }
            }
            if (domaines.containsKey(var)) {
                domaines.put(var, copyDom);
            }
        }
        return isFilter;
    }
}
