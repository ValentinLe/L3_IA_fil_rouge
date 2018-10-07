
package examples;

import java.util.*;
import representations.Variable;

public class AssemblyLine {
    
    public AssemblyLine() {
       Set<String> varBool = new HashSet<>(Arrays.asList(
               "HAS_CHASSIS", "HAS_FRONT_LEFT_WHEEL","HAS_FRONT_RIGHT_WHEEL",
               "HAS_REAR_LEFT_WHEEL","HAS_REAR_RIGHT_WHEEL","HAS_BODY"
       ));
       
       Set<String> booleans = new HashSet<>(Arrays.asList(
               "TRUE", "FALSE"
       ));
       
       Set<String> components = new HashSet<>(Arrays.asList(
               "FRONT_COLOR","LEFT_COLOR","REAR_COLOR","RIGHT_COLOR","ROOF_COLOR"
       ));
       
       Set<String> colors = new HashSet<>(Arrays.asList(
               "GRAY", "BLACK", "WHITE", "RED", "GREEN", "BLUE", "ORANGE", "YELLOW"
       ));
       
       Set<Variable> HAS_ELEMENT = new HashSet<>();
       
       for (String varBoolean : varBool) {
           HAS_ELEMENT.add(new Variable(varBoolean, booleans));
       }
       
       Set<Variable> X_Y_WHEEL_COLOR = new HashSet<>();
       
       for (String component : components) {
           X_Y_WHEEL_COLOR.add(new Variable(component, colors));
       }
    }
}
