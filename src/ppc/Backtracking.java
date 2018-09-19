/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import representations.Constraint;
import representations.Variable;

/**
 *
 * @author quentindeme
 */
public class Backtracking {
    
    private Set<Constraint> constraints;
    
    public Backtracking(Set<Constraint> constraints){
        this.constraints = constraints;
    }
    
    public Map<Variable,String> getSolution(){
        Map res = new HashMap<Variable, String>();
        
        Iterator iterConstraints = this.constraints.iterator();
        
        while(iterConstraints.hasNext()){
            
            ArrayList<Variable> tab = new ArrayList<Variable>(((Constraint)iterConstraints.next()).getScope());
            
            System.out.println(tab);
            
        }
        
        return res;
        
    }
}
