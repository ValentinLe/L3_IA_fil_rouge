/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package representations;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AllEqualConstraint implements Constraint{
    
    private Set<Variable> variables;

    public AllEqualConstraint(Set<Variable> variables){
        this.variables = variables;
    }
    
    @Override
    /*
    Return variables
    */
    public Set<Variable> getScope() {
        return this.variables;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, String> voiture) {
        String pastValue;
        String value;
        
        Iterator iter = voiture.keySet().iterator();
        
        pastValue = (String)voiture.get(iter.next());
        do{
            value = (String)voiture.get(iter.next());
            System.out.println(pastValue+" "+value);
            if(pastValue != value){
                return false;
            }
            pastValue = value;
        }while(iter.hasNext());        
        return true;
    }
    
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("Les variables suivantes doivent être égales:\n");
        res.append(this.variables);
        
        return res.toString();
    }
}
