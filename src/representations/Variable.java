
package representations;

import java.util.Set;

public class Variable implements Comparable<Variable> {

    private String name;
    private Set<String> domaine;
    private Integer value;

    public Variable(String name, Set<String> domaine) {
        this.name = name;
        this.domaine = domaine;
        this.value = 0;
    }

    public String getName() {
        return this.name;
    }

    public Set<String> getScope(){
        return this.domaine;
    }
    
    public Set<String> getDomaine(){
        return this.domaine;
    }
    
    public Integer getValue() {
        return this.value;
    }
    
    public void setValue(Integer newValue) {
        this.value = newValue;
    }
    
    @Override
    public boolean equals(Object o) {
        if(this==o) {
          return true;
        } else {
            if(o instanceof Variable) {
                return this.name.equals(((Variable) o).getName());
            } else {
                return false;
            }
        }
    }
    
    @Override
    public int hashCode(){
      int result = 17;
      result = 31 * this.name.hashCode();
      return result;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Variable o) {
        return this.value.compareTo(o.getValue());
    }
}
