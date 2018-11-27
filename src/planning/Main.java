
package planning;

import java.util.*;
import examples.AssemblyLine;

public class Main {
    public static void main(String[] args) {

        AssemblyLine assembly = new AssemblyLine();

        Set<Action> actions = assembly.getActions();

        State goal = assembly.generateGoal();

        PlanningProblemWithCost problemWithCost = new PlanningProblemWithCost(
                assembly.getInitState(),
                goal,
                actions,
                new SimpleHeuristic()
        );

        System.out.println("\nLe temps de calcul est d'environ 20s pour dijkstra, A* et WA* avec SimpleHeuristic, A* et WA* avec InformedHeuristic");
        System.out.println("Une voiture a au départ un chassis, sinon elle est considéré comme rien");
        System.out.println("\nVoiture de départ : \n" + problemWithCost.getInitState());
        System.out.println("\nVoiture d'arrivée : \n" + problemWithCost.getGoal());
        System.out.println("\n--> DIJKSTRA \\/\n");
        System.out.println("actions : \n" + problemWithCost.dijkstra());
        System.out.println("Nombre de noeuds parcourus : " + problemWithCost.getNbNode());

        System.out.println("\n<> SimpleHeuristic :");

        System.out.println("\n--> A* \\/\n");
        System.out.println("actions : \n" + problemWithCost.aStar());
        System.out.println("Nombre de noeuds parcourus : " + problemWithCost.getNbNode());

        System.out.println("\n--> WA* \\/\n");
        System.out.println("actions : \n" + problemWithCost.weightedAStar(5));
        System.out.println("Nombre de noeuds parcourus : " + problemWithCost.getNbNode());

        problemWithCost.setHeuristic(new InformedHeuristic());

        System.out.println("\n<> InformedHeuristic :");

        System.out.println("\n--> A* \\/\n");
        System.out.println("actions : \n" + problemWithCost.aStar());
        System.out.println("Nombre de noeuds parcourus : " + problemWithCost.getNbNode());

        System.out.println("\n--> WA* \\/\n");
        System.out.println("actions : \n" + problemWithCost.weightedAStar(5));
        System.out.println("Nombre de noeuds parcourus : " + problemWithCost.getNbNode());

    }
}
