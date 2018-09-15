
package representations;

import java.util.Iterator;
import java.util.Map;

public class Rule {

    private Map<Variable,String > premisse;
    private Map<Variable, String> conclusion;

    public Rule(Map<Variable, String> premisse, Map<Variable,String> conclusion) {
        this.premisse = premisse;
        this.conclusion = conclusion;
    }

    public boolean isSatisfiedBy(Map<Variable,String> contraintes) {

        boolean p = true;
        boolean c = false;

        for(Variable var : contraintes.keySet()) {
            if(this.premisse != null && contraintes.get(var).equals(this.premisse.get(var))){
                System.out.println("iiiiiiiiiiiiiiiiii");
                p = false;
            }
            if(this.conclusion != null && contraintes.get(var).equals(this.conclusion.get(var))){
                c = true;
            }
        }

        return !p || c;
    }

    public String getStringMap(Map<Variable, String> map, String separator) {
        String ch = "";
        if (map != null) {
           Iterator iter = map.keySet().iterator();
           while(iter.hasNext()){
               Variable var = (Variable) iter.next();
               ch += var.getName()+ " = " + map.get(var);
               if (iter.hasNext()) {
                   ch += " " + separator + " ";
               }
           }
        }
        return ch;
    }

    @Override
    public String toString() {
        String ch = "";

        ch += this.getStringMap(this.premisse, "&&");

        if (this.premisse != null && this.conclusion != null) {
            ch += " -> ";
        }

        ch += this.getStringMap(this.conclusion, "||");

        return ch;
    }

}
