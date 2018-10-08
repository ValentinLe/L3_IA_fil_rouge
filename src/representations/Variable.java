
package representations;

import java.util.Set;

/**
 * A variable is represented by a name and a domain
 * 
 */
public class Variable {
    
    private String name;
    private Set<String> domaine;

    /**
     * Construct a instance of Variable
     * @param name the variable's name
     * @param domaine all the values that the varible can be worth
     */
    public Variable(String name, Set<String> domaine) {
        this.name = name;
        this.domaine = domaine;
    }
    
    /**
     * Getter of the variable's name
     * @return the variable's name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Getter of the variable's domain
     * @return the variable's domain
     */
    public Set<String> getDomaine(){
        return this.domaine;
    }
    
    /**
     * The equals test
     * @param o the other object of the comparation
     * @return true if the variable and the object are the same name
     */
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
    
    /**
     * hash fonction of the variable object with the variable's name
     * @return the hashcode
     */
    @Override
    public int hashCode(){
      int result = 17;
      result = 31 * this.name.hashCode();
      return result;
    }
    
    /**
     * The reprensentation of a variable is its name
     * @return the string of the variable's name
     */
    @Override
    public String toString() {
        return this.name;
    }
}
