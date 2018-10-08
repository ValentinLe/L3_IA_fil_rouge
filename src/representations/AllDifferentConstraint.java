
package representations;

import java.util.*;

/**
 * this constraint is about the variables' values that their are all differents
 * 
 */
public class AllDifferentConstraint extends AllCompareConstraint {

    /**
     * Build a instance of AllDifferentConstraint
     * @param variables vairables of the constraint
     */
    public AllDifferentConstraint(Set<Variable> variables){
        super(variables);
    }

    /**
     * Test if all variables is differents in a car
     * @param voiture the car
     * @return the result of the test
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, String> voiture) {
        if (voiture.isEmpty() || voiture.size()==1) {
            return true;
        } else {
            ArrayList<String> values = new ArrayList<>();
            String currentValue = null;
            for (Variable var : this.variables) {
                currentValue = voiture.get(var);
                if (currentValue != null) {
                    if (values.isEmpty()) {
                        values.add(currentValue);
                    } else {
                        if (values.contains(currentValue)) {
                            return false;
                        }
                        values.add(currentValue);
                    }
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
        return " != ";
    }
    
    /**
     * filtering of variables' domain
     * @param voiture a car for the filtering test
     * @param domaines variables and its copy domain for filtering
     * @return true if there is a filtering
     */
    @Override
    public boolean filtrer(Map<Variable, String> voiture, Map<Variable, Set<String>> domaines) {
        boolean isFilter = false;
        Set<String> values = new HashSet<>();
        for (Variable var : this.variables) {
            if (voiture.get(var) != null) {
                values.add(voiture.get(var));
            }
        }
        if (!values.isEmpty()) {
            for (Variable var : this.variables) {
                if (domaines.containsKey(var)) {
                    Set<String> copyDom = new HashSet<>(domaines.get(var));
                    for (String str : domaines.get(var)) {
                        if (values.contains(str)) {
                            copyDom.remove(str);
                            isFilter = true;
                        }
                    }
                    domaines.put(var, copyDom);
                }
                
            }
        }
        return isFilter;
    }
}
