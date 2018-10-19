
package planning;

import java.util.Map;
import representations.Variable;

public class SimpleHeuristic extends AbstractHeuristic {

    public SimpleHeuristic(State initiaState, State goal) {
        super(initiaState, goal);
    }
    
    @Override
    public int heuristicValue(State currentState, State goal) {
        int cpt = 0;
        Map<Variable, String> voitureState = currentState.getVoiture();
        Map<Variable, String> voitureGoal = goal.getVoiture();
        for (Variable var : voitureState.keySet()) {
            if (voitureState.get(var)!="TRUE" && voitureState.get(var)!="FALSE" && !voitureState.get(var).equals(voitureGoal.get(var))) {
                cpt += 1;
            }
        }
        return cpt;
    }
}
