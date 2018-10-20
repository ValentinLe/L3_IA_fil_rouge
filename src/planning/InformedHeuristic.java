
package planning;

import java.util.Map;
import representations.Variable;

/**
 * Informed heuristic that count the number of all components not equal between the
 * initial state and the target state
 */
public class InformedHeuristic implements Heuristic {

    /**
     * Calculate the heuristic value between an initial state to the target state
     * @param currentState the initial state
     * @param goal the target state
     * @return the value of the heuristic
     */
    @Override
    public int heuristicValue(State currentState, State goal) {
        int cpt = 0;
        // the car of initial state
        Map<Variable, String> voitureState = currentState.getVoiture();
        // the car of the target state
        Map<Variable, String> voitureGoal = goal.getVoiture();
        for (Variable var : voitureState.keySet()) {
            // for all variable of the initial state's car
            if (!voitureState.get(var).equals(voitureGoal.get(var))) {
                // if the value not corresponding with the variable of the goal's state
                cpt += 1;
            }
        }
        return cpt;
    }
    
}
