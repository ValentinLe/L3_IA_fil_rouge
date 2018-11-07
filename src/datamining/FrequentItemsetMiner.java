
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
            } else {
                items = combinaisons(items);
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
    
    public Set<Set<Variable>> combinaisons(Set<Set<Variable>> setVar) {
        Set<Set<Variable>> comb = new HashSet<>();
        Set<Variable> itemTemp;
        for (Set<Variable> item : setVar) {
            for (Set<Variable> item2 : setVar) {
                if (item != item2 && this.hasSameKFirstElment(item, item2)) {
                    itemTemp = new HashSet<>();
                    itemTemp.addAll(item);
                    itemTemp.addAll(item2);
                    comb.add(itemTemp);
                }
            }
        }
        return comb;
    }
    
    public boolean hasSameKFirstElment(Set<Variable> item1, Set<Variable> item2) {
        Iterator<Variable> iter1 = item1.iterator();
        Iterator<Variable> iter2 = item2.iterator();
        int k = item1.size() - 1;
        Variable var1;
        Variable var2;
        while (k > 0) {
            var1 = iter1.next();
            var2 = iter2.next();
            if (!var1.equals(var2)) {
                return false;
            }
            k--;
        }
        return true;
    }
    
    public int frequence(List<Map<Variable, String>> listTransaction, Set<Variable> item) {
        int cpt = 0;
        for (Map<Variable, String> transactions : listTransaction) {
            boolean itemInTransaction = true;
            for (Variable var : item) {
                if (transactions.get(var)=="0") {
                    itemInTransaction = false;
                }
            }
            if (itemInTransaction) {
                cpt += 1;
            }
        }
        return cpt;
    }
}
