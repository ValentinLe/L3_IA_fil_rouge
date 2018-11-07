
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
}
