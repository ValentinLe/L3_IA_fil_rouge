
package representations;

import java.util.Map;

public class IncompatibilityConstraint extends Rule {
    
    public IncompatibilityConstraint(Map<Variable, String> premisse) {
        super(premisse, null);
    }   
}
