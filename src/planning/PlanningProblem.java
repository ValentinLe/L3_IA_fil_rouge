
package planning;

import java.util.*;

/* RESULTS OF TESTS

Tests with 3 colors in the component_color :
- dfs :
nodes : 594, 469 and 153
number of actions : 468, 593 and 152

- dfs iterative :
nodes : 191, 11, 41
number of actions : 35, 113 and 11

- bfs :
nodes : 2124, 1962 and 81
number of actions : 10, 10 and 5
__________________________________________

Tests with 5 colors in the component_color :
- dfs :
nodes : 3216, 2774, 2419 and StackOverflowError
number of actions : 2418, 2773, 3115 and StackOverflowError

- dfs iterative :
nodes : 230, 5, 491, 1922, 2242
number of actions : 5, 309, 1133, 1280, 145

- bfs :
nodes : 18884, 16159, 637, 17091, 16578
number of actions : 10, 5, 10, 10, 10
___________________________________________

Tests with 8 colors in the component_color :
- dfs : StackOverflowError

- dfs iterative : we interrupt the program after 30 minutes because it's so long...

- bfs : we interrupt the program after 30 minutes because it's so long too...
___________________________________________

Conclusion :
The bfs method with few colors possible find a solution quickly but it's not the
best solution, there are many actions in the plan and with 5 or 8 colors there are
StackOverflowError or it's infinity. The dfs isn't a good algorithm to find a solution.
As well, the bfs with few colors possible find a solution not quickly but there
are just the actions that are needed with many node browsed. With 8 colors, the algorithm
is so long to find a solution. The bfs isn't a good algorithm to find a solution.
*/


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
     * Function to call the DFS recursive method with the good arguments
     * @return the plan of action to execute to go to the target state
     */
    public Stack<Action> dfs() {
        // initialize the number of node
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
            // reverse the plan
            Collections.reverse(plan);
            return plan;
        } else {
            for (Action action : this.actions) {
                // for all action in the set of actions of the problem
                if (action.isApplicable(state)) {
                    // if the action is applicable on the state
                    // apply the action to build a new state
                    State next = action.apply(state);
                    if (!closed.contains(next)) {
                        // the new state isn't already visited
                        // add to the plan
                        plan.push(action);
                        // add to the set of visited state
                        closed.add(next);
                        // recusivity with the new state
                        Stack<Action> subplan = dfs(next, plan, closed);
                        if (!subplan.isEmpty()) {
                            // if the recursivity return an empty plan
                            return subplan;
                        } else {
                            // remove the new state
                            plan.pop();
                        }
                    }
                }
            }
            // empty plan
            return plan;
        }
    }

    /**
     * DFS iterative method
     * @return the plan of action to execute to go to the target state
     */
    public Queue<Action> dfsIter() {
        // initialize the counter of node
        this.initNbNode();
        // map of (son, father)
        Map<State, State> father = new HashMap<>();
        // map of the plan state and action to go to this state
        Map<State, Action> plan = new HashMap<>();
        // set of visited state
        Set<State> closed = new HashSet<>();
        // stack of state to visit
        Stack<State> open = new Stack<>();
        // add the initial state to the open
        open.push(this.initialState);
        // the initial state hasn't a father
        father.put(this.initialState, null);
        while (!open.isEmpty()) {
            // increment the counter of node
            this.upNbNode();
            // take the state at the top of the stack
            State state = open.pop();
            // add the state to visited states
            closed.add(state);
            for (Action action : this.actions) {
                // for all action in the set of actions of the problem
                if (action.isApplicable(state)) {
                    // if the action is applicable
                    // apply the action to build a new state
                    State next = action.apply(state);
                    if (!closed.contains(next) && !open.contains(next)) {
                        // if the new state isn't already visited and isn't in the open
                        // add the state before apply the action at father of the
                        // new state
                        father.put(next, state);
                        // add the new state in the plan with the action apply
                        plan.put(next, action);
                        if (next.satisfies(this.goal.getVoiture())) {
                            // if the new state satisfies the goal
                            // use the function satisfies of state because
                            // satisfies a premisse is the same as satisfies a car
                            return this.getBfsPlan(father, plan, next);
                        } else {
                            // add the new state to the open
                            open.add(next);
                        }
                    }
                }
            }
        }
        // no solution found
        return null;
    }

    /**
     * BFS iterative method
     * @return the plan of action to execute to go to the target state
     */
    public Queue<Action> bfs() {
        // initialize the counter of node
        this.initNbNode();
        // map of (son, father)
        Map<State, State> father = new HashMap<>();
        // map of the plan state and action to go to this state
        Map<State, Action> plan = new HashMap<>();
        // set of visited state
        Set<State> closed = new HashSet<>();
        // Queue of state to visit
        Queue<State> open = new LinkedList<>();
        // add the initial state to the open
        open.add(this.initialState);
        // the initial state hasn't a father
        father.put(this.initialState, null);
        while (!open.isEmpty()) {
            // increment the counter of node
            this.upNbNode();
            // dequeue of the open
            State state = open.remove();
            // add the state to visited state
            closed.add(state);
            for (Action action : this.actions) {
                // for all action in the set of actions of the problem
                if (action.isApplicable(state)) {
                    // if the action is applicable
                    // apply the action to build a new state
                    State next = action.apply(state);
                    if (!closed.contains(next) && !open.contains(next)) {
                        // if the new state isn't already visited and isn't in the open
                        // add the state before apply the action at father of the
                        // new state
                        father.put(next, state);
                        // add the new state in the plan with the action apply
                        plan.put(next, action);
                        if (next.satisfies(this.goal.getVoiture())) {
                            // if the new state satisfies the goal
                            // use the function satisfies of state because
                            // satisfies a premisse is the same as satisfies a car
                            return this.getBfsPlan(father, plan, next);
                        } else {
                            // add the new state to the open
                            open.add(next);
                        }
                    }
                }
            }
        }
        // no solution found
        return null;
    }

    /**
     * Build the plan of action to go to the initialState to the goal
     * @param father map of (son, father)
     * @param actions the plan of actions
     * @param goal the target state
     * @return the plan of actions to go to the target state
     */
    public Queue<Action> getBfsPlan(Map<State, State> father,
            Map<State, Action> actions, State goal) {

        // initialize the plan
        Queue<Action> plan = new LinkedList<>();
        // get the action to go to the goal
        Action action = actions.get(goal);
        do {
            // add the action to the plan
            plan.add(action);
            // goal will be the father of the goal
            goal = father.get(goal);
            // action is the action to go to the goal
            action = actions.get(goal);
        } while (goal != null && action != null); // if there is goal && action
        // reverse the plan
        Collections.reverse((LinkedList<Action>) plan);
        return plan;
    }
}
