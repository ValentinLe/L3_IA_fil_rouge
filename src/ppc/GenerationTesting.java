package ppc;

import java.util.*;
import representations.*;
import examples.Examples;

public class GenerationTesting {

    private Set<Variable> variables;
    private Set<Constraint> constraints;
    private int n;
    
    public GenerationTesting(Set<Constraint> constraints, int n){
        Examples ex = new Examples();

        this.variables.addAll(ex.getVariables());
        this.constraints = constraints;
        
        this.n = n;
        
        int i = 0;
        boolean res = false;
        /*
        while(i != n){
            res = generate_and_test();
            if(res){
                break;
            }
            i++;
        }
        
        System.out.println("Résultat des test: "+res);
        */
    }
    
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
    
    public Map<Variable,String> generateCar(){
        Map<Variable,String> voiture = new HashMap<>();
        
        Iterator<Variable> iter = this.variables.iterator();
        
        while(iter.hasNext()){
            Variable var = iter.next();
            voiture.put(var, getElement(var.getScope()));
        }
        
        return voiture;
    }
    
    public String getElement(Set<String> myHashSet){
        int size = myHashSet.size();
        int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
        int i = 0;
        for(Object obj : myHashSet) {
            if (i == item)
                return (String)obj;
            i++;
        }
        
        return null;
    }
}

