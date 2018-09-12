
package representations;

import java.util.Map;

public class Rule {
    
    private Map<Variable,String > premisse;
    private Map<Variable, String> conclusion;
    
    public Rule(Map<Variable, String> premisse, Map<Variable,String> conclusion){
        this.premisse = premisse;
        this.conclusion = conclusion;
    }
    
    public boolean isSatisfiedBy(Map<Variable,String> contraintes) {
        
        boolean p = true;
        for(Variable var : contraintes.keySet()) {
            if(contraintes.get(var) != this.premisse.get(var)){
                p = false;
            }
        }
        
        boolean c = true;
        for(Variable var : contraintes.keySet()) {
            if(contraintes.get(var) != this.conclusion.get(var)){
                c = false;
            }
        }
        
        return !p || c;
    }
    
}
