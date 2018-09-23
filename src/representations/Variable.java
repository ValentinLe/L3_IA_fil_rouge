
package representations;

import java.util.Set;

/**
 * A variable is represented by a name and a domain
 * 
 */
public class Variable implements Comparable<Variable> {
    
    private String name;
    private Set<String> domaine;
    /** the value is the number of time the variable occurs */
    private Integer occurences;

    /**
     * Construct a instance of Variable
     * @param name the variable's name
     * @param domaine all the values that the varible can be worth
     */
    public Variable(String name, Set<String> domaine) {
        this.name = name;
        this.domaine = domaine;
        this.occurences = 0;
    }
    
    /**
     * Getter of the variable's name
     * @return the variable's name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Getter of the variable's scope
     * @return the variable's scope
     */
    public Set<String> getScope(){
        return this.domaine;
    }
    
    /**
     * Getter of the variable's domain
     * @return the variable's domain
     */
    public Set<String> getDomaine(){
        return this.domaine;
    }
    
    /**
     * Getter of the variable's occurences
     * @return the variable's occurences
     */
    public Integer getOccurences() {
        return this.occurences;
    }
    
    /**
     * Setter of the variable's occurences
     * @param newValue the new variable's occurences
     */
    public void setOccurences(Integer newValue) {
        this.occurences = newValue;
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

    /**
     * Compare two variable about their occurences
     * @param o the other variable
     * @return the result of the comparation of their occurences
     */
    @Override
    public int compareTo(Variable o) {
        return this.occurences.compareTo(o.getOccurences());
    }
}
