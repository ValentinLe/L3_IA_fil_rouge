
package diagnosis;

import java.util.*;
import representations.*;
import ppc.*;

public class Diagnoser {
  
  private Backtracking backtrack;
  private Map<Variable, Set<String>> csp;
  
  public Diagnoser(Backtracking backtrack) {
    this.backtrack = backtrack;
    this.csp = this.backtrack.transformToMap(this.backtrack.getVariables());
  }
  
  public boolean isExplication(Map<Variable, String> choices, Variable variable, String value) {
    Set<String> reduceDomain;
    for (Variable var : choices.keySet()) {
      reduceDomain = new HashSet<>();
      reduceDomain.add(choices.get(var));
      this.csp.put(var, reduceDomain);
    }
    reduceDomain = new HashSet<>();
    reduceDomain.add(value);
    this.csp.put(variable, reduceDomain);
    Map<Variable, String> solution = this.backtrack.backtrackingFilter(choices, this.csp);
    return solution == null;
  }
  
  public Variable choiceVariable(Map<Variable, String> map) {
    Iterator<Variable> iter = map.keySet().iterator();
    return iter.next();
  }
  
  public Map<Variable, String> findExplication(Map<Variable, String> choices, Variable variable, String value) {
    Map<Variable, String> map = new HashMap<>(choices);
    Map<Variable, String> mapTemp;
    Map<Variable, String> nextChoices = new HashMap<>(choices);
    Variable var;
    while (!map.isEmpty()) {
      mapTemp = new HashMap<>(map);
      var = this.choiceVariable(mapTemp);
      System.out.println("\nmap " + mapTemp);
      nextChoices.remove(var);
      System.out.println("next " + nextChoices);
      if (this.isExplication(nextChoices, variable, value)) {
        System.out.println("explication " + this.isExplication(nextChoices, variable, value));
        choices = new HashMap<>(nextChoices);
      }
      map.remove(var);
    }
    return nextChoices;
  }
}
