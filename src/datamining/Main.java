
package datamining;

import java.io.*;
import java.util.*;
import representations.*;
import examples.Examples;

public class Main {
    public static void main(String[] args) {

        Examples ex = new Examples();
        DatabaseReader data = new DatabaseReader(ex.getVariables());
        Database database = null;
        try {
            database = data.importDB("datamining/example_db.csv");
        } catch(IOException excep) {
            excep.printStackTrace();
        }
        BooleanDatabase bdb = database.toBooleanDatabase();

        FrequentItemsetMiner frim = new FrequentItemsetMiner(bdb);

        Map<Set<Item>, Integer> itemsets = frim.frequentItemsets(3000);
        AssociationRuleMiner arm = new AssociationRuleMiner(itemsets);
        Set<Rule> rules = arm.getAssociationsRules(3000, 0.9);
        //System.out.println("rules " + rules);
        System.out.println(rules.size() + " rules");

        Map<Variable, String> premisse = new HashMap<>();
        premisse.put(ex.getVariableWithName("hayon"), "noir");

        Map<Variable, String> conclusion = new HashMap<>();
        conclusion.put(ex.getVariableWithName("toit"), "noir");
        Rule rule = new Rule(premisse, conclusion);
        /*
        System.out.println(rule);
        System.out.println("regle trouvee : " + rules.contains(rule));
        System.out.println("frequence = " + arm.frequence(rule));
        System.out.println("confiance = " + arm.confiance(rule));*/

        
        for (Rule rule1 : rules) {
            System.out.println("\n" + rule1);
        }

        /*
        System.out.println("rule " + rule);
        System.out.println("fr " + arm.frequence(rule));
        System.out.println("conf " + arm.confiance(rule));
        System.out.println("test " + rules.contains(rule));
        */
    }
}
