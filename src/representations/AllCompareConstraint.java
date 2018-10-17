
package representations;

import java.util.Iterator;
import java.util.Set;

/**
 * This class compares the values of constraint
 *
 */
public abstract class AllCompareConstraint implements Constraint {

    protected Set<Variable> variables;

    /**
     * Build an instance of AllCompareConstraint
     * @param variables all the variables of constraint
     */
    public AllCompareConstraint(Set<Variable> variables){
        this.variables = variables;
    }

    /**
     * Getter method of constraint's scope
     * @return the scope of this constraint
     */
    @Override
    public Set<Variable> getScope() {
        return this.variables;
    }

    /**
     * Getter method of the string separator, "=" for equal, "!=" for different
     * @return the string's separator
     */
    public abstract String getSeparator();

    /**
     * Representation of a allCompareConstraint with separators
     * @return the string's representation
     */
    @Override
    public String toString(){
        String ch = "";
        Iterator<Variable> iter = this.variables.iterator();

        while(iter.hasNext()) {
            ch += iter.next();
            if (iter.hasNext()) {
                ch += this.getSeparator();
            }
        }
        return ch;
    }
}
