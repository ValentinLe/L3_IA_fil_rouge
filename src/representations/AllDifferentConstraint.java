
package representations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AllDifferentConstraint extends AllComparationConstraint implements Constraint {

    public AllDifferentConstraint(Set<Variable> variables, boolean not){
        super(variables, not);
    }

    public AllDifferentConstraint(Set<Variable> variables){
        super(variables,false);
    }
    
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
    
    @Override
    public String getSeparator() {
        return " != ";
    }
    
}
