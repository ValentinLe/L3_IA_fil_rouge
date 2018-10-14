
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

    public boolean sameCar(Map<Variable, String> otherCar) {
      if (otherCar.size() != this.voiture.size()) {
        return false;
      } else {
        for (Variable var : this.voiture.keySet()) {
          if (!otherCar.get(var).equals(this.voiture.get(var))) {
            return false;
          }
        }
        return true;
      }
    }

    @Override
    public boolean equals(Object o) {
        if(this==o) {
          return true;
        } else {
            if(o instanceof State) {
              return this.sameCar(((State) o).getVoiture());
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
