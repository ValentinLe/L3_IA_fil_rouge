
package representations;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ConstraintBool implements Constraint {
    
    protected Constraint c1;
    protected Constraint c2;

    public ConstraintBool(Constraint c1, Constraint c2) {
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
    public abstract boolean isSatisfiedBy(Map<Variable, String> contraintes);
    
    public abstract String getSeparator();
    
    @Override
    public String toString() {
        return "(" + this.c1 + ")" + this.getSeparator() + "(" + this.c2 + ")";
    }
    
}
