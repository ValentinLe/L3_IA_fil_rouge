
package representations;

import java.util.*;

/**
 * This class represents all the values with different constraints
 *
 */
public class AllDifferentConstraint extends AllCompareConstraint {

    /**
     * Builds an instance of AllDifferentConstraint
     * @param variables vairables of constraint
     */
    public AllDifferentConstraint(Set<Variable> variables){
        super(variables);
    }

    /**
     * Test if all the variables are different in a car
     * @param voiture the car
     * @return test result
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, String> voiture) {
        if (voiture.isEmpty() || voiture.size()==1) {
            // si la voiture est vide ou qu'il y a qu'une variable elle satisfait
            // la contrainte
            return true;
        } else {
            // liste des valeurs qu'aura chaque variable
            ArrayList<String> values = new ArrayList<>();
            String currentValue = null;
            for (Variable var : this.variables) {
                // pour toutes les variables
                // on recupere la valeur de la variable dans la voiture
                currentValue = voiture.get(var);
                if (currentValue != null) {
                    // si la variable est presente dans la voiture
                    if (values.contains(currentValue)) {
                        // si la valeur de la variable dans la voiture est affectee
                        // a une autre variable, elle est donc present dans la liste
                        // alors la contrainte n'est pas respectee
                        return false;
                    }
                    // on ajoute la valeur de la variable qui n'est pas deja dans
                    // la liste
                    values.add(currentValue);
                }
            }
            return true;
        }
    }

    /**
     * Get the string separator of this constraint
     * @return the string's separator
     */
    @Override
    public String getSeparator() {
        return " != ";
    }

    /**
     * Filters the domain's variables
     * @param voiture a car for the filtering test
     * @param domaines variables and its copied domain for filtering
     * @return true if filtering occures
     */
    @Override
    public boolean filtrer(Map<Variable, String> voiture, Map<Variable, Set<String>> domaines) {
        boolean isFilter = false;
        Set<String> values = new HashSet<>();
        // recuperation de toutes les valeurs des variables presentes dans la
        // voiture
        for (Variable var : this.variables) {
            // pour toutes les variables
            if (voiture.get(var) != null) {
                // si la variable est presente dans la voiture on l'ajoute
                values.add(voiture.get(var));
            }
        }
        if (!values.isEmpty()) {
            // si la liste des valeurs n'est pas vide
            for (Variable var : this.variables) {
                // pour toutes les variables
                if (domaines.containsKey(var)) {
                    // si la variable est dans le domaine
                    // on copie le domaine
                    Set<String> copyDom = new HashSet<>(domaines.get(var));
                    for (String value : domaines.get(var)) {
                        // pour toutes les valeurs du domaine
                        if (values.contains(value)) {
                            // si la valeur est deja affecte a une autre variable
                            // on la retire du domaine donc on filtre
                            copyDom.remove(value);
                            isFilter = true;
                        }
                    }
                    // on ajoute la variable avec le domaine filtrer ou non
                    domaines.put(var, copyDom);
                }

            }
        }
        return isFilter;
    }
}
