
package datamining;

import java.util.*;
import representations.*;

public class Main {
    public static void main(String[] args) {
        
        Set<String> dom = new HashSet<>();
        
        Variable a = new Variable("A", dom);
        Variable b = new Variable("B", dom);
        Variable c = new Variable("C", dom);
        Variable d = new Variable("D", dom);
        Variable e = new Variable("E", dom);
        
        List<Variable> listVar = new ArrayList<>();
        listVar.add(a);
        listVar.add(b);
        listVar.add(c);
        listVar.add(d);
        listVar.add(e);
        
        
        Map<Variable, String> t1 = new HashMap<>();
        t1.put(a, "1");
        t1.put(b, "1");
        t1.put(c, "1");
        t1.put(d, "1");
        t1.put(e, "1");
        
        Map<Variable, String> t2 = new HashMap<>();
        t2.put(a, "1");
        t2.put(b, "0");
        t2.put(c, "1");
        t2.put(d, "0");
        t2.put(e, "0");
        
        Map<Variable, String> t3 = new HashMap<>();
        t3.put(a, "1");
        t3.put(b, "1");
        t3.put(c, "1");
        t3.put(d, "1");
        t3.put(e, "0");
        
        Map<Variable, String> t4 = new HashMap<>();
        t4.put(a, "0");
        t4.put(b, "1");
        t4.put(c, "1");
        t4.put(d, "0");
        t4.put(e, "0");
        
        Map<Variable, String> t5 = new HashMap<>();
        t5.put(a, "1");
        t5.put(b, "1");
        t5.put(c, "1");
        t5.put(d, "0");
        t5.put(e, "0");
        
        Map<Variable, String> t6 = new HashMap<>();
        t6.put(a, "0");
        t6.put(b, "0");
        t6.put(c, "0");
        t6.put(d, "0");
        t6.put(e, "1");
        
        List<Map<Variable, String>> listTransactions = new ArrayList<>();
        listTransactions.add(t1);
        listTransactions.add(t2);
        listTransactions.add(t3);
        listTransactions.add(t4);
        listTransactions.add(t5);
        listTransactions.add(t6);
        
        BooleanDatabase bdb = new BooleanDatabase(listVar, listTransactions);
        FrequentItemsetMiner fim = new FrequentItemsetMiner(bdb);
        
        Set<Variable> item = new HashSet<>();
        item.add(a);
        item.add(c);
        
        int fr = fim.frequence(listTransactions, item);
        Map<Set<Variable>, Integer> res = fim.frequentItemsets(0);
        System.out.println("res : " + res);
        System.out.println("nb : " + res.size());
        AssociationRuleMiner arm = new AssociationRuleMiner(res);
        Set<Rule> rules = arm.getAssociationsRules(0, 0.1);
        System.out.println("associa : " + rules);
        System.out.println("size : " + rules.size());
        Set<Set<Variable>> closed = arm.getClosed();
        System.out.println("closed : " + closed);
        System.out.println("size " + closed.size());
        Set<Variable> abc = new HashSet<>();
        abc.add(a);
        abc.add(b);
        abc.add(c);
        arm.generateRules(abc, 0);
    }
}
