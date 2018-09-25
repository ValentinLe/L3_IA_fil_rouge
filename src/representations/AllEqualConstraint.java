
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
    
    /*
    // domaines des variables pas encore affectées
    @Override
    public boolean filtrer(Map<Variable, String> voiture, Map<Variable, Set<String>> domaines) {
        
        for (Variable var : this.variables) {
            if (voiture.get(var) != null) {
                for (Variable var2 : this.variables) {
                    if (!var.equals(var2)) {
                        Set<String> domVar2 = var2.getScope();
                        Set<String> domVar3 = new HashSet<>();
                        domVar3.addAll(domVar2);
                        for (String str : domVar3) {
                            if (domVar2.contains(str) && !str.equals(voiture.get(var))) {
                                domVar2.remove(str);
                            }
                        }
                        domaines.put(var2,domVar2);
                    }
                }
            }
        }
        
        return false;
    }
    */
    
    // domaines des variables pas encore affectées
    @Override
    public boolean filtrer(Map<Variable, String> voiture, Map<Variable, Set<String>> domaines) {
        String value = null;
        boolean isFilter = false;
        for (Variable var : this.variables) {
            if (voiture.get(var) != null) {
                if (value == null) {
                    value = voiture.get(var);
                }
                Set<String> domVar = new HashSet<>(var.getScope());
                for (String valueDom : domVar) {
                    if (domVar.contains(valueDom) && !valueDom.equals(value)) {
                        domVar.remove(valueDom);
                        isFilter = true;
                    }
                }
                domaines.put(var,domVar);
            }
        }
        return isFilter;
    }
}
