
package datamining;

import java.util.*;
import representations.*;

public class Database {

    private List<Variable> listVariables;
    private List<Map<Variable, String>> listTransactions;

    public Database(List<Variable> listVariables, List<Map<Variable, String>> listTransactions) {
        this.listVariables = listVariables;
        this.listTransactions = listTransactions;
    }
    
    public BooleanDatabase toBooleanDatabase() {
        Set<Item> motifs = new HashSet<>();
        List<Map<Item, String>> booleanTransactions = new ArrayList<>();
        Item itemTemp;
        Map<Item, String> transactionTemp;
        for (Map<Variable, String> transaction : this.listTransactions) {
            transactionTemp = new HashMap<>();
            for (Variable var : transaction.keySet()) {
                for (String value : var.getDomaine()) {
                    itemTemp = new Item(var, value);
                    if (transaction.get(var).equals(value)) {
                        motifs.add(itemTemp);
                        transactionTemp.put(itemTemp, "1");
                    } else {
                        transactionTemp.put(itemTemp, "0");
                    }
                }
            }
            booleanTransactions.add(transactionTemp);
        }
        return new BooleanDatabase(new ArrayList<>(motifs), booleanTransactions);
    }

    @Override
    public String toString() {
        String ch = "";
        for (Map<Variable, String> map : this.listTransactions) {
            ch += map;
        }
        return ch;
    }
}
