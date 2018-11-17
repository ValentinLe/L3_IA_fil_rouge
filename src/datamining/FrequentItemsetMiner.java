
package datamining;

import java.util.*;

/**
 * This class find the frequent items of a boolean database
 *
 */
public class FrequentItemsetMiner {

    public BooleanDatabase database;

    /**
     * Build an instance of FrequentItemsetMiner
     * @param database a boolean database
     */
    public FrequentItemsetMiner(BooleanDatabase database) {
        this.database = database;
    }

    /**
     * Build a map of the motifs and their frequence
     * @param minfr the minimum of the frequence you want
     * @return the map of frequence of motifs builded
     */
    public Map<Set<Item>, Integer> frequentItemsets(int minfr) {
        Map<Set<Item>, Integer> mapFrequent = new HashMap<>();
        List<Item> listItems = this.database.getListItems();
        List<Map<Item, String>> listTransactions = this.database.getListTransactions();
        Set<Set<Item>> motifs = null;
        // k is the size of motifs builded
        int k = 1;
        while (motifs==null || !motifs.isEmpty()) {
            // while there are no more combination possible
            if (k==1) {
                // build motifs with the size 1
                motifs = this.getSingletons(listItems);
            } else {
                // make a combination between all motifs of size k
                motifs = combinaisons(motifs);
            }
            for (Set<Item> motif : new HashSet<>(motifs)) {
                // for all motifs of combination builded
                int frequence = this.frequence(listTransactions, motif);
                if (frequence >= minfr) {
                    // if the motif has a frequence more important than the minimum
                    // of frequence
                    mapFrequent.put(motif, frequence);
                } else {
                    // remove the motif to the combination
                    motifs.remove(motif);
                }
            }
            k ++;
        }
        return mapFrequent;
    }

    /**
     * Build a set of motifs with the size 1 of a list of item
     * @param listItem the list of item
     * @return the set of motifs with size 1
     */
    public Set<Set<Item>> getSingletons(List<Item> listItem) {
        Set<Set<Item>> singletons = new HashSet<>();
        Set<Item> motif;
        for (Item item : listItem) {
            // for all item of the list of items
            // add a motif with size 1 in the set
            motif = new HashSet<>();
            motif.add(item);
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
        Item itemTest = null;
        for (Map<Item, String> transaction : listTransactions) {
            boolean itemInTransaction = true;
            for (Item item : motif) {
                if (transaction.get(item).equals("0")) {
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
