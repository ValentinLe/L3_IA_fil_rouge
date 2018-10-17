
package planning;

import java.util.*;
import representations.*;

public class PlanningProblem {

    protected State initialState;
    protected State goal;
    public Set<Action> actions;
    protected int nbNode;

    public PlanningProblem(State initialState, State goal, Set<Action> actions) {
        this.initialState = initialState;
        this.goal = goal;
        this.actions = actions;
        this.nbNode = 0;
    }

    public int getNbNode() {
        return this.nbNode;
    }
    
    public void upNbNode() {
        this.nbNode += 1;
    }
    
    public void initNbNode() {
        this.nbNode = 0;
    }

    public Stack<Action> dfs() {
        this.initNbNode();
        return this.dfs(this.initialState, new Stack<>(), new HashSet<>());
    }

    public Stack<Action> dfs(State state, Stack<Action> plan,
            Set<State> closed) {

        this.upNbNode();
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
                        if (!subplan.isEmpty()) {
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

    public Queue<Action> dfsIter() {
        this.initNbNode();
        Map<State, State> father = new HashMap<>();
        Map<State, Action> plan = new HashMap<>();
        Set<State> closed = new HashSet<>();
        Stack<State> open = new Stack<>();
        open.push(this.initialState);
        father.put(this.initialState, null);
        while (!open.isEmpty()) {
            this.upNbNode();
            State state = open.pop();
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

    public Queue<Action> bfs() {
        this.initNbNode();
        Map<State, State> father = new HashMap<>();
        Map<State, Action> plan = new HashMap<>();
        Set<State> closed = new HashSet<>();
        Queue<State> open = new LinkedList<>();
        open.add(this.initialState);
        father.put(this.initialState, null);
        while (!open.isEmpty()) {
            this.upNbNode();
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
        Action action = actions.get(goal);
        do {
            plan.add(action);
            goal = father.get(goal);
            action = actions.get(goal);
        } while (goal != null && action != null);
        Collections.reverse((LinkedList<Action>)plan);
        return plan;
    }
}
