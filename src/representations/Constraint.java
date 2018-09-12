/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package representations;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author quentindeme
 */
public interface Constraint {
    
    public Set<Variable> getScope();
    public boolean isSatisfiedBy(Map<Variable,String> contraintes);
    
}
