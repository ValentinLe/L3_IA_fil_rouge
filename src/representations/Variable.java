
package representations;

import java.util.Set;

public class Variable {

    private String name;
    private Set<String> domaine;

    public Variable(String name, Set<String> domaine) {
        this.name = name;
        this.domaine = domaine;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Variable var) {
        return this.name.equals(var.getName());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
