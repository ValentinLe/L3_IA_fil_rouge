
package datamining;

import representations.*;
import java.util.*;

public class Item implements Comparable<Item> {

    private Variable variable;
    private String value;

    public Item(Variable var, String value) {
        this.variable = var;
        this.value = value;
    }

    public Item(Variable var) {
        this(var, null);
    }

    public Variable getVariable() {
        return this.variable;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isEquals(Item other) {
        Variable var = other.getVariable();
        String value = other.getValue();
        return this.variable.equals(var) &&
                (this.value==null || this.value.equals(value));
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) {
            return true;
        } else {
            if (o instanceof Item) {
                return this.isEquals(((Item)o));
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        int val = this.variable.hashCode();
        if (this.value != null) {
            val *= this.value.hashCode();
        }
        return val;
    }

    @Override
    public int compareTo(Item other) {
        return this.variable.compareTo(other.getVariable());
    }

    @Override
    public String toString() {
        String ch = "" + this.variable;
        if (this.value != null) {
            ch += "=" + this.value;
        }
        return ch;
    }
}
