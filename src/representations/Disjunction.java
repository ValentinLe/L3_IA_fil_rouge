/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package representations;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author quentindeme
 */
public class Disjunction /*extends Rule*/ implements Constraint {
    /*
    private Rule expression;
    private HashMap<Integer,Set<String>> combinaisons;
    */
    
   
    public Disjunction(Map<Variable, String> premisse, Map<Variable,String> conclusion){
        
    }

    @Override
    public Set<Variable> getScope() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, String> solution) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
