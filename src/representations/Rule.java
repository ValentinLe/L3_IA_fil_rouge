
package representations;

import java.util.Iterator;
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
            if(this.premisse != null && contraintes.get(var) != this.premisse.get(var)){
                p = false;
            }
            if(this.conclusion != null && contraintes.get(var) != this.conclusion.get(var)){
                c = false;
            }
        }
        
        return !p || c;
    }
    
    @Override
    public String toString() {
        String ch = "";
        if (this.premisse != null) {
           Iterator iter = this.premisse.keySet().iterator();
           while(iter.hasNext()){
               Variable var = (Variable) iter.next();
               ch += var.getName()+ " = " + this.premisse.get(var);
               if (iter.hasNext()) {
                   ch += " && ";
               }
           }   
        }
        
        if (this.premisse != null && this.conclusion != null) {
            ch += " -> ";
        }
        
        if (this.conclusion != null) {
           Iterator iter2 = this.conclusion.keySet().iterator();
            while(iter2.hasNext()){
                Variable var = (Variable) iter2.next();
                ch += var.getName()+ " = " + this.conclusion.get(var);
                if (iter2.hasNext()) {
                    ch += " || ";
                }
            }
        }
        
        return ch;
    }
    
}
