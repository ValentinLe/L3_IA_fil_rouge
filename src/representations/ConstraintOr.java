
package representations;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConstraintOr implements Constraint {

    private Constraint c1;
    private Constraint c2;

    public ConstraintOr(Constraint c1, Constraint c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> scopeFinal = new HashSet<>();
        scopeFinal.addAll(this.c1.getScope());
        scopeFinal.addAll(this.c2.getScope());
        return scopeFinal;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, String> contraintes) {
        return (this.c1.isSatisfiedBy(contraintes) || this.c2.isSatisfiedBy(contraintes));
    }

    @Override
    public String toString() {
        return "(" + this.c1 + ") || (" + this.c2 + ")";
    }
}
