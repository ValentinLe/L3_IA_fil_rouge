
package planning;

import java.util.Map;
import java.util.Set;
import representations.*;

public class Action {
    private Set<Rule> preconditions;
    
    public Action(Set<Rule> preconditions){
        this.preconditions = preconditions;
    }
    
    public Set<Rule> getPreconditions() {
        return this.preconditions;
    }
    
    public boolean isApplicable(State state) {
        for (Rule rule : this.preconditions) {
            if (state.satisfies(rule.getPremisse())) {
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
