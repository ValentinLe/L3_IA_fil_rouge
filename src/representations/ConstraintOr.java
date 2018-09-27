
package representations;

import java.util.Map;
import java.util.Set;

/**
 * this class it's a OR combination between two constraints
 *
 */
public class ConstraintOr extends ConstraintBool implements Constraint {

    /**
     * Build a constraint OR
     * @param c1 the first constraint
     * @param c2 the second constraint
     */
    public ConstraintOr(Constraint c1, Constraint c2) {
        super(c1, c2);
    }

    /**
     * Test if the first constraint OR the second constraint is satisfied by a car
     * @param voiture the car
     * @return the result of the test
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, String> voiture) {
        return (this.c1.isSatisfiedBy(voiture) || this.c2.isSatisfiedBy(voiture));
    }
    
    /**
     * Get the string separator of the OR
     * @return the string separator
     */
    @Override
    public String getSeparator() {
        return " || ";
    }
    
    @Override
    public boolean filtrer(Map<Variable, String> voiture, Map<Variable, Set<String>> domaines) {
        return (this.c1.filtrer(voiture, domaines) || this.c2.filtrer(voiture, domaines));
    }
}
