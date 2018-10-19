
package planning;

import java.util.HashMap;
import java.util.Map;
import representations.*;


public class State implements Comparable<State> {

    private Map<Variable, String> voiture;
    private Integer distance;

    public State(Map<Variable, String> voiture, Integer distance) {
        this.voiture = voiture;
        this.distance = distance;
    }

    public State(Map<Variable, String> voiture) {
        this(voiture, Integer.MAX_VALUE);
    }
    
    public Map<Variable, String> getVoiture() {
        return this.voiture;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean satisfies(Map<Variable, String> partialState) {
        return Rule.isPartSatisfied(voiture, partialState, true);
    }

    public State getCopy() {
        return new State(new HashMap<>(this.voiture), this.distance);
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
    public int compareTo(State other) {
        return this.distance.compareTo(other.getDistance());
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
