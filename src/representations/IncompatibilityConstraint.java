
package representations;

import java.util.*;

/**
 * The incompatibility is the rule : !(a=4 & b=5 & c=0 & ...)
 *
 */
public class IncompatibilityConstraint extends Rule {

    /**
     * Build an instance of incompatibility
     * @param scope all variables in constraint
     * @param premisse the incompatibility
     */
    public IncompatibilityConstraint(Set<Variable> scope, Map<Variable, String> premisse) {
        super(scope, premisse, null);
    }

    /**
     * Filters the domain's variables
     * not(a=1 and b=3 and ...) = a!=1 or b!=3 or ..., it's like filtering
     * a conclusion with an equality test set to false (inequality)
     * @param voiture a car for the filtering test
     * @param domaines variables and its copied domain for filtering
     * @return true if a filter occures
     */
    @Override
    public boolean filtrer(Map<Variable, String> voiture, Map<Variable, Set<String>> domaines) {
        return this.filterWithPart(voiture, domaines, this.getPremisse(), false);
    }
}
