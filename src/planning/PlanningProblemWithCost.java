
package planning;

import java.util.*;

public class PlanningProblemWithCost extends PlanningProblem {
    
    private Heuristic heuristic;
    
    public PlanningProblemWithCost(State initialState, State goal, Set<Action> actions, Heuristic heuristic) {
        super(initialState, goal, actions);
        this.heuristic = heuristic;
    }

    public Stack<Action> dijkstra() {
        this.initNbNode();
        PriorityQueue<State> priority = new PriorityQueue<>();
        Map<State, Integer> distance = new HashMap<>();
        Map<State, State> father = new HashMap<>();
        Map<State, Action> plan = new HashMap<>();
        Set<State> open = new HashSet<>();
        Set<State> goals = new HashSet<>();
        this.initialState.setDistance(0);
        priority.add(this.initialState);
        open.add(this.initialState);
        father.put(this.initialState, null);
        distance.put(this.initialState, 0);
        while (!open.isEmpty()) {
            State state = priority.remove();
            open.remove(state);
            if (state.satisfies(this.goal.getVoiture())) {
                goals.add(state);
            }
            for (Action action : this.actions) {
                if (action.isApplicable(state)) {
                    this.upNbNode();
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
                        priority.add(next);
                    }
                }
            }
        }
        return this.getDijkstraPlan(father, plan, goals, distance);
    }

    public State getMinimumState(Set<State> goals, Map<State, Integer> distance) {
        int minimum = Integer.MAX_VALUE;
        State stateMin = null;
        for (State state : goals) {
            if (state.getDistance() < minimum) {
                minimum = state.getDistance();
                stateMin = state;
            }
        }
        return stateMin;
    }
    
    public Stack<Action> getDijkstraPlan(Map<State, State> father, Map<State, Action> actions, Set<State> goals, Map<State, Integer> distance) {
        Stack<Action> plan = new Stack<>();
        State goal = this.getMinimumState(goals, distance);
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
        this.initNbNode();
        PriorityQueue<State> priority = new PriorityQueue<>();
        Map<State, Integer> distance = new HashMap<>();
        Map<State, Action> plan = new HashMap<>();
        Map<State, State> father = new HashMap<>();
        Map<State, Integer> value = new HashMap<>();
        Set<State> open = new HashSet<>();
        open.add(this.initialState);
        father.put(this.initialState, null);
        this.initialState.setDistance(0);
        distance.put(this.initialState, 0);
        priority.add(this.initialState);
        value.put(this.initialState, this.heuristic.heuristicValue(this.initialState, this.goal));
        while (!open.isEmpty()) {
            State state = priority.remove();
            if (state.satisfies(this.goal.getVoiture())) {
                return this.getBfsPlan(father, plan, state);
            } else {
                open.remove(state);
                for (Action action : this.actions) {
                    if (action.isApplicable(state)) {
                        this.upNbNode();
                        State next = action.apply(state);
                        if (!distance.keySet().contains(next)) {
                            distance.put(next, Integer.MAX_VALUE);
                        }
                        if (distance.get(next) > distance.get(state) + action.getCost()) {
                            next.setDistance(state.getDistance() + action.getCost());
                            distance.put(next, next.getDistance());
                            value.put(next, distance.get(next) + this.heuristic.heuristicValue(next, this.goal));
                            father.put(next, state);
                            plan.put(next, action);
                            open.add(next);
                            priority.add(next);
                        }
                    }
                }
            }
        }
        return null;
    }

}
