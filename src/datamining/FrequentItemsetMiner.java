
package datamining;

import java.util.*;
import representations.*;

public class FrequentItemsetMiner {
    
    private BooleanDatabase database;
    
    public FrequentItemsetMiner(BooleanDatabase database) {
        this.database = database;
    }
    
    public Map<Set<Variable>, Integer> frequentItemsets(int minfr) {
        Map<Set<Variable>, Integer> mapFrequent = new HashMap<>();
        List<Variable> listVariables = this.database.getListVariables();
        List<Map<Variable, String>> listTransactions = this.database.getListTransactions();
        Set<Set<Variable>> items = null;
        int k = 0;
        while (k < listVariables.size() + 1) {
            if (k==0) {
                items = this.getSingletons(listVariables);
            } else if (k==1) {
                items = combinaisons(items, k + 1);
            } else {
                items = combinaisons2(items, k + 1);
            }
            for (Set<Variable> item : new HashSet<>(items)) {
                int frequence = this.frequence(listTransactions, item);
                if (frequence >= minfr) {
                    mapFrequent.put(item, frequence);
                } else {
                    items.remove(item);
                }
            }
            k ++;
        }
        return mapFrequent;
    }
    
    public Set<Set<Variable>> getSingletons(List<Variable> listVariables) {
        Set<Set<Variable>> singletons = new HashSet<>();
        Set<Variable> item;
        for (Variable var : listVariables) {
            item = new HashSet<>();
            item.add(var);
            singletons.add(item);
        }
        return singletons;
    }
    
    
    public Set<Set<Variable>> combinaisons(Set<Set<Variable>> setVar, int sizeCondition) {
        Set<Set<Variable>> comb = new HashSet<>();
        Set<Variable> itemTemp;
        for (Set<Variable> item : setVar) {
            for (Set<Variable> item2 : setVar) {
                if (item != item2) {
                    itemTemp = new HashSet<>();
                    itemTemp.addAll(item);
                    itemTemp.addAll(item2);
                    if (itemTemp.size() == sizeCondition) {
                        comb.add(itemTemp);
                    }
                }
            }
        }
        return comb;
    }
    
    public Set<Set<Variable>> combinaisons2(Set<Set<Variable>> setVar, int sizeCondition) {
        Set<Set<Variable>> comb = new HashSet<>();
        Set<Variable> itemTemp;
        for (Set<Variable> item : setVar) {
            for (Set<Variable> item2 : setVar) {
                if (item != item2 && this.hasSameFirstElment(item, item2)) {
                    itemTemp = new HashSet<>();
                    itemTemp.addAll(item);
                    itemTemp.addAll(item2);
                    if (itemTemp.size() == sizeCondition) {
                        comb.add(itemTemp);
                    }
                }
            }
        }
        return comb;
    }
    
    public boolean hasSameFirstElment(Set<Variable> item1, Set<Variable> item2) {
        return this.getFirstElement(item1).equals(this.getFirstElement(item2));
    }
    
    public Variable getFirstElement(Set<Variable> item) {
        Iterator<Variable> iter = item.iterator();
        return iter.next();
    }
    
    public int frequence(List<Map<Variable, String>> listTransaction, Set<Variable> item) {
        int cpt = 0;
        for (Map<Variable, String> transactions : listTransaction) {
            boolean test = true;
            for (Variable var : item) {
                if (transactions.get(var)=="0") {
                    test = false;
                }
            }
            if (test) {
                cpt += 1;
            }
        }
        return cpt;
    }
}
