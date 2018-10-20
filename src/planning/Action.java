
package planning;

import java.util.*;
import representations.*;

/**
 * represents an Action to apply on a car
 * 
 */
public class Action {

    private Set<Rule> preconditions;
    private int cost;

    /**
     * Build an instance of Action
     * @param preconditions a set of rules to apply
     * @param cost the cost of the action's application
     */
    public Action(Set<Rule> preconditions, int cost){
        this.preconditions = preconditions;
        this.cost = cost;
    }

    /**
     * Getter of the preconditions
     * @return the preconditions of the action
     */
    public Set<Rule> getPreconditions() {
        return this.preconditions;
    }

    /**
     * Getter of the action's cost
     * @return the cost of the action
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Test is the action is applicable on the state
     * @param state the state
     * @return true if the action is applicable
     */
    public boolean isApplicable(State state) {
        for (Rule rule : this.preconditions) {
            // for all rule in the precondistions
            if (state.satisfies(rule.getPremisse()) && !state.satisfies(rule.getConclusion())) {
                // if the state satisfies the premisse of the rule and
                // the state don't satisfies the conclusion (if one or more value(s) 
                // of the car's variables not corresponding to the conclusion's value
                return true;
            }
        }
        return false;
    }

    /**
     * Applies the action on the state
     * @param state a copy of the result of the application of the action
     * @return a state copy with the action apply
     */
    public State apply(State state) {
        // create a copy of the state
        State copyState = state.getCopy();
        if (this.isApplicable(copyState)) {
            for (Rule rule : this.preconditions) {
                // for all rules in the preconditions
                if (copyState.satisfies(rule.getPremisse())) {
                    // if the copyState satisfies the premisse of the rule
                    for (Variable var : rule.getConclusion().keySet()) {
                        // for all variables in the conclusion
                        // modification of the value in the copyState's car
                        // car[variable] = rule's_conclusion[var]
                        copyState.getVoiture().put(var, rule.getConclusion().get(var));
                    }
                }
            }
        }
        return copyState;
    }

    /**
     * toString of the action is the toString of all rules
     * @return String representation of the action
     */
    @Override
    public String toString() {
        String ch = "";
        for (Rule rule : this.preconditions) {
            ch += rule.toString() + "\n";
        }
        return ch;
    }
}
