
package representations;

import java.util.Map;
import java.util.Set;

public class Disjunction extends Rule {

    public Disjunction(Set<Variable> scope,Map<Variable,String> conclusion){
        super(scope,null,conclusion);
    }
}
