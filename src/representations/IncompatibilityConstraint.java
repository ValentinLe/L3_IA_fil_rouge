
package representations;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The incompatibility is the rule : !(a=4 & b=5 & c=0 & ...)
 * 
 */
public class IncompatibilityConstraint extends Rule {
    
    /**
     * Build a instance of incompatibility
     * @param scope all variables in the constraint
     * @param premisse the incompatibility
     */
    public IncompatibilityConstraint(Set<Variable> scope, Map<Variable, String> premisse) {
        super(scope, premisse, null);
    }
    
    // !(a=4 && b=3) = a!=4 || b!=3
    @Override
    public boolean filtrer(Map<Variable, String> voiture, Map<Variable, Set<String>> domaines) {
        return this.filterWithPart(voiture, domaines, this.getPremisse(), false);
    }
}
