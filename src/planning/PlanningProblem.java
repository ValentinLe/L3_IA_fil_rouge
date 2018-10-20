
package planning;

import java.util.*;

/**
 * PlanningProblem contains the DFS and BFS methods
 * 
 */
public class PlanningProblem {

    protected State initialState;
    protected State goal;
    public Set<Action> actions;
    /** counter of node */
    protected int nbNode;

    /**
     * Build an instance of PlanningProblem
     * @param initialState the initial state of the problem
     * @param goal the target state
     * @param actions set of possible actions
     */
    public PlanningProblem(State initialState, State goal, Set<Action> actions) {
        this.initialState = initialState;
        this.goal = goal;
        this.actions = actions;
        this.nbNode = 0;
    }

    /**
     * Getter of the number of node
     * @return the number of node
     */
    public int getNbNode() {
        return this.nbNode;
    }
    
    /**
     * Increments the number of node
     */
    public void upNbNode() {
        this.nbNode += 1;
    }
    
    /**
     * Initializes the number of node to 0
     */
    public void initNbNode() {
        this.nbNode = 0;
    }

    /**
     * Function to call the DFS method
     * @return the plan of action to execute to go to the target state
     */
    public Stack<Action> dfs() {
        this.initNbNode();
        return this.dfs(this.initialState, new Stack<>(), new HashSet<>());
    }

    /**
     * DFS recursive method
     * @param state the state of the beginning
     * @param plan the plan to build
     * @param closed the set of state visited
     * @return the plan of action to execute to go to the target state
     */
    public Stack<Action> dfs(State state, Stack<Action> plan,
            Set<State> closed) {

        this.upNbNode();
        if (state.satisfies(this.goal.getVoiture())) {
            Collections.reverse(plan);
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

    /**
     * DFS iterative method
     * @return the plan of action to execute to go to the target state
     */
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

    /**
     * BFS iterative method
     * @return the plan of action to execute to go to the target state
     */
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

    /**
     * Build the plan of action to go to the initialState to the goal
     * @param father map of (son, father) 
     * @param actions 
     * @param goal
     * @return 
     */
    public Queue<Action> getBfsPlan(Map<State, State> father,
            Map<State, Action> actions, State goal) {
        
        Queue<Action> plan = new LinkedList<>();
        Action action = actions.get(goal);
        do {
            plan.add(action);
            goal = father.get(goal);
            action = actions.get(goal);
        } while (goal != null && action != null);
        Collections.reverse((LinkedList<Action>) plan);
        return plan;
    }
}
