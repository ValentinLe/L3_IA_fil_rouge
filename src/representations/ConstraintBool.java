
package representations;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This abstract classe is a boolean combination between two constraints (And or OR)
 * 
 */
public abstract class ConstraintBool implements Constraint {
    
    protected Constraint c1;
    protected Constraint c2;
    
    /**
     * build a instance of the boolean constraint
     * @param c1 the first constraint
     * @param c2 the second constraint
     */
    public ConstraintBool(Constraint c1, Constraint c2) {
        this.c1 = c1;
        this.c2 = c2;
    }
    
    /**
     * Getter of the scope's union
     * @return the scope's union
     */
    @Override
    public Set<Variable> getScope() {
        Set<Variable> scopeFinal = new HashSet<>();
        scopeFinal.addAll(this.c1.getScope());
        scopeFinal.addAll(this.c2.getScope());
        return scopeFinal;
    }
    
    /**
     * Test if the first constraint AND/OR the second constraint is satisfied by a car
     * @param voiture the car
     * @return the result of the test
     */
    @Override
    public abstract boolean isSatisfiedBy(Map<Variable, String> voiture);
    
    /**
     * Getter of the string separator, "&&" for AND, "||" for OR
     * @return the string separator
     */
    public abstract String getSeparator();
    
    /**
     * Build a string representation of the boolean constraint
     * @return the string representation
     */
    @Override
    public String toString() {
        return "(" + this.c1 + ")" + this.getSeparator() + "(" + this.c2 + ")";
    }
    
    @Override
    public boolean filtrer(Map<Variable, String> voiture, Map<Variable, Set<String>> domaines) {
        return (this.c1.filtrer(voiture, domaines) || this.c2.filtrer(voiture, domaines));
    }
    
}
