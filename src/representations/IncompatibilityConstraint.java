
package representations;

import java.util.Map;
import java.util.Set;

public class IncompatibilityConstraint extends Rule {
    
    public IncompatibilityConstraint(Set<Variable> scope, Map<Variable, String> premisse, boolean not) {
        super(scope, premisse, null,not);
    }
    public IncompatibilityConstraint(Set<Variable> scope, Map<Variable, String> premisse) {
        super(scope, premisse, null,false);
    }
}
