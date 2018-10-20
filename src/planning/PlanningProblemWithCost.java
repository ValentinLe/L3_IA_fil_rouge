
package planning;

import java.util.*;

/**
 * PlanningProblemWithCost contains the Dijkstra and aStar algortihms
 * actions of the problem are now a cost
 */
public class PlanningProblemWithCost extends PlanningProblem {

    private Heuristic heuristic;

    /**
     * 
     * @param initialState
     * @param goal
     * @param actions
     * @param heuristic 
     */
    public PlanningProblemWithCost(State initialState, State goal, Set<Action> actions, Heuristic heuristic) {
        super(initialState, goal, actions);
        this.heuristic = heuristic;
    }
    
    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public Stack<Action> dijkstra() {
        this.initNbNode();
        PriorityQueue<State> open = new PriorityQueue<>();
        Map<State, Integer> distance = new HashMap<>();
        Map<State, State> father = new HashMap<>();
        Map<State, Action> plan = new HashMap<>();
        PriorityQueue<State> goals = new PriorityQueue<>();
        this.initialState.setDistance(0);
        open.add(this.initialState);
        father.put(this.initialState, null);
        distance.put(this.initialState, 0);
        while (!open.isEmpty()) {
            this.upNbNode();
            State state = open.remove();
            if (state.satisfies(this.goal.getVoiture())) {
                goals.add(state);
            }
            for (Action action : this.actions) {
                if (action.isApplicable(state)) {
                    State next = action.apply(state);
                    if (!distance.keySet().contains(next)) {
                        distance.put(next, Integer.MAX_VALUE);
                    }
                    if (distance.get(next) > distance.get(state) + action.getCost()) {
                        next.setDistance(distance.get(state) + action.getCost());
                        distance.put(next, next.getDistance());
                        father.put(next, state);
                        plan.put(next, action);
                        open.add(next);
                    }
                }
            }
        }
        return this.getDijkstraPlan(father, plan, goals, distance);
    }
    
    public Stack<Action> getDijkstraPlan(Map<State, State> father, Map<State, Action> actions, PriorityQueue<State> goals, Map<State, Integer> distance) {
        Stack<Action> plan = new Stack<>();
        State goal = goals.remove();
        Action action = actions.get(goal);
        do {
            plan.push(action);
            goal = father.get(goal);
            action = actions.get(goal);
        } while (goal != null && action != null);
        Collections.reverse(plan);
        return plan;
    }
    
    public Queue<Action> aStar() {
        return this.weightedAStar(1);
    }

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
