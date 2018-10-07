
package representations;

import java.util.*;

/**
 * this constraint is about the variables' values that their are all equals
 * 
 */
public class AllEqualConstraint extends AllCompareConstraint {

    /**
     * Build a instance of AllEqualConstraint
     * @param variables vairables of the constraint
     */
    public AllEqualConstraint(Set<Variable> variables){
        super(variables);
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
            String values = null;
            String currentValue = null;
            for (Variable var : this.variables) {
                currentValue = voiture.get(var);
                if (currentValue != null) {
                    if (values == null) {
                        values = currentValue;
                    } else {
                        if (!values.equals(currentValue)) {
                            return false;
                        }
                        values = currentValue;
                    }
                } else {
                    return true;
                }
            }
            return true;
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
        boolean isFilter = false; // boolean to test if there is a filtering
        String value = null; // value of variable of the allEqual
        for (Variable var : this.variables) {
            // for all variable in constraint's variables
            if (value == null && voiture.get(var) != null) {
                // if value isn't assigned and the car don't have this variable
                value = voiture.get(var);
                break;
            }
        }
        if (value != null) {
            // if value isn't assigned, there are no variables of this constraint in the car
            for (Variable var : this.variables) {
                // for all variable in the constraint's variables
                if (domaines.containsKey(var)) {
                    // if the variable isn't in the car
                    Set<String> copyDom = new HashSet<>(domaines.get(var)); // copy of variable's domain
                    for (String str : domaines.get(var)) {
                        // for all value of variables' (aren't in the car) Set of Values domain of the map
                        if (!str.equals(value)) {
                            // if the value isn't equal to the value find at the beggining of this function
                            // we remove it and assign true to the boolean of filtering test
                            copyDom.remove(str);
                            isFilter = true;
                        }
                    }
                    // add variable and the domain filter or not filter
                    domaines.put(var, copyDom);
                }
            }
        }
        return isFilter;
    }
}
