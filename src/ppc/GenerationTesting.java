package ppc;

import representations.Variable;
import java.util.*;
import representations.Constraint;
import representations.Rule;

public class GenerationTesting {
    
    private Set<String> variablesColor;
    private Set<String> variablesBool;
    
    private Set<Variable> variables;
    
    private Set<String> domaineCouleur;
    private Set<String> domaineBool;
    
    private Set<Constraint> constraints;
    
    private int n;
    
    public GenerationTesting(Set<Constraint> constraints, int n){
        this.variablesColor = new HashSet<>(Arrays.asList(
            "toit","capot","hayon","droit","gauche"
        ));
        
        this.variablesBool = new HashSet<>(Arrays.asList(
            "sono", "toit ouvrant"
        ));
        
        this.domaineCouleur = new HashSet<>(Arrays.asList(
            "rouge","blanc","noir"
        ));
        
        this.domaineBool = new HashSet<>(Arrays.asList(
            "true","false"   
        ));
        
        this.variables = new HashSet<>();
        
        computeVariables(variablesColor, domaineCouleur);
        computeVariables(variablesBool, domaineBool);
        System.out.println(variables);
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
    
    public void computeVariables(Set<String> names, Set<String> domaine){
        
        Iterator<String> iter = names.iterator();
        
        while(iter.hasNext()){
            
            this.variables.add(new Variable(iter.next(), domaine));
        }
    }
    
    public String getElement(Set<String> myHashSet){
        int size = myHashSet.size();
        int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
        int i = 0;
        for(Object obj : myHashSet)
        {
            if (i == item)
                return (String)obj;
            i++;
        }
        
        return null;
    }
}

