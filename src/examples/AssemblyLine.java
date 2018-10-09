
package examples;

import java.util.*;
import planning.*;
import representations.*;

public class AssemblyLine {
    
    private Map<Variable, String> voiture;
    private Map<String, Variable> mapVar;
    private Set<String> domainColors;
    private Set<String> elementsBool;
    private Set<String> componentsColor;
    private Set<Variable> HAS_ELEMENT;
    private Set<Variable> X_Y_WHEEL_COLOR;
    
    public AssemblyLine() {
       this.elementsBool = new HashSet<>(Arrays.asList(
               "HAS_CHASSIS", "HAS_FRONT_LEFT_WHEEL", "HAS_FRONT_RIGHT_WHEEL",
               "HAS_REAR_LEFT_WHEEL", "HAS_REAR_RIGHT_WHEEL", "HAS_BODY"
       ));
       
       Set<String> domainBooleans = new HashSet<>(Arrays.asList(
               "TRUE", "FALSE"
       ));
       
       this.componentsColor = new HashSet<>(Arrays.asList(
               "FRONT_COLOR", "LEFT_COLOR", "REAR_COLOR", "RIGHT_COLOR", "ROOF_COLOR"
       ));
       
       this.domainColors = new HashSet<>(Arrays.asList(
               "GRAY", "BLACK", "WHITE", "RED", "GREEN", "BLUE", "ORANGE", "YELLOW"
       ));
       
       this.HAS_ELEMENT = new HashSet<>();
       
       this.mapVar = new HashMap<>();
       
       this.voiture = new HashMap<>();
       
       for (String varBoolean : this.elementsBool) {
           Variable var = new Variable(varBoolean, domainBooleans);
           this.HAS_ELEMENT.add(new Variable(varBoolean, domainBooleans));
           this.voiture.put(var,"FALSE");
           this.mapVar.put(varBoolean, var);
       }
       
       this.X_Y_WHEEL_COLOR = new HashSet<>();
       
       for (String component : this.componentsColor) {
           Variable var = new Variable(component, this.domainColors);
           this.X_Y_WHEEL_COLOR.add(var);
           this.voiture.put(var,"GRAY");
           this.mapVar.put(component, var);
       }
       
       voiture = generateCar();
        System.out.println(voiture);
    }
    
    public String choiceValue(Set<String> setValues) {
        Random r = new Random();
        int valueRandom = r.nextInt(setValues.size());
        int i = 0;
        for (String value : setValues) {
            if (i++ == valueRandom) {
                return value;
            }
        }
        return null;
    }
    
    public Map<Variable, String> generateCar() {
        Map<Variable, String> voiture = new HashMap<>(this.voiture);
        String colorSelect = choiceValue(this.domainColors);
        for (Variable var : this.HAS_ELEMENT) {
            voiture.put(var, "TRUE");
        }
        for (Variable var : this.X_Y_WHEEL_COLOR) {
            voiture.put(var, colorSelect);
        }
        return voiture;
    }
}
