
package representations;

import java.util.Map;

public class Rule {
    
    private Map<Variable,String > premisse;
    private Map<Variable, String> conclusion;
    
    public Rule(Map<Variable, String> premisse, Map<Variable,String> conclusion){
        this.premisse = premisse;
        this.conclusion = conclusion;
    }
    
}
