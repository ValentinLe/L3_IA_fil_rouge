
package representations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * this constraint is about the variables' values that their are all differents
 * 
 */
public class AllDifferentConstraint extends AllCompareConstraint {

    /**
     * Build a instance of AllDifferentConstraint
     * @param variables vairables of the constraint
     * @param not if you want the not of the constraint
     */
    public AllDifferentConstraint(Set<Variable> variables, boolean not){
        super(variables, not);
    }

    /**
     * Build a instance of AllDifferentConstraint
     * @param variables vairables of the constraint
     */
    public AllDifferentConstraint(Set<Variable> variables){
        super(variables,false);
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
            Iterator<Variable> iter = this.variables.iterator();
            ArrayList<String> values = new ArrayList<>();
            String currentValue = null;
            while(iter.hasNext()) {
                Variable var = iter.next();
                currentValue = voiture.get(var);
                if (currentValue != null) {
                    if (values.isEmpty()) {
                        values.add(currentValue);
                    } else {
                        if (values.contains(currentValue)) {
                            return this.not;
                        }
                        values.add(currentValue);
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
        return " != ";
    }
    
    // domaines des variables pas encore affectées
    @Override
    public boolean filtrer(Map<Variable, String> voiture, Map<Variable, Set<String>> domaines) {
        ArrayList<String> values = new ArrayList<>();
        boolean isFilter = false;
        for (Variable var : this.variables) {
            if (voiture.get(var) != null) {
                Set<String> domVar = new HashSet<>(var.getDomaine());
                for (String valueDom : domVar) {
                    if (domVar.contains(valueDom) && values.contains(valueDom)) {
                        domVar.remove(valueDom);
                        isFilter = true;
                    }
                }
                domaines.put(var,domVar);
                values.add(voiture.get(var));
            }
        }
        return isFilter;
    }
}
