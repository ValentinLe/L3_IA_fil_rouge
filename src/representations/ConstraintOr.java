
package representations;

import java.util.Map;

public class ConstraintOr extends ConstraintBool implements Constraint {

    public ConstraintOr(Constraint c1, Constraint c2) {
        super(c1, c2);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, String> contraintes) {
        return (this.c1.isSatisfiedBy(contraintes) || this.c2.isSatisfiedBy(contraintes));
    }
    
    @Override
    public String getSeparator() {
        return " || ";
    }
}
