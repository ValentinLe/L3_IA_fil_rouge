
package examples;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import representations.Variable;

public class AssemblyLine {
    
    public AssemblyLine() {
       Set<String> varBool = new HashSet<>(Arrays.asList("HAS_CHASSIS", "HAS_FRONT_LEFT_WHEEL","HAS_FRONT_RIGHT_WHEEL","HAS_REAR_LEFT_WHEEL",
               "HAS_REAR_RIGHT_WHEEL","HAS_BODY"));
       
       Set<String> carColor = new HashSet<>(Arrays.asList("GRAY", "BLACK", "WHITE", "RED", "GREEN", "BLUE", "ORANGE", "YELLOW"));
    }
}
