
package representations;

import java.util.Map;
import java.util.Set;

/**
 * A constraint is a requirement on the variable's values
 * 
 */
public interface Constraint {
    
    /**
     * Getter of the constraint's scope
     * @return the scope of the constraint
     */
    public Set<Variable> getScope();
    
    /**
     * test if the constraint is satisfied by a car
     * @param voiture the car 
     * @return the result of the test
     */
    public boolean isSatisfiedBy(Map<Variable,String> voiture); 
    
}
