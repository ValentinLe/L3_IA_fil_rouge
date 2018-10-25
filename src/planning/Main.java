
package planning;

import java.util.*;
import examples.AssemblyLine;

public class Main {
    public static void main(String[] args) {

        AssemblyLine assembly = new AssemblyLine();

        Set<Action> actions = assembly.getActions();

        //--------------------------Tests of dfs and bfs----------------------//

        PlanningProblem problem = new PlanningProblem(
                assembly.getInitState(),
                assembly.getGoal(),
                actions
        );

        //System.out.println(actions);
        //System.out.println(problem.dfs());
        //System.out.println(problem.dfsIter());
        //System.out.println(problem.bfs());
        //System.out.println("Nombre de noeuds parcourus : " + problem.getNbNode());


        //----------Test of algorithms dijkstra/aStar and heuristics-----------//


        PlanningProblemWithCost problemWithCost = new PlanningProblemWithCost(
                assembly.getInitState(),
                assembly.getGoal(),
                actions,
                new SimpleHeuristic()
        );

        //System.out.println(problemWithCost.dijkstra());
        //System.out.println("Nombre de noeuds parcourus : " + problemWithCost.getNbNode());

        System.out.println("\nSimpleHeuristic : \n");

        System.out.println(problemWithCost.aStar());
        System.out.println("Nombre de noeuds parcourus : " + problemWithCost.getNbNode());

        System.out.println(problemWithCost.weightedAStar(5));
        System.out.println("Nombre de noeuds parcourus : " + problemWithCost.getNbNode());

        problemWithCost.setHeuristic(new InformedHeuristic());

        System.out.println("\nInformedHeuristic : \n");

        System.out.println(problemWithCost.aStar());
        System.out.println("Nombre de noeuds parcourus : " + problemWithCost.getNbNode());

        System.out.println(problemWithCost.weightedAStar(5));
        System.out.println("Nombre de noeuds parcourus : " + problemWithCost.getNbNode());

    }
}
