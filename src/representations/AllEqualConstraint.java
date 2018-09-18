
package representations;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AllEqualConstraint implements Constraint {

    private Set<Variable> variables;
    private boolean isNot;
    
    public AllEqualConstraint(Set<Variable> variables, boolean isNot){
        this.variables = variables;
        this.isNot = isNot;
    }
    
    public AllEqualConstraint(Set<Variable> variables){
        this(variables,false);
    }
    
    

    @Override
    /*
    Return variables
    */
    public Set<Variable> getScope() {
        return this.variables;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, String> voiture) {

        Iterator iter = this.variables.iterator();
        
        Variable var = (Variable) iter.next();
        String pastValue = (String)voiture.get(var);
        System.out.println(voiture + "  " + var);
        String value;
        do {
            var = (Variable) iter.next();
            value = (String)voiture.get(var);
            if (!pastValue.equals(value)) {
                return this.isNot;
            }
        } while (iter.hasNext());
        return !this.isNot;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("Les variables suivantes doivent être égales:\n");
        res.append(this.variables);

        return res.toString();
    }
}
