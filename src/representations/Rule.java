
package representations;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A Rule is a constraint that represents a implication :  a implique b
 * 
 */

public class Rule implements Constraint {
    
    private Set<Variable> scope;
    private Map<Variable,String > premisse;
    private Map<Variable, String> conclusion;
    private boolean not;
    
    /**
     * Build a instance of Rule
     * @param scope All Variables involved in the Constraint
     * @param premisse The premisse of the rule
     * @param conclusion The conclusion of the rule
     * @param not If you want not(rule)
     */

    public Rule(Set<Variable> scope, Map<Variable, String> premisse, Map<Variable,String> conclusion, boolean not) {
        this.scope = scope;
        this.premisse = premisse;
        this.conclusion = conclusion;
        this.not = not;
    }
    
    /**
     * Build a instance of Rule
     * @param scope All Variables involved in the Constraint
     * @param premisse The premisse of the rule
     * @param conclusion The conclusion of the rule
     */
    public Rule(Set<Variable> scope, Map<Variable, String> premisse, Map<Variable,String> conclusion) {
        this(scope, premisse, conclusion, false);
    }
    
    /**
     * Getter of the rule's scope
     * @return the rule's scope
     */
    @Override
    public Set<Variable> getScope() {
      return this.scope;
    }
    
    /**
     * Test if the part (premise or conclusion) is satisfied by a car
     * @param voiture the car of the test
     * @param part the part (premise or conclusion)
     * @param testPart the boolean of the initialization of test
     * @return the result of the test or null if one of the variable of the part are a null value
     */
    public Boolean isPartSatisfied(Map<Variable, String> voiture, Map<Variable, String> part, boolean testPart) {
        if (part != null) {
            for (Variable var : part.keySet()) {
                if (voiture.get(var) == null) {
                    return null;
                }
                if (testPart) {
                    if(!voiture.get(var).equals(part.get(var))) {
                        testPart = false;
                    }
                } else {
                    if(voiture.get(var).equals(part.get(var))){
                        testPart = true;
                    }
                }
            }
        }
        return testPart;
    }
    
    /**
     * Test if the rule is satisfied by a car
     * @param voiture the car of the test
     * @return the result of the test
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable,String> voiture) {
        Boolean p = true;
        Boolean c = false;

        if (voiture.isEmpty()) {
            return !this.not;
        }

        p = isPartSatisfied(voiture, this.premisse, p);
        if (p == null) {
            return !this.not;
        }

        c = isPartSatisfied(voiture, this.conclusion, c);
        if (c == null) {
            return !this.not;
        }

        if (this.not) {
            return p && !c;
        } else {
            return !p || c;
        }
    }
    
    /**
     * Construct a string of a part of the rule with the variable their value 
     * @param part the part of the rule 
     * @param separator the separator between each variables
     * @return the string result of the representation of the part
     */
    public String getStringMap(Map<Variable, String> part, String separator) {
        String ch = "";
        if (part != null) {
           Iterator<Variable> iter = part.keySet().iterator();
           while(iter.hasNext()){
               Variable var = iter.next();
               ch += var.getName()+ " = " + part.get(var);
               if (iter.hasNext()) {
                   ch += " " + separator + " ";
               }
           }
        }
        return ch;
    }
    
    /**
     * Construct a string representation of the rule
     * @return the string representation
     */
    @Override
    public String toString() {
        String ch = "";
        if (this.conclusion == null && !this.not) {
            ch += "!(";
        }

        ch += this.getStringMap(this.premisse, "&&");

        if (this.premisse != null && this.conclusion != null) {
            ch += " -> ";
        }

        ch += this.getStringMap(this.conclusion, "||");

        if (this.conclusion == null && !this.not) {
            ch += ")";
        }
        return ch;
    }

    @Override
    public boolean filtrer(Map<Variable, String> voiture, Map<Variable, Set<String>> dom) {
        return false;
    }

}
