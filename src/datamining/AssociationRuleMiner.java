
package datamining;

import java.util.*;
import representations.*;

public class AssociationRuleMiner {
    
    private Map<Set<Variable>, Integer> itemsets;
    
    public AssociationRuleMiner(Map<Set<Variable>, Integer> itemsets) {
        this.itemsets = itemsets;
    }
    
    public Set<Rule> getAssociationsRules(int minfr, double minconf) {
        Set<Rule> rules = new HashSet<>();
        for(Set<Variable> item : this.itemsets.keySet()) {
            Set<Rule> rulesGenerated = this.generateRules(item, minfr);
            for (Rule rule : rulesGenerated) {
                if (this.isSupConf(rule, minconf)) {
                    rules.add(rule);
                }
            }
        }
        return rules;
    }
    
    public boolean isSupConf(Rule rule, double minconf) {
        return this.confiance(rule) >= minconf;
    }
    
    public double confiance(Rule rule) {
        return 0.5;
    }
    
    public Set<Rule> generateRules(Set<Variable> item, int minfr) {
        Set<Rule> rules = new HashSet<>();
        Iterator<Variable> iter = item.iterator();
        Map<Variable, String> premisse = new HashMap<>();
        premisse.put(iter.next(), "_");
        Map<Variable, String> conclusion = new HashMap<>();
        for (Variable var : item) {
            conclusion.put(var,"_ ");
        }
        rules.add(new Rule(premisse, conclusion));
        return rules;
    }
}
