
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
        Iterator iter = this.premisse.keySet().iterator();
        int i = 0;
        while(iter.hasNext()){
            Variable var = (Variable) iter.next();
            ch += var.getName()+ " = " + this.premisse.get(var);
            i++;
            if (iter.hasNext()) {
                ch += " && ";
            }
        }
        
        ch += " -> ";
        
        Iterator iter2 = this.conclusion.keySet().iterator();
        int j = 0;
        while(iter2.hasNext()){
            Variable var = (Variable) iter2.next();
            ch += var.getName()+ " = " + this.premisse.get(var);
            i++;
            if (iter2.hasNext()) {
                ch += " || ";
            }
        }
        return ch;
    }
    
}
