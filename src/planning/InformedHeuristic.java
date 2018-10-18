
package planning;

public class InformedHeuristic extends AbstractHeuristic {

    public InformedHeuristic(State initiaState, State goal) {
        super(initiaState, goal);
    }

    @Override
    public int heuristicValue(State currentState, State goal) {
        return currentState.getDistance() + super.heuristicValue(currentState, goal);
    }
    
}
