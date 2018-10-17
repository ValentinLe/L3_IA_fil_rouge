
package planning;

import java.util.Map;
import representations.Variable;

public class SimpleHeuristic implements Heuristic {

    @Override
    public int heuristicValue(State goal, State state) {
        int cpt = 0;
        Map<Variable, String> voitureState = state.getVoiture();
        Map<Variable, String> voitureGoal = goal.getVoiture();
        for (Variable var : voitureState.keySet()) {
            if (!voitureState.get(var).equals(voitureGoal.get(var))) {
                cpt += 1;
            }
        }
        return cpt;
    }
    
}
