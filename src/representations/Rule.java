
package representations;

import java.util.*;

/**
 * A Rule is a constraint that represents a implication :  a implies b
 *
 */

public class Rule implements Constraint {

    private Set<Variable> scope;
    private Map<Variable,String > premisse;
    private Map<Variable, String> conclusion;

    /**
     * Build a instance of Rule
     * @param scope All Variables involved in the Constraint
     * @param premisse The premisse of the rule
     * @param conclusion The conclusion of the rule
     */

    public Rule(Set<Variable> scope, Map<Variable, String> premisse, Map<Variable,String> conclusion) {
        this.scope = scope;
        this.premisse = premisse;
        this.conclusion = conclusion;
    }

    /**
     * Getter of the rule's scope
     * @return the rule's scope
     */
    @Override
    public Set<Variable> getScope() {
      return this.scope;
    }

    public Map<Variable, String> getPremisse() {
        return this.premisse;
    }

    public Map<Variable, String> getConclusion() {
        return this.conclusion;
    }

    /**
     * Test if the part (premise or conclusion) is satisfied by a car
     * @param voiture the car of the test
     * @param part the part (premise or conclusion)
     * @param testPart the boolean of the initialization of test
     * @return the result of the test or null if at least one of variables of the part are a null value
     */
    public static Boolean isPartSatisfied(Map<Variable, String> voiture, Map<Variable, String> part, boolean testPart) {
        boolean testRes = testPart;
        for (Variable var : part.keySet()) { // for all of variables in the part
            if (voiture.get(var) == null) {
                // if the variable isn't defined in the car
                return null;
            }
            if (testPart) {
                // if the part is the premisse
                if(!voiture.get(var).equals(part.get(var))) {
                    // if one of the values of the variable doesn't match the constraint
                    testRes = false;
                }
            } else {
                // if the part is the conclusion
                if(voiture.get(var).equals(part.get(var))){
                    // if one of the values of the variable matches the constraint
                    testRes = true;
                }
            }
        }
        return testRes; // boolean of the part unchanged
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
            // if there are no variables in the car
            return true;
        }

        if (this.premisse != null) {
            // if there is a premisse
            p = isPartSatisfied(voiture, this.premisse, p);
            if (p == null) {
                // if there is at least one variable of the premisse not defined in the car
                return true;
            }
        }

        if (this.conclusion != null) {
            // if there is a conclusion
            c = isPartSatisfied(voiture, this.conclusion, c);
            if (c == null) {
                // if there is at least one variable of the conclusion not defined in the car
                return true;
            }
        }
        return !p || c;
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
           while (iter.hasNext()) {
               // iteration on the variables of the part
               Variable var = iter.next();
               // add string : variable = value
               ch += var.getName()+ " = " + part.get(var);
               if (iter.hasNext()) {
                   // if there is a next variable in the part
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
        
        if (this.conclusion == null) {
            // if it's a incompatibilityContraint
            ch += "!(";
        }
        
        // add the "toString" of the premisse with the separator
        ch += this.getStringMap(this.premisse, "&&");

        if (this.premisse != null && this.conclusion != null) {
            // if there are a premisse and a conclusion
            ch += " -> ";
        }

        // add the "toString" of the conclusion with the separator
        ch += this.getStringMap(this.conclusion, "||");

        if (this.conclusion == null) {
            // if it's a incompatibilityContraint
            ch += ")";
        }
        
        return ch;
    }

    public int countVariable(Map<Variable, Set<String>> domaines, Map<Variable, String> part) {
        // counter of variable in the domaines of varibale not defined, that there are in this constraint
        int cpt = 0;
        for (Variable var : part.keySet()) { // for all variables in the conclusion
            if (domaines.containsKey(var)) {
                // if variable is not defined in the car
                cpt += 1;
            }
            if (cpt > 1) {
                // it's usefull tu continue when the value of the counter are more than one
                return cpt;
            }
        }
        return cpt;
    }
    
    public boolean filterWithPart(Map<Variable, String> voiture, Map<Variable, Set<String>> domaines, Map<Variable, String> part, boolean equal) {
        boolean isFilter = false;
        if (countVariable(domaines, part) == 1) {
            // if there is only one variable not difined in the conclusion
            Variable varNotAssigned = null;
            for (Variable var : part.keySet()) { // for all variables in the conclusion

                String valueVoiture = voiture.get(var);
                if (equal && valueVoiture != null && valueVoiture.equals(part.get(var))){
                    // if the car has the variable and the value corresponding to the constraint, no need to filter
                    return false;
                }
                if (!equal && valueVoiture != null && !valueVoiture.equals(part.get(var))){
                    // if the car has the variable and the value corresponding to the constraint, no need to filter
                    return false;
                }
                if (voiture.get(var) == null) {
                    // assignation of the variable that isn't defined in the car
                    varNotAssigned = var;
                }
            }
            // reduction of the domain of the variable not difined to the value of this constraint
            Set<String> domaineVarNotAssi = new HashSet<>();
            domaineVarNotAssi.add(part.get(varNotAssigned));
            if (!domaines.get(varNotAssigned).equals(domaineVarNotAssi)) {
                // if the domaine hasn't already been filtered
                isFilter = true;
                domaines.put(varNotAssigned, domaineVarNotAssi);
            }
        }
        return isFilter;
    }

    @Override
    public boolean filtrer(Map<Variable, String> voiture, Map<Variable, Set<String>> domaines) {
        if (this.conclusion != null) {
            // if there is a conclusion
            return this.filterWithPart(voiture, domaines, this.conclusion, true);
        }
        return false;
    }

}
