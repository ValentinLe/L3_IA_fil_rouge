
package representations;

import java.util.Map;
import java.util.Set;

/**
 * The incompatibility is the rule : !(a=4 && b=5 && c=0 && ...)
 * 
 */
public class IncompatibilityConstraint extends Rule {
    
    /**
     * Build a instance of incompatibility
     * @param scope all variables in the constraint
     * @param premisse the incompatibility
     * @param not if you want the not of the constraint
     */
    public IncompatibilityConstraint(Set<Variable> scope, Map<Variable, String> premisse, boolean not) {
        super(scope, premisse, null, not);
    }
    
    /**
     * Build a instance of incompatibility
     * @param scope all variables in the constraint
     * @param premisse the incompatibility
     */
    public IncompatibilityConstraint(Set<Variable> scope, Map<Variable, String> premisse) {
        super(scope, premisse, null, false);
    }
}
