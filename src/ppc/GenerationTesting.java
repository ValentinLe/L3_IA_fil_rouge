package ppc;

import java.util.*;
import representations.*;
import examples.Examples;

/**
 * Make n tests of by generating random cars to find a solution to our PPC.
 * @author quentindeme
 */
public class GenerationTesting {

    private Set<Variable> variables;
    private Set<Constraint> constraints;
    private int n;
    
    /**
     * Build a new serie of tests.
     * @param constraints Set of constraints used for the PPC.
     * @param n Integer, number of tests.
     */
    public GenerationTesting(Set<Constraint> constraints, int n){
        Examples ex = new Examples();

        this.variables.addAll(ex.getVariables());
        this.constraints = constraints;
        
        this.n = n;
        
        int i = 0;
        boolean res = false;
    }
    
    /**
     * Method called for generate the serie of test.
     * @return True if a solution had been found, false otherwise.
     */
    public boolean generate_and_test(){
        Map<Variable,String> voiture = generateCar();
        System.out.println("La voiture est: "+voiture);
        
        Iterator<Constraint> iter = this.constraints.iterator();
        
        while(iter.hasNext()){
            if(!iter.next().isSatisfiedBy(voiture)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Generate a new random car.
     * @return Map representing the car.
     */
    public Map<Variable,String> generateCar(){
        Map<Variable,String> voiture = new HashMap<>();
        
        Iterator<Variable> iter = this.variables.iterator();
        
        while(iter.hasNext()){
            Variable var = iter.next();
            voiture.put(var, getElement(var.getScope()));
        }
        
        return voiture;
    }
    
    /**
     * Get an element of the domain of a variable, to assign it to it.
     * @param domain All possibles values of the variable.
     * @return A random String value, to assign to the variable.
     */
    public String getElement(Set<String> domain){
        int size = domain.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for(Object obj : domain) {
            if (i == item)
                return (String)obj;
            i++;
        }
        
        return null;
    }
}

