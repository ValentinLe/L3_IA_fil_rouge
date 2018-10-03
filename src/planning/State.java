
package planning;

import java.util.HashMap;
import java.util.Map;
import representations.*;


public class State {
    
    private Map<Variable, String> voiture;
    
    public State(Map<Variable, String> voiture) {
        this.voiture = voiture;
    }
    
    public Map<Variable, String> getVoiture() {
        return this.voiture;
    }
    
    public boolean satisfies(Map<Variable, String> partialState) {        
        return Rule.isPartSatisfied(voiture, partialState, true);
    }
    
    public State getCopy() {
        return new State(new HashMap<>(this.voiture));
    }
}
