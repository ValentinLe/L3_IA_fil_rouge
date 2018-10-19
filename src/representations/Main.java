
package representations;

import java.util.*;
import examples.*;
import ppc.*;
import planning.*;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Examples ex = new Examples();


        // voiture
        /*
        Map<Variable,String> voiture = ex.getVoiture1();
        System.out.println(voiture);

        AllEqualConstraint allE1 = ex.getExemple1();
        System.out.println("Voiture : " + voiture);
        System.out.println(allE1 + " --> " + allE1.isSatisfiedBy(voiture) + "\n");

        //--------------------------------------------------------------//

        Map<Variable,String> voiture2 = ex.getVoiture2();

        ConstraintOr dgt = ex.getExemple2();
        System.out.println("Voiture : " + voiture2);
        System.out.println(dgt + " --> " + dgt.isSatisfiedBy(voiture2) + "\n");

        //--------------------------------------------------------------//

        Map<Variable,String> voiture3 = ex.getVoiture3();

        IncompatibilityConstraint cNonEqu = ex.getExemple3();
        System.out.println("Voiture : " + voiture3);
        System.out.println(cNonEqu + " --> " + cNonEqu.isSatisfiedBy(voiture3) + "\n");

        //--------------------------------------------------------------//

        Map<Variable,String> voiture4 = ex.getVoiture4();

        IncompatibilityConstraint nEqu = ex.getExemple4();
        System.out.println("Voiture : " + voiture4);
        System.out.println(nEqu + " --> " + nEqu.isSatisfiedBy(voiture4) + "\n");

        */
        /*
        Set<Variable> variables = ex.getVariables();

        AllEqualConstraint c1 = ex.getExemple1();
        ConstraintOr c2 = ex.getExemple2();
        IncompatibilityConstraint c3 = ex.getExemple3();
        IncompatibilityConstraint c4 = ex.getExemple4();
        AllEqualConstraint c5 = ex.getExemple5();
        AllDifferentConstraint c6 = ex.getExemple6();
        Disjunction c7 = ex.getExemple7();

        ArrayList<Constraint> constraints = new ArrayList<>();
        constraints.add(c1);
        constraints.add(c2);
        constraints.add(c3);
        constraints.add(c4);
        //constraints.add(c5);
        //constraints.add(c6);
        //constraints.add(c7);

        Backtracking.Heuristic heuristic = Backtracking.Heuristic.CONSTRAINT_MAX;
        Backtracking back = new Backtracking(variables, constraints, heuristic);
        System.out.println(back.getStringConstraints());

        Map<Variable, String> voiture1 = back.solutionFilter();
        System.out.println("backtrack : " + voiture1);

        Set<Map<Variable, String>> sols = back.solutionsFilter();


        for (Map<Variable, String> voiture : sols) {
            System.out.println(voiture + "\n");
        }
        System.out.println("\n/\\ SOLUTIONS TROUVEES /\\\n");
        System.out.println("Nombre de solutions : " + sols.size());
        System.out.println("Heuristic utilisée : " + back.getHeuristic());
        System.out.println("noeuds parcourus : " + back.getNbNode());
        */

        /*
        Map<Variable, String> voiture = ex.getVoiture1();
        Map<Variable, Set<String>> map = back.getMapVariableNotAssigned(voiture);
        System.out.println("VOITURE : " + voiture);
        System.out.println("MAP AV " + map);
        System.out.println(c3.filtrer(voiture, map));
        System.out.println("MAP ap1 " + map);
        System.out.println(c3.filtrer(voiture, map));
        System.out.println("MAP ap2 " +map);
        */
        
        
        AssemblyLine assembly = new AssemblyLine();

        Set<Action> actions = assembly.getActions();
        PlanningProblem pb = new PlanningProblem(assembly.getInitState(), assembly.getGoal(), actions);
        //System.out.println(actions);
        //System.out.println(pb.bfs());
        Stack<Action> que =  pb.dfs();
        Collections.reverse(que);
        System.out.println(que);
        State initialState = assembly.getInitState();
        Action action = null;
        while (!que.isEmpty()) {
            action = que.pop();
            initialState = action.apply(initialState);
        }
        System.out.println(initialState);
        
        //System.out.println(pb.dfsIter());
        /*
        State initialState = assembly.getInitState();
        State goal = assembly.getGoal();
        PlanningProblemWithCost pb2 = new PlanningProblemWithCost(initialState, goal, actions, new SimpleHeuristic(initialState, goal));
        
        System.out.println(pb2.dijkstra());
        System.out.println("Nombre de noeuds parcourus : " + pb2.getNbNode());
        
        System.out.println(pb2.aStar());
        System.out.println("Nombre de noeuds parcourus : " + pb2.getNbNode());
        
        pb2 = new PlanningProblemWithCost(initialState, goal, actions, new InformedHeuristic(initialState, goal));
        
        System.out.println(pb2.aStar());
        System.out.println("Nombre de noeuds parcourus : " + pb2.getNbNode());*/
        /*
        Heuristic heuristic = new SimpleHeuristic(initialState, goal);
        Heuristic heuristicInf = new InformedHeuristic(initialState, goal);
        
        System.out.println(initialState);
        System.out.println(goal);
        System.out.println(heuristic.heuristicValue(initialState, goal));
        System.out.println(heuristicInf.heuristicValue(initialState, goal));*/
       
    }

}
