
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
                if (this.isConfiance(rule, minconf) && this.isFrequence(rule, minfr)) {
                    rules.add(rule);
                }
            }
        }
        return rules;
    }
    
    public boolean isConfiance(Rule rule, double minconf) {
        return this.confiance(rule) >= minconf;
    }
    
    public boolean isFrequence(Rule rule, int minfr) {
        return this.frequence(rule) >= minfr;
    }
    
    public double confiance(Rule rule) {
        return this.frequence(rule) / this.frequence(rule.getPremisse().keySet());
    }
    
    public int frequence(Rule rule) {
        return this.frequence(rule.getScope());
    }
    
    public int frequence(Set<Variable> item) {
        return this.itemsets.get(item);
    }
    
    public Set<Set<Variable>> getClosed() {
        Set<Set<Variable>> res = new HashSet<>(this.itemsets.keySet());
        for (Set<Variable> item1 : this.itemsets.keySet()) {
            for (Set<Variable> item2 : this.itemsets.keySet()) {
                if (item1 != item2 && this.isInclude(item1, item2) && this.haveSameFrequence(item1, item2)) {
                    res.remove(item1);
                }
            }
        }
        return res;
    }
    
    public boolean isInclude(Set<Variable> item1, Set<Variable> item2) {
        if (item1.size() <= item2.size()) {
            for (Variable var : item1) {
                if (!item2.contains(var)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    public boolean haveSameFrequence(Set<Variable> item1, Set<Variable> item2) {
        return this.itemsets.get(item1).equals(this.itemsets.get(item2));
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
