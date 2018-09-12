/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package representations;

import java.util.Set;

/**
 *
 * @author quentindeme
 */

public class Variable {
    
    private String name;
    private Set<String> domaine;
    
    public Variable(String name, Set<String> domaine){
        this.name = name;
        this.domaine = domaine;
    }
    
    @Override
    public String toString(){
        return this.name+" || Domaine : "+this.domaine;
    }
}
