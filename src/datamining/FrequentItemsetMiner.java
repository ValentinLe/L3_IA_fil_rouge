
package datamining;

import java.util.*;
import representations.*;

public class FrequentItemsetMiner {
    
    private BooleanDatabase database;
    
    public FrequentItemsetMiner(BooleanDatabase database) {
        this.database = database;
    }
    
    public Map<Set<Variable>, Integer> frequentItemsets(int minfr) {
        List<Map<Variable, String>> listTransactions = this.database.getListTransactions();
        Set<Set<Variable>> items = this.database.getSubSet();
        
        return null;
    }
}
