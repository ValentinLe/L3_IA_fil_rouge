
package ppc;

import java.util.List;
import representations.Constraint;

/**
 * Abstract class of heuristics
 * 
 */
public abstract class AbstractHeuristic implements Heuristic {
    
    protected List<Constraint> constraints;
    
    /**
     * Build a heuristic with a list of constraints
     * @param constraints the list of constraints
     */
    public AbstractHeuristic(List<Constraint> constraints) {
        this.constraints = constraints;
    }
    
    /**
     * Getter on the constraints
     * @return the constraints of the problem
     */
    @Override
    public List<Constraint> getConstraints() {
        return this.constraints;
    }
    
    /**
     * Setter on the constraints
     * @param constraints a list of constraints
     */
    @Override
    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }
}
