
package planning;

import java.util.Map;
import representations.Variable;

public abstract class AbstractHeuristic implements Heuristic {
    
    protected State initialState;
    protected State goal;
    
    public AbstractHeuristic(State initiaState, State goal) {
        this.initialState = initiaState;
        this.goal = goal;
    }
    
    @Override
    public int heuristicValue(State currentState, State goal) {
        int cpt = 0;
        Map<Variable, String> voitureState = currentState.getVoiture();
        Map<Variable, String> voitureGoal = goal.getVoiture();
        for (Variable var : voitureState.keySet()) {
            if (!voitureState.get(var).equals(voitureGoal.get(var))) {
                cpt += 1;
            }
        }
        return cpt;
    }
}
