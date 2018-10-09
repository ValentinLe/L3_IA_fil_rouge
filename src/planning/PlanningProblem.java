
package planning;

import java.util.*;
import representations.*;

public class PlanningProblem {
    
    private State initialState;
    private State goal;
    private Set<Action> actions;
    
    public PlanningProblem(State initialState, State goal, Set<Action> actions) {
        this.initialState = initialState;
        this.goal = goal;
        this.actions = actions;
    }
    
    public Stack<Action> dfs(State state, Stack<Action> plan, 
            Set<State> closed) {
        
        if (state.satisfies(this.goal.getVoiture())) {
            return plan;
        } else {
            for (Action action : this.actions) {
                if (action.isApplicable(state)) {
                    State next = action.apply(state);
                    if (!closed.contains(next)) {
                        plan.push(action);
                        closed.add(next);
                        Stack<Action> subplan = dfs(next, plan, closed);
                        if (!(subplan != null)) {
                            return subplan;
                        } else {
                            plan.pop();
                        }
                    }
                }
            }
            return plan;
        }
    }
    
    public void dfsIter() {
        
    }
    
    public Queue<State> bfs() {
        Map<State, State> father = new HashMap<>();
        Map<State, Action> plan = new HashMap<>();
        Set<State> closed = new HashSet<>();
        Queue<State> open = new LinkedList<>();
        open.add(this.initialState);
        father.put(this.initialState, null);
        while (!open.isEmpty()) {
            
        }
        return null;
    }
}
