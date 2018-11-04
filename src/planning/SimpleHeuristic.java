
package planning;

import java.util.Map;
import representations.Variable;

/**
 * Simple heuristic that count the number of the colors not equal between the
 * initial state and the target state
 */
public class SimpleHeuristic implements HeuristicState {

    /**
     * Calculates the heuristic value between an initial state and the target state
     * @param currentState the initial state
     * @param goal the target state
     * @return the value of the heuristic
     */
    @Override
    public int heuristicValue(State currentState, State goal) {
        int cpt = 0;
        // the car of the initial state
        Map<Variable, String> voitureState = currentState.getVoiture();
        // the car of the target state
        Map<Variable, String> voitureGoal = goal.getVoiture();
        for (Variable var : voitureState.keySet()) {
            // for all variable of the initial state's car
            if (voitureState.get(var)!="TRUE" && voitureState.get(var)!="FALSE" && !voitureState.get(var).equals(voitureGoal.get(var))) {
                // if the value isn't TRUE or FALSE then it's a color and if the
                // value doesn't correspond with the variable of the goal's state
                cpt += 1;
            }
        }
        return cpt;
    }
}
