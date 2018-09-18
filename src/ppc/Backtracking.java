
package ppc;

import representations.Variable;
import java.util.*;

public class Backtracking {
    
    private Set<String> variablesColor;
    private Set<String> variablesBool;
    
    private Set<Variable> variables;
    
    private Set<String> domaineCouleur;
    private Set<String> domaineBool;
    
    public Backtracking(){
        this.variablesColor = new HashSet<String>(Arrays.asList(
            "toit","capot","hayon","droite","gauche"
        ));
        
        this.variablesBool = new HashSet<String>(Arrays.asList(
            "sono", "toit ouvrant"
        ));
        
        this.domaineCouleur = new HashSet<String>(Arrays.asList(
            "rouge","blanc","noir"
        ));
        
        this.domaineBool = new HashSet<String>(Arrays.asList(
            "true","false"   
        ));
        
        this.variables = new HashSet<Variable>();
        
        computeVariables(variablesColor, domaineCouleur);
        computeVariables(variablesBool, domaineBool);
        System.out.println(variables);
        
    }
    
    public Map<Variable,String> generateCar(){
        Map<Variable,String> voiture = new HashMap<Variable,String>();
        
        Iterator iter = this.variables.iterator();
        
        while(iter.hasNext()){
            Variable var = (Variable)iter.next();
            voiture.put(var, getElement(var.getScope()));
        }
        
        return voiture;
    }
    
    public void computeVariables(Set<String> names, Set<String> domaine){
        
        Iterator iter = names.iterator();
        
        while(iter.hasNext()){
            
            this.variables.add(new Variable((String)iter.next(), domaine));
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
