
package representations;

import java.util.Map;

/**
 * this class it's a AND combination between two constraints
 *
 */
public class ConstraintAnd extends ConstraintBool implements Constraint {
    
    /**
     * Build a constraint OR
     * @param c1 the first constraint
     * @param c2 the second constraint
     */
    public ConstraintAnd(Constraint c1, Constraint c2) {
        super(c1, c2);
    }

    /**
     * Test if the first constraint AND the second constraint is satisfied by a car
     * @param voiture the car
     * @return the result of the test
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, String> voiture) {
        return (this.c1.isSatisfiedBy(voiture) && this.c2.isSatisfiedBy(voiture));
    }
    
    /**
     * Get the string separator of the AND
     * @return the string separator
     */
    @Override
    public String getSeparator() {
        return " && ";
    }
}
