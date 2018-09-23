
package representations;

import java.util.Map;
import java.util.Set;

/**
 * A Disjunction is a OR between variables : a=1 || b=0 || c=6 || ...
 * 
 */
public class Disjunction extends Rule {
    
    /**
     * Build a instance of disjunction
     * @param scope all variables in the constraint
     * @param conclusion the disjunction
     * @param not if you want the not of the constraint
     */
    public Disjunction(Set<Variable> scope, Map<Variable,String> conclusion, boolean not){
        super(scope, null, conclusion, not);
    }
    
    /**
     * Build a instance of disjunction
     * @param scope all variables in the constraint
     * @param conclusion the disjunction
     */
    public Disjunction(Set<Variable> scope,Map<Variable,String> conclusion){
        super(scope, null, conclusion, false);
    }
}
