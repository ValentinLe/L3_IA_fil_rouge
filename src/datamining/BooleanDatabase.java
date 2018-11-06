
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
    
    public Set<Set<Variable>> getSubSet() {
        Set<Set<Variable>> subSet = new HashSet<>();
        int n = this.listVariables.size();
        Set<Variable> vars;
        for(int i = 0; i < (1<<n); i++) {
            vars = new HashSet<>();
            for (int j = 0; j<n; j++) {
                if ((i & (1 << j)) > 0) {
                    vars.add(this.listVariables.get(j));
                }
            }
            subSet.add(vars);
        }
        return subSet;
    }
}
