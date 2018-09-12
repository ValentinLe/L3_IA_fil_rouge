
package representations;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Disjunction extends Rule {
    /*
    private Rule expression;
    private HashMap<Integer,Set<String>> combinaisons;
    */
   
    public Disjunction(Map<Variable,String> conclusion){
        super(null,conclusion);
    }
}
