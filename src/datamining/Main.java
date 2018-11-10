
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
        
        Map<Set<Item>, Integer> itemsets = frim.frequentItemsets(1500);
        AssociationRuleMiner arm = new AssociationRuleMiner(itemsets);
        Set<Rule> rules = arm.getAssociationsRules(1500, 0.9);
        System.out.println("rules " + rules);
        System.out.println(rules.size() + " rules");
        
        Map<Variable, String> premisse = new HashMap<>();
        premisse.put(ex.getVariableWithName("hayon"), "blanc");
        premisse.put(ex.getVariableWithName("toit"), "blanc");
        
        Map<Variable, String> conclusion = new HashMap<>();
        conclusion.put(ex.getVariableWithName("capot"), "blanc");
        Rule rule = new Rule(premisse, conclusion);
        
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
