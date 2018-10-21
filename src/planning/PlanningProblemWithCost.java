
package planning;

import java.util.*;

/**
 * PlanningProblemWithCost contains the Dijkstra and aStar algortihms
 * actions of the problem are now a cost
 */
public class PlanningProblemWithCost extends PlanningProblem {

    private Heuristic heuristic;

    /**
     * Build an instance of PlanningProblemWithCost with a heuristic
     * @param initialState the initial state of the problem
     * @param goal the target state
     * @param actions set of possible actions
     * @param heuristic the heuristic on states
     */
    public PlanningProblemWithCost(State initialState, State goal, Set<Action> actions, Heuristic heuristic) {
        super(initialState, goal, actions);
        this.heuristic = heuristic;
    }
    
    /**
     * Setter of the heuristic
     * @param heuristic the new heuristic you want use
     */
    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * Dijkstra algoritm
     * @return the plan of actions
     */
    public Stack<Action> dijkstra() {
        // initialize the counter of node
        this.initNbNode();
        // priority queue of the open states
        PriorityQueue<State> open = new PriorityQueue<>();
        // the map of states' distance
        Map<State, Integer> distance = new HashMap<>();
        // map of fathers (son, father)
        Map<State, State> father = new HashMap<>();
        // the state and the action do to get this state
        Map<State, Action> plan = new HashMap<>();
        // list of priority of goals found
        PriorityQueue<State> goals = new PriorityQueue<>();
        // initialise the distance of the initial state to 0
        this.initialState.setDistance(0);
        // add the initial state on the open states
        open.add(this.initialState);
        // the initial state hasn't a father
        father.put(this.initialState, null);
        // the distance of the initial state is 0
        distance.put(this.initialState, 0);
        while (!open.isEmpty()) {
            // while there is a state in the list of open states
            // increment the counter of node
            this.upNbNode();
            // take the state with the minimal distance
            State state = open.remove();
            if (state.satisfies(this.goal.getVoiture())) {
                // if the state is a goal, we add it in the list of goals
                goals.add(state);
            }
            for (Action action : this.actions) {
                // for all actions possibles of the problem
                if (action.isApplicable(state)) {
                    // if the action is applicable on the state
                    // apply this action to create a new state
                    State next = action.apply(state);
                    if (!distance.keySet().contains(next)) {
                        // if the new state hasn't a distance, we add it with the
                        // max value possible to its distance
                        distance.put(next, Integer.MAX_VALUE);
                    }
                    if (distance.get(next) > distance.get(state) + action.getCost()) {
                        // if the distance of the new state are more than the distance
                        // of the state and the cost of the action
                        
                        // set the distance of the new state to the distance of
                        // the state and the cost of the action
                        next.setDistance(distance.get(state) + action.getCost());
                        // add the distance of the new state to its new distance
                        distance.put(next, next.getDistance());
                        // add the state to father of the new state
                        father.put(next, state);
                        // add the new state with its action in the plan
                        plan.put(next, action);
                        // add the new state to the list of open states
                        open.add(next);
                    }
                }
            }
        }
        // return the plan build
        return this.getDijkstraPlan(father, plan, goals);
    }
    
    /**
     * Build the plan of the best path to go to the target state
     * @param father the map of fathers
     * @param actions the map of plan
     * @param goals the list of goals found
     * @return the plan of the best path
     */
    public Stack<Action> getDijkstraPlan(Map<State, State> father, Map<State, Action> actions, PriorityQueue<State> goals) {
        // initialize the plan
        Stack<Action> plan = new Stack<>();
        // get the target state with the minimal distance
        State goal = goals.remove();
        // initialize the action with the last action before the target state
        Action action = actions.get(goal);
        do {
            // add the action to the plan
            plan.push(action);
            // get the son of the state
            goal = father.get(goal);
            // get the action to go to this son
            action = actions.get(goal);
        } while (goal != null && action != null); // while there is a goal or an action
        // resverse the plan
        Collections.reverse(plan);
        return plan;
    }
    
    /**
     * aStar algorithm, A* it's the WA* with a weight=1
     * @return the best plan to go to the target state
     */
    public Queue<Action> aStar() {
        return this.weightedAStar(1);
    }

    /**
     * 
     * @param weight
     * @return 
     */
    public Queue<Action> weightedAStar(int weight) {
        this.initNbNode();
        PriorityQueue<State> open = new PriorityQueue<>();
        Map<State, Integer> distance = new HashMap<>();
        Map<State, Action> plan = new HashMap<>();
        Map<State, State> father = new HashMap<>();
        open.add(this.initialState);
        father.put(this.initialState, null);
        this.initialState.setDistance(0);
        distance.put(this.initialState, 0);
        while (!open.isEmpty()) {
            this.upNbNode();
            State state = open.remove();
            if (state.satisfies(this.goal.getVoiture())) {
                return this.getBfsPlan(father, plan, state);
            } else {
                for (Action action : this.actions) {
                    if (action.isApplicable(state)) {
                        State next = action.apply(state);
                        if (!distance.keySet().contains(next)) {
                            distance.put(next, Integer.MAX_VALUE);
                        }
                        if (distance.get(next) > distance.get(state) + action.getCost()) {
                            distance.put(next, distance.get(state) + action.getCost());
                            next.setDistance(distance.get(next) + weight*this.heuristic.heuristicValue(next, this.goal));
                            father.put(next, state);
                            plan.put(next, action);
                            open.add(next);
                        }
                    }
                }
            }
        }
        return null;
    }
}
