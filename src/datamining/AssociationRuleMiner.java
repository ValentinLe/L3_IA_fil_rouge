
package datamining;

import java.util.*;
import representations.*;

public class AssociationRuleMiner {

    private Map<Set<Item>, Integer> itemsets;

    public AssociationRuleMiner(Map<Set<Item>, Integer> itemsets) {
        this.itemsets = itemsets;
    }

    public Set<Rule> getAssociationsRules(int minfr, double minconf) {
        Set<Rule> rules = new HashSet<>();
        Set<Set<Item>> closed = this.getClosed();
        for(Set<Item> motif : closed) {
            Set<Rule> rulesGenerated = this.generateRules(motif);
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
        double v1 = this.frequence(rule);
        double v2 = this.frequence(this.createItems(rule));
        return v1 / v2;
    }
    
    public Set<Item> createItems(Rule rule) {
        Set<Item> items = new HashSet<>();
        Map<Variable, String> premisse = rule.getPremisse();
        for (Variable var : premisse.keySet()) {
            items.add(new Item(var, premisse.get(var)));
        }
        return items;
    }

    public int frequence(Rule rule) {
        Set<Item> items = new HashSet<>();
        Map<Variable, String> scope = new HashMap<>(rule.getPremisse());
        scope.putAll(rule.getConclusion());
        for (Variable var : scope.keySet()) {
            items.add(new Item(var, scope.get(var)));
        }
        return this.frequence(items);
    }

    public int frequence(Set<Item> item) {
        return this.itemsets.get(item);
    }

    public Set<Set<Item>> getClosed() {
        Set<Set<Item>> closed = new HashSet<>(this.itemsets.keySet());
        for (Set<Item> motif1 : this.itemsets.keySet()) {
            for (Set<Item> motif2 : this.itemsets.keySet()) {
                if (motif1 != motif2 && this.isInclude(motif1, motif2) && this.haveSameFrequence(motif1, motif2)) {
                    closed.remove(motif1);
                }
            }
        }
        return closed;
    }

    public boolean isInclude(Set<Item> motif1, Set<Item> motif2) {
        if (motif1.size() <= motif2.size()) {
            for (Item item : motif1) {
                if (!motif2.contains(item)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean haveSameFrequence(Set<Item> motif1, Set<Item> motif2) {
        return this.frequence(motif1) == this.frequence(motif2);
    }

    /*
        https://www.geeksforgeeks.org/finding-all-subsets-of-a-given-set-in-java/
    */
    public Set<Set<Item>> generateSubSets(Set<Item> motif) {
        Set<Set<Item>> subSets = new HashSet<>();
        Set<Item> subSetMotif;
        int n = motif.size();
        Iterator<Item> iter;
        Item item;
        for (int i = 1; i<(1<<n); i++) {
            subSetMotif = new HashSet<>();
            iter = motif.iterator();
            for (int j = 0; j<n; j++) {
                item = iter.next();
                if ((i & (1 << j)) > 0) {
                    subSetMotif.add(item);
                }
            }
            subSets.add(subSetMotif);
        }
        return subSets;
    }

    public Set<Item> itemPrivateOfItem(Set<Item> motif1, Set<Item> motif2) {
        for (Item item : motif2) {
            if (motif1.contains(item)) {
                motif1.remove(item);
            }
        }
        return motif1;
    }
    
    public Map<Variable, String> buildMap(Set<Item> motif) {
        Map<Variable, String> part = new HashMap<>();
        for (Item item : motif) {
            part.put(item.getVariable(), item.getValue());
        }
        return part;
    }
    
    public Rule buildRule(Set<Item> motifPremisse, Set<Item> motifConclusion) {
        Map<Variable, String> premisse = this.buildMap(motifPremisse);
        Map<Variable, String> conclusion = this.buildMap(motifConclusion);
        return new Rule(premisse, conclusion);
    }

    public Set<Rule> generateRules(Set<Item> motif) {
        Set<Rule> rules = new HashSet<>();
        Set<Set<Item>> subSetsOfItem = this.generateSubSets(motif);
        Set<Item> complementMotif;
        for (Set<Item> subMotif : subSetsOfItem) {
            if (subMotif.size() < motif.size()) {
                complementMotif = this.itemPrivateOfItem(new HashSet<>(motif), subMotif);
                Rule rule = this.buildRule(subMotif, complementMotif);
                rules.add(rule);
            }
        }
        return rules;
    }
}
