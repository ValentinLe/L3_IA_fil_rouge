
package planning;

import java.util.*;
import representations.*;

/**
 * represent an Action 
 * 
 */
public class Action {

    private Set<Rule> preconditions;
    private int cost;

    public Action(Set<Rule> preconditions, int cost){
        this.preconditions = preconditions;
        this.cost = cost;
    }

    public Set<Rule> getPreconditions() {
        return this.preconditions;
    }

    public int getCost() {
        return this.cost;
    }

    public boolean isApplicable(State state) {
        for (Rule rule : this.preconditions) {
            if (state.satisfies(rule.getPremisse()) && !state.satisfies(rule.getConclusion())) {
                return true;
            }
        }
        return false;
    }

    public State apply(State state) {
        State copyState = state.getCopy();
        if (this.isApplicable(state)) {
            for (Rule rule : this.preconditions) {
                if (state.satisfies(rule.getPremisse())) {
                    for (Variable var : rule.getConclusion().keySet()) {
                        copyState.getVoiture().put(var,rule.getConclusion().get(var));
                    }
                }
            }
        }
        return copyState;
    }

    @Override
    public String toString() {
        String ch = "";
        for (Rule rule : this.preconditions) {
            ch += rule.toString() + "\n";
        }
        return ch;
    }
}
