
package representations;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class AllComparationConstraint implements Constraint {
    
    protected Set<Variable> variables;
    protected boolean not;

    public AllComparationConstraint(Set<Variable> variables, boolean not){
        this.variables = variables;
        this.not = not;
    }

    public AllComparationConstraint(Set<Variable> variables){
        this(variables,false);
    }

    @Override
    public Set<Variable> getScope() {
        return this.variables;
    }

    @Override
    public abstract boolean isSatisfiedBy(Map<Variable, String> contraintes);
    
    public abstract String getSeparator();
    
    @Override
    public String toString(){
        String ch = "";
        if (this.not) {
            ch += "!(";
        }
        Iterator<Variable> iter = this.variables.iterator();

        while(iter.hasNext()) {
            ch += iter.next();
            if (iter.hasNext()) {
                ch += this.getSeparator();
            }
        }
        if (this.not) {
            ch += ")";
        }
        return ch;
    }
}
