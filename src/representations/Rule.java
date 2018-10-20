
package representations;

import java.util.*;

/**
 * A Rule is a constraint that represents an implication :  a implies b
 *
 */

public class Rule implements Constraint {

    private Set<Variable> scope;
    private Map<Variable,String > premisse;
    private Map<Variable, String> conclusion;

    /**
     * Build an instance of Rule
     * @param scope All Variables involved in a Constraint
     * @param premisse The premisse of the rule
     * @param conclusion The conclusion of the rule
     */

    public Rule(Set<Variable> scope, Map<Variable, String> premisse, Map<Variable,String> conclusion) {
        this.scope = scope;
        this.premisse = premisse;
        this.conclusion = conclusion;
    }

    public Rule(Map<Variable, String> premisse, Map<Variable,String> conclusion) {
        this.premisse = premisse;
        this.conclusion = conclusion;
        this.createScope();
    }

    /**
     * Getter method of the rule's scope
     * @return the rule's scope
     */
    @Override
    public Set<Variable> getScope() {
      return this.scope;
    }

    /**
     * Getter method of the rule's premisse
     * @return the premisse of the rule
     */
    public Map<Variable, String> getPremisse() {
        return this.premisse;
    }

    /**
     * Getter method of the rule's conclusion
     * @return the conclusion of the rule
     */
    public Map<Variable, String> getConclusion() {
        return this.conclusion;
    }

    /**
     * build the scope of the rule
     */
    public void createScope() {
        this.scope = new HashSet<>();
        this.scope.addAll(this.premisse.keySet());
        this.scope.addAll(this.conclusion.keySet());
    }

    /**
     * Test if the part (premise or conclusion) is satisfied by a car
     * @param voiture the car of the test
     * @param part the part (premise or conclusion)
     * @param testPart the boolean of the initialization of a test (the neutral element 
     * of the part, true for the premisse and false for the conclusion)
     * @return a test result or a null if at least one of the variable's part values is null
     */
    public static Boolean isPartSatisfied(Map<Variable, String> voiture, Map<Variable, String> part, boolean testPart) {
        boolean testRes = testPart;
        for (Variable var : part.keySet()) { // for all of the variables in the part
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
        return testRes;
    }

    /**
     * Test if the rule is satisfied by a car
     * @param voiture the car of the test
     * @return test result
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
                // if there is at least one the premisse's variable is not defined in the car
                return true;
            }
        }

        if (this.conclusion != null) {
            // if there is a conclusion
            c = isPartSatisfied(voiture, this.conclusion, c);
            if (c == null) {
                // if there is at least one of the conclusion's variables is not defined in the car
                return true;
            }
        }
        return !p || c;
    }

    /**
     * Construct a string of a rule's part with the variable as their value
     * @param part the part of the rule
     * @param separator the separator between each variable
     * @return the string result of the representation of a part
     */
    public String getStringMap(Map<Variable, String> part, String separator) {
        String ch = "";
        if (part != null) {
           Iterator<Variable> iter = part.keySet().iterator();
           while (iter.hasNext()) {
               // iteration on the variables of a part
               Variable var = iter.next();
               // add the string "variable = value"
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
     * @return the string's representation
     */
    @Override
    public String toString() {
        String ch = "";

        if (this.conclusion == null) {
            // if it's an incompatibilityContraint
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
            // if it's an incompatibilityContraint
            ch += ")";
        }

        return ch;
    }

    /**
     * Counts the number of the part's variables present in the map
     * @param domaines the map of variable with its copied domain
     * @param part the part to count
     * @return the number of the part's variables in the map
     */
    public int countVariable(Map<Variable, Set<String>> domaines, Map<Variable, String> part) {
        // counter of the domaine's not defined variables in this constraint
        int cpt = 0;
        for (Variable var : part.keySet()) { // for all variables in the conclusion
            if (domaines.containsKey(var)) {
                // if variable is not defined in the car
                cpt += 1;
            }
        }
        return cpt;
    }

    /**
     * Filters the domain's variables
     * @param voiture a car for the filtering test
     * @param domaines variables and its copied domain for filtering
     * @param part the part for filtering (premisse or conclusion)
     * @param equal test of equality : false for IncompatibilityConstraint
     * or true for Rule
     * @see IncompatibilityConstraint
     * @return true if a filtering occures
     */
    public boolean filterWithPart(Map<Variable, String> voiture,
            Map<Variable, Set<String>> domaines, Map<Variable, String> part, boolean equal) {

        boolean isFilter = false;
        if (countVariable(domaines, part) == 1) {
            // if there is only one variable not defined in the conclusion
            Variable varNotAssigned = null;
            for (Variable var : part.keySet()) { // for all the variables in the conclusion

                String valueVoiture = voiture.get(var);
                if (equal && valueVoiture != null && valueVoiture.equals(part.get(var))){
                    // if the car has the variable and the value corresponding to the constraint, no need for filtering
                    return false;
                }
                if (!equal && valueVoiture != null && !valueVoiture.equals(part.get(var))){
                    // if the car has the variable and the value corresponding to the constraint, no need for filtering
                    return false;
                }
                if (voiture.get(var) == null) {
                    // assigne the variables that aren't defined in the car
                    varNotAssigned = var;
                }
            }
            // reduction of the variable's domain that is not defined in value of this constraint
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

    /**
     * Filters the domain's variables
     * @param voiture a car for the filtering test
     * @param domaines variables and its copied domain for filtering
     * @return true if a filtering occures
     */
    @Override
    public boolean filtrer(Map<Variable, String> voiture, Map<Variable, Set<String>> domaines) {
        if (this.conclusion != null) {
            // if there is a conclusion
            return this.filterWithPart(voiture, domaines, this.conclusion, true);
        }
        return false;
    }

}
