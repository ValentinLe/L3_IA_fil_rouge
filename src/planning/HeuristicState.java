
package planning;

/**
 * Interface of heuristics
 * 
 */
public interface HeuristicState {
    
    /**
     * Calculate the heuristic value between an initial state to the target state
     * @param currentState the initial state
     * @param goal the target state
     * @return the value of the heuristic
     */
    public int heuristicValue(State currentState, State goal);
    
}
