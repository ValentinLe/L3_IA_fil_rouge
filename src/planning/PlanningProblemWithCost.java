
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
        PriorityQueue<State> distance = new PriorityQueue<>();
        Map<State, State> father = new HashMap<>();
        Map<State, Action> plan = new HashMap<>();
        Set<State> open = new HashSet<>();
        Set<State> goals = new HashSet<>();
        distance.add(this.initialState);
        open.add(this.initialState);
        father.put(this.initialState, null);
        this.initialState.setDistance(0);
        while (!open.isEmpty()) {
            State state = distance.remove();
            open.remove(state);
            if (state.satisfies(this.goal.getVoiture())) {
                goals.add(state);
            }
            for (Action action : this.actions) {
                if (action.isApplicable(state)) {
                    State next = action.apply(state);
                    System.out.println(next);
                    try {
                        Thread.sleep(1000);
                    } catch(Exception e) {
                        System.out.println("oulala pas bien");
                    }
                    if (!distance.contains(next)) {
                        distance.add(next);
                    }
                    if (next.getDistance() > state.getDistance() + action.getCost()) {
                        next.setDistance(state.getDistance() + action.getCost());
                        distance.add(next);
                        father.put(next, state);
                        plan.put(next, action);
                        open.add(next);
                    }
                }
            }
        }
        return this.getDijkstraPlan(father, plan, goals, distance);
    }

    public Stack<Action> getDijkstraPlan(Map<State, State> father, Map<State, Action> actions, Set<State> goals, PriorityQueue<State> distance) {
        Stack<Action> plan = new Stack<>();
        State goal = distance.remove();
        while (goal != null) {
            plan.push(actions.get(goal));
            goal = father.get(goal);
        }
        Collections.reverse(plan);
        return plan;
    }

    public Queue<Action> aStar() {
        PriorityQueue<State> distance = new PriorityQueue<>();
        Map<State, Action> plan = new HashMap<>();
        Map<State, State> father = new HashMap<>();
        Map<State, Integer> value = new HashMap<>();
        Set<State> open = new HashSet<>();
        open.add(this.initialState);
        father.put(this.initialState, null);
        this.initialState.setDistance(0);
        distance.add(this.initialState);
        value.put(this.initialState, this.simpleHeuristic(this.initialState));
        while (!open.isEmpty()) {
            State state = distance.remove();
            if (state.satisfies(this.goal.getVoiture())) {
                return this.getBfsPlan(father, plan, state);
            } else {
                open.remove(state);
                for (Action action : this.actions) {
                    if (action.isApplicable(state)) {
                        State next = action.apply(state);
                        if (!distance.contains(next)) {
                            distance.add(next);
                        }
                        if (next.getDistance() > state.getDistance() + action.getCost()) {
                            next.setDistance(state.getDistance() + action.getCost());
                            distance.add(next);
                            value.put(next, next.getDistance() + this.simpleHeuristic(next));
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
