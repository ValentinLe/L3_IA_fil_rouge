
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
    
    public Queue<Action> bfs() {
        Map<State, State> father = new HashMap<>();
        Map<State, Action> plan = new HashMap<>();
        Set<State> closed = new HashSet<>();
        Queue<State> open = new LinkedList<>();
        open.add(this.initialState);
        father.put(this.initialState, null);
        while (!open.isEmpty()) {
            State state = open.remove();
            closed.add(state);
            for (Action action : this.actions) {
                if (action.isApplicable(state)) {
                    State next = action.apply(state);
                    if (!closed.contains(next) && !open.contains(next)) {
                        father.put(next, state);
                        plan.put(next, action);
                        if (next.satisfies(this.goal.getVoiture())) {
                            return this.getBfsPlan(father, plan, next);
                        } else {
                            open.add(next);
                        }
                    }
                }
            }
        }
        return null;
    }
    
    public Queue<Action> getBfsPlan(Map<State, State> father, 
            Map<State, Action> actions, State goal) {
        
        Queue<Action> plan = new LinkedList<>();
        while (goal != null) {
            plan.add(actions.get(goal));
            goal = father.get(goal);
        }
        Collections.reverse((LinkedList<Action>)plan);
        return plan;
    }
}
