
package planning;

import java.util.*;

public class PlanningProblemWithCost extends PlanningProblem {

    public PlanningProblemWithCost(State initialState, State goal, Set<Action> actions) {
        super(initialState, goal, actions);
    }

    public int simpleHeuristic(State state) {
        int cpt = 0;
        for (Variable var : state.keySet()) {
            if (!state.get(var).equals(this.goal.get(var))) {
                cpt += 1;
            }
        }
        return cpt;
    }

    public int InformedHeuristic() {
        return 0;
    }

    public dijkstra() {
        Map<State, Integer> distance = new HashMap<>();
        Map<State, State> father = new HashMap<>();
        Map<State, Action> plan = new HashMap<>();
        Set<State> open = new HashSet<>();
        Set<State> goals = new HashSet<>();
        open.add(this.initialState);
        father.put(this.initialState, null);
        while (!open.isEmpty()) {
            State state =
            open.remove(state);
            if (state.satisfies(this.goal.getVoiture())) {
                goals.add(state);
            }
            for (Action action : this.actions) {
                if (action.isApplicable(state)) {
                    State next = action.apply(state);
                    if (!distance.keySet().constraints(next)) {
                        distance.put(next, Integer.MAX_VALUE);
                    }
                    if (distance.get(next) > distance.get(state) + action.getCost()) {
                        distance.put(next,distance.get(state) + action.getCost());
                        father.put(next, state);
                        plan.put(next, action);
                        open.add(next);
                    }
                }
            }
        }
        return this.getDijkstraPlan(father, plan, goals, distance);
    }

    public Stack<Action> getDijkstraPlan(Map<State, State> father, Map<State, Action> actions, Set<State> goals, Map<State, Integer> distance) {
        Stack<Action> plan = new Stack<>();
        State goal =
        while (goal != null) {
            plan.push(actions.get(goal));
            goal = father.get(goal);
        }
        Collections.reverse(plan);
        return plan;
    }


    
}
