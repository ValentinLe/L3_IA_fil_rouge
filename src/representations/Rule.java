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
public class Rule{
    //Map variable - String
    
    private Map<Variable,String > premisse;
    private Map<Variable, String> conclusion;
    
    public Rule(Map<Variable, String> premisse, Map<Variable,String> conclusion){
        this.premisse = premisse;
        this.conclusion = conclusion;
    }
    
}
