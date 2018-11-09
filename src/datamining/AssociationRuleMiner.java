
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
        Set<Set<Variable>> closed = new HashSet<>(this.itemsets.keySet());
        for (Set<Variable> item1 : this.itemsets.keySet()) {
            for (Set<Variable> item2 : this.itemsets.keySet()) {
                if (item1 != item2 && this.isInclude(item1, item2) && this.haveSameFrequence(item1, item2)) {
                    closed.remove(item1);
                }
            }
        }
        return closed;
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
        return this.frequence(item1) == this.frequence(item2);
    }
    
    public Set<Set<Variable>> generateSubSets(Set<Variable> item) {
        Set<Set<Variable>> subSet = new HashSet<>();
        Set<Variable> vars;
        int n = item.size();
        Iterator<Variable> iter;
        Variable var;
        for (int i = 1; i<(1<<n); i++) {
            vars = new HashSet<>();
            iter = item.iterator();
            for (int j = 0; j<n; j++) {
                var = iter.next();
                if ((i & (1 << j)) > 0) {
                    vars.add(var);
                }
            }
            subSet.add(vars);
        }
        return subSet;
    }
    
    public Set<Variable> itemPrivateOfItem(Set<Variable> item1, Set<Variable> item2) {
        for (Variable var : item2) {
            if (item1.contains(var)) {
                item1.remove(var);
            }
        }
        return item1;
    }
    
    public Set<Rule> generateRules(Set<Variable> item, int minfr) {
        Set<Rule> rules = new HashSet<>();
        Set<Set<Variable>> subSetsOfItem = this.generateSubSets(item);
        Set<Variable> complementItem;
        for (Set<Variable> itemize : subSetsOfItem) {
            if (itemize.size() < item.size()) {
                complementItem = this.itemPrivateOfItem(new HashSet<>(item), itemize);
                
                System.out.println("it " + itemize + " -> " + complementItem);
            }
        }
        return rules;
    }
}
