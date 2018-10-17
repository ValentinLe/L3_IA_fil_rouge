
package planning;

import java.util.*;
import representations.*;

public class PlanningProblemWithCost extends PlanningProblem {

    public PlanningProblemWithCost(State initialState, State goal, Set<Action> actions) {
        super(initialState, goal, actions);
    }

    public int simpleHeuristic(State state) {
        int cpt = 0;
        Map<Variable, String> voitureState = state.getVoiture();
        Map<Variable, String> voitureGoal = this.initialState.getVoiture();
        for (Variable var : voitureState.keySet()) {
            if (!voitureState.get(var).equals(voitureGoal.get(var))) {
                cpt += 1;
            }
        }
        return cpt;
    }

    public int InformedHeuristic() {
        return 0;
    }

    public Stack<Action> dijkstra() {
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
                    priority.add(next);
                }
            }
        }
        System.out.println(this.initialState);
        System.out.println(this.goal);
        return this.getDijkstraPlan(father, plan, goals, priority);
    }

    public Stack<Action> getDijkstraPlan(Map<State, State> father, Map<State, Action> actions, Set<State> goals, PriorityQueue<State> distance) {
        Stack<Action> plan = new Stack<>();
        State state = null;
        while (!goals.contains(state)) {
            state = distance.remove();
        }
        State goal = distance.remove();
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
        value.put(this.initialState, this.simpleHeuristic(this.initialState));
        while (!open.isEmpty()) {
            State state = priority.remove();
            if (state.satisfies(this.goal.getVoiture())) {
                return this.getBfsPlan(father, plan, state);
            } else {
                open.remove(state);
                for (Action action : this.actions) {
                    if (action.isApplicable(state)) {
                        State next = action.apply(state);
                        if (!distance.keySet().contains(next)) {
                            distance.put(next, Integer.MAX_VALUE);
                        }
                        if (distance.get(next) > distance.get(state) + action.getCost()) {
                            next.setDistance(state.getDistance() + action.getCost());
                            distance.put(next, next.getDistance());
                            value.put(next, distance.get(next) + this.simpleHeuristic(next));
                            father.put(next, state);
                            plan.put(next, action);
                            open.add(next);
                        }
                        priority.add(next);
                    }
                }
            }
        }
        return null;
    }

}
