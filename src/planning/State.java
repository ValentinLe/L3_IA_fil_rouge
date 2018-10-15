
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
    
    @Override
    public int hashCode() {
        return this.voiture.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(this==o) {
          return true;
        } else {
            if(o instanceof State) {
              return this.voiture.equals(((State) o).getVoiture());
            } else {
                return false;
            }
        }
    }

    @Override
    public String toString() {
        String ch = "";
        for (Variable var : this.voiture.keySet()) {
            ch += var + " = " + this.voiture.get(var) + "\n";
        }
        return ch;
    }
}
