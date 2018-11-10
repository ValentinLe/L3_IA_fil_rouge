
package datamining;

import java.util.*;
import representations.*;

public class BooleanDatabase {

    private List<Item> listVariables;
    private List<Map<Item, String>> listTransactions;

    public BooleanDatabase(List<Item> listVariables, List<Map<Item, String>> listTransactions) {
        this.listVariables = listVariables;
        this.listTransactions = listTransactions;
    }

    public List<Item> getListVariables() {
        return this.listVariables;
    }

    public List<Map<Item, String>> getListTransactions() {
        return this.listTransactions;
    }
}
