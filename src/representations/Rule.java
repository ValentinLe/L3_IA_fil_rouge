
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
        boolean c = true;
        
        for(Variable var : contraintes.keySet()) {
            if(contraintes.get(var) != this.premisse.get(var)){
                p = false;
            }
            if(contraintes.get(var) != this.conclusion.get(var)){
                c = false;
            }
        }
        
        return !p || c;
    }
    
    @Override
    public String toString() {
        String ch = "";
        for(Variable var : this.premisse.keySet()) {
            ch += var.getName() + " = " + this.premisse.get(var) + " && ";
        }
        ch += " -> ";
        for(Variable var : this.conclusion.keySet()) {
            ch += var.getName() + " = " + this.conclusion.get(var) + " || ";
        }
        return ch;
    }
    
}
