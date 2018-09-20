
package representations;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AllEqualConstraint implements Constraint {

    private Set<Variable> variables;
    private boolean not;

    public AllEqualConstraint(Set<Variable> variables, boolean not){
        this.variables = variables;
        this.not = not;
    }

    public AllEqualConstraint(Set<Variable> variables){
        this(variables,false);
    }


    /*
    Return variables
    */
    @Override
    public Set<Variable> getScope() {
        return this.variables;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, String> voiture) {
        System.out.println("isSatisfiedBy de AllEqual");
        Iterator<Variable> iter = this.variables.iterator();

        Variable var = iter.next();
        String pastValue = voiture.get(var);
        while (pastValue==null && iter.hasNext()) {
          var = iter.next();
          pastValue = voiture.get(var);
        }

        String value;
        if (!iter.hasNext()) {
            System.out.println("Résultat : "+!this.not);
            return !this.not;
        }
        do {
            var = iter.next();
            value = voiture.get(var);
            if (value==null) {
                continue;
            }
            if (!pastValue.equals(value)) {
                System.out.println("Résultat : "+this.not);
                return this.not;
            }
        } while (iter.hasNext());
        System.out.println("Résultat : "+!this.not);
        return !this.not;
    }

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
                ch += " = ";
            }
        }
        if (this.not) {
            ch += ")";
        }
        return ch;
    }
}
