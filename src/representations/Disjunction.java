
package representations;

import java.util.Map;
import java.util.Set;

public class Disjunction extends Rule {

    public Disjunction(Set<Variable> scope,Map<Variable,String> conclusion, boolean not){
        super(scope,null,conclusion,not);
    }
    
    public Disjunction(Set<Variable> scope,Map<Variable,String> conclusion){
        super(scope,null,conclusion,false);
    }
}
