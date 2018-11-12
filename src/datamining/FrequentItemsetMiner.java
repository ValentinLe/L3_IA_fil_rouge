
package datamining;

import java.util.*;
import representations.*;

public class FrequentItemsetMiner {

    public BooleanDatabase database;

    public FrequentItemsetMiner(BooleanDatabase database) {
        this.database = database;
    }

    public Map<Set<Item>, Integer> frequentItemsets(int minfr) {
        Map<Set<Item>, Integer> mapFrequent = new HashMap<>();
        List<Item> listItems = this.database.getListVariables();
        List<Map<Item, String>> listTransactions = this.database.getListTransactions();
        Set<Set<Item>> motifs = null;
        int k = 0;
        while (k < listItems.size() + 1 && (motifs==null || !motifs.isEmpty())) {
            if (k==0) {
                motifs = this.getSingletons(listItems);
            } else {
                motifs = combinaisons(motifs);
            }
            for (Set<Item> motif : new HashSet<>(motifs)) {
                int frequence = this.frequence(listTransactions, motif);
                if (frequence >= minfr) {
                    mapFrequent.put(motif, frequence);
                } else {
                    motifs.remove(motif);
                }
            }
            k ++;
        }
        return mapFrequent;
    }

    public Set<Set<Item>> getSingletons(List<Item> listVariables) {
        Set<Set<Item>> singletons = new HashSet<>();
        Set<Item> motif;
        for (Item var : listVariables) {
            motif = new HashSet<>();
            motif.add(var);
            singletons.add(motif);
        }
        return singletons;
    }

    public Set<Set<Item>> combinaisons(Set<Set<Item>> setVar) {
        Set<Set<Item>> comb = new HashSet<>();
        Set<Item> motifTemp;
        for (Set<Item> motif1 : setVar) {
            for (Set<Item> motif2 : setVar) {
                if (motif1 != motif2 && this.hasSameKFirstElment(motif1, motif2)) {
                    motifTemp = new HashSet<>();
                    motifTemp.addAll(motif1);
                    motifTemp.addAll(motif2);
                    comb.add(motifTemp);
                }
            }
        }
        return comb;
    }

    public boolean hasSameKFirstElment(Set<Item> motif1, Set<Item> motif2) {
        Iterator<Item> iter1 = motif1.iterator();
        Iterator<Item> iter2 = motif2.iterator();
        int k = motif1.size() - 1;
        Item item1;
        Item item2;
        while (k > 0) {
            item1 = iter1.next();
            item2 = iter2.next();
            if (!item1.equals(item2)) {
                return false;
            }
            k--;
        }
        return true;
    }

    public int frequence(List<Map<Item, String>> listTransactions, Set<Item> motif) {
        int cpt = 0;
        for (Map<Item, String> transaction : listTransactions) {
            boolean itemInTransaction = true;
            for (Item item : motif) {
                if (transaction.get(item)=="0") {
                    itemInTransaction = false;
                    break;
                }
            }
            if (itemInTransaction) {
                cpt += 1;
            }
        }
        return cpt;
    }
}
