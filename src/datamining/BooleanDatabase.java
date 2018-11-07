
package datamining;

import java.util.*;
import representations.*;

public class BooleanDatabase {
    
    private List<Variable> listVariables;
    private List<Map<Variable, String>> listTransactions;
    
    public BooleanDatabase(List<Variable> listVariables, List<Map<Variable, String>> listTransactions) {
        this.listVariables = listVariables;
        this.listTransactions = listTransactions;
    }
    
    public List<Variable> getListVariables() {
        return this.listVariables;
    }
    
    public List<Map<Variable, String>> getListTransactions() {
        return this.listTransactions;
    }
}
