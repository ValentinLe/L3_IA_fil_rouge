
package representations;

import java.util.Map;

public class Disjunction extends Rule {
    /*
    private Rule expression;
    private HashMap<Integer,Set<String>> combinaisons;
    */
   
    public Disjunction(Map<Variable,String> conclusion){
        super(null,conclusion);
    }
}
