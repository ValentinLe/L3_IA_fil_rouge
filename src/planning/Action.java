
package planning;

import java.util.Map;
import java.util.Set;
import representations.*;

public class Action {
    
    private Map<Variable, String> voiture;
    private Set<Rule> preconditions;
    
    public Action(Map<Variable,String> voiture, Set<Rule> preconditions){
        this.voiture = voiture;
        this.preconditions = this.preconditions;
    }
    
    public Map<Variable, String> getVoiture() {
        return this.voiture;
    }
    
    public Set<Rule> getPreconditions() {
        return this.preconditions;
    }
    
    public boolean isApplicable(State state) {
        for (Rule rule : this.getPreconditions()) {
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
                if (copyState.satisfies(rule.getPremisse())) {
                    for (Variable var : rule.getConclusion().keySet()) {
                        copyState.getVoiture().put(var,rule.getConclusion().get(var));
                    }
                }
            }
        }
        return copyState;
    }
}
