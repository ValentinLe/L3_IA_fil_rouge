
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
}
