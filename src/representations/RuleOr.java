
package representations;

import java.util.Map;
import java.util.Set;

public class RuleOr implements Constraint {
    
    private Set<Variable> scope;
    private Constraint c1;
    private Constraint c2;
    
    public RuleOr(Set<Variable> scope, Constraint c1, Constraint c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public Set<Variable> getScope() {
        return this.scope;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, String> contraintes) {
        return (this.c1.isSatisfiedBy(contraintes) || this.c2.isSatisfiedBy(contraintes));
    }
    
}
