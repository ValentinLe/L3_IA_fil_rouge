
package examples;

import java.util.*;
import planning.Action;
import representations.Variable;

public class AssemblyLine {
    
    private Map<Variable, String> voiture;
    private Map<String, Variable> mapVar;
    
    public AssemblyLine() {
       Set<String> varBool = new HashSet<>(Arrays.asList(
               "HAS_CHASSIS", "HAS_FRONT_LEFT_WHEEL", "HAS_FRONT_RIGHT_WHEEL",
               "HAS_REAR_LEFT_WHEEL", "HAS_REAR_RIGHT_WHEEL", "HAS_BODY"
       ));
       
       Set<String> booleans = new HashSet<>(Arrays.asList(
               "TRUE", "FALSE"
       ));
       
       Set<String> components = new HashSet<>(Arrays.asList(
               "FRONT_COLOR", "LEFT_COLOR", "REAR_COLOR", "RIGHT_COLOR", "ROOF_COLOR"
       ));
       
       Set<String> colors = new HashSet<>(Arrays.asList(
               "GRAY", "BLACK", "WHITE", "RED", "GREEN", "BLUE", "ORANGE", "YELLOW"
       ));
       
       Set<Variable> HAS_ELEMENT = new HashSet<>();
       
       this.mapVar = new HashMap<>();
       
       this.voiture = new HashMap<>();
       
       for (String varBoolean : varBool) {
           Variable var = new Variable(varBoolean, booleans);
           HAS_ELEMENT.add(new Variable(varBoolean, booleans));
           this.voiture.put(var,"FALSE");
           this.mapVar.put(varBoolean, var);
       }
       
       Set<Variable> X_Y_WHEEL_COLOR = new HashSet<>();
       
       for (String component : components) {
           Variable var = new Variable(component, colors);
           X_Y_WHEEL_COLOR.add(var);
           this.voiture.put(var,"BLACK");
           this.mapVar.put(component, var);
       }
       
        
    }
    
    public Action getAction1() {
        Map<Variable, String> premisse = new HashMap<>();
    }
}
