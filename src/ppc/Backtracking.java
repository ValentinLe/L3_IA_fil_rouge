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
        
        ArrayList<Constraint> constraintList = new ArrayList<Constraint>(this.constraints);
        Iterator iterConstraints = this.constraints.iterator();
        
        for(Constraint constraint: constraintList){
            
            ArrayList<Variable> variablesConcerned = new ArrayList<Variable>(constraint.getScope());
            
            for(Variable variable : variablesConcerned){
                
                ArrayList<String>domaine = new ArrayList<String>(variable.getDomaine());
                
                res.put(variable, domaine.get(0));
                
                System.out.println("Résultat du test : "+verifyAllConstraint(res));
            }
            
            
        }
        
        
        return res;
    }
    
    public boolean verifyAllConstraint(Map<Variable, String> voiture){
        
        System.out.println("La voiture => "+voiture);
        
        Iterator iter = this.constraints.iterator();
        
        while(iter.hasNext()){
            if(!((Constraint)iter.next()).isSatisfiedBy(voiture)){
                return false;
            }
        }
        return true;
    }
}
