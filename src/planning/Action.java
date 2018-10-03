/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planning;

import java.util.Map;
import representations.Constraint;
import representations.Variable;

/**
 *
 * @author quentindeme
 */
public class Action {
    
    private Constraint preconditions;
    private Map<Variable, String> effets;
    
    public Action(Constraint preconditions, Map<Variable,String> effets){
        
    }
}
