/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package representations;

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
    public boolean isSatisfiedBy(Map<Variable, String> solution) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
