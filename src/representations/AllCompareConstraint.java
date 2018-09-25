
package representations;

import java.util.Iterator;
import java.util.Set;

/**
 * This constraint compare variables on their value
 * 
 */
public abstract class AllCompareConstraint implements Constraint {

    protected Set<Variable> variables;
    protected boolean not;

    /**
     * Build a instance of AllCompareConstraint
     * @param variables all variables of the constraint
     * @param not if you want the not of the constraint
     */
    public AllCompareConstraint(Set<Variable> variables, boolean not){
        this.variables = variables;
        this.not = not;
    }

    /**
     * Build a instance of AllCompareConstraint
     * @param variables all variables of the constraint
     */
    public AllCompareConstraint(Set<Variable> variables){
        this(variables,false);
    }

    /**
     * Getter of the constraint's scope
     * @return the scope of this constraint
     */
    @Override
    public Set<Variable> getScope() {
        return this.variables;
    }

    /**
     * Getter of the string separator, "=" for equal, "!=" for different
     * @return the string separator
     */
    public abstract String getSeparator();

    /**
     * The representation of a allCompareConstraint with separator
     * @return the string representation
     */
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
