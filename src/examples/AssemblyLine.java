
package examples;

import java.util.*;
import planning.*;
import representations.*;

/**
 * Simulation of an assembly line
 *
 */
public class AssemblyLine {

    private Map<Variable, String> voiture;
    private Map<String, Variable> mapVar;
    private Set<String> domainBooleans;
    private Set<String> domainColors;
    private Set<String> elementsBool;
    private Set<String> componentsColor;
    private Set<Variable> HAS_ELEMENT;
    private Set<Variable> ELEMENT_COLOR;

    /**
     * Build all lists and maps will need
     */
    public AssemblyLine() {
        // set of all names of variables with a boolean domain
        this.elementsBool = new HashSet<>(Arrays.asList(
                "HAS_CHASSIS", "HAS_FRONT_LEFT_WHEEL", "HAS_FRONT_RIGHT_WHEEL",
                "HAS_REAR_LEFT_WHEEL", "HAS_REAR_RIGHT_WHEEL", "HAS_BODY"
        ));

        // boolean domain
        this.domainBooleans = new HashSet<>(Arrays.asList(
                "TRUE", "FALSE"
        ));

        // set of all names of variables with a colors domain
        this.componentsColor = new HashSet<>(Arrays.asList(
                "FRONT_COLOR", "LEFT_COLOR", "REAR_COLOR", "RIGHT_COLOR", "ROOF_COLOR",
                "FRONT_LEFT_WHEEL_COLOR", "FRONT_RIGHT_WHEEL_COLOR", "REAR_LEFT_WHEEL_COLOR",
                "REAR_RIGHT_WHEEL_COLOR", "BODY_COLOR"
        ));


        // colors domain
        this.domainColors = new HashSet<>(Arrays.asList(
                "GRAY", "BLACK", "WHITE", "RED", "BLUE", "GREEN", "ORANGE", "YELLOW"
        ));
        /*
        this.domainColors = new HashSet<>(Arrays.asList(
                "GRAY", "BLACK", "WHITE", "RED", "BLUE", "GREEN", "ORANGE", "YELLOW",
                 "BROWN", "CYAN", "MAGENTA", "PINK", "PURPLE", "KAKI"
        ));*/

        /*
        this.domainColors = new HashSet<>(Arrays.asList(
                "GRAY", "BLACK", "RED"
        ));*/

        // set of variable with boolean domain
        this.HAS_ELEMENT = new HashSet<>();

        // map to get a variable with this name
        this.mapVar = new HashMap<>();

        for (String varBoolean : this.elementsBool) {
            // for all names of boolean components
            // build a variable with the name and the boolean domain
            Variable var = new Variable(varBoolean, domainBooleans);
            // add the variable on the set of variable boolean
            this.HAS_ELEMENT.add(var);
            // put the names of variable and the variable on the map
            this.mapVar.put(varBoolean, var);
        }

        // set of variable with colors domain
        this.ELEMENT_COLOR = new HashSet<>();

        for (String component : this.componentsColor) {
            // for all names of components with colors domain
            // build a variable with the name and the colors domain
            Variable var = new Variable(component, this.domainColors);
            // add the variable to the set of variables colors domain
            this.ELEMENT_COLOR.add(var);
            // put the names of variable and the variable on the map
            this.mapVar.put(component, var);
        }

        // initial car
        this.voiture = this.initializeCar("GRAY");
    }

    /**
     * Builds a state with the car of this class
     * @return the state builded
     */
    public State getInitState() {
        return new State(this.voiture);
    }

    /**
     * Builds a random state with a car with one random color
     * @return the state builded
     */
    public State getGoal() {
        return new State(this.generateCar());
    }

    /**
     * Builds all actions possbile of the problem
     * @return a set of all actions
     */
    public Set<Action> getActions() {
        Set<Action> actions = new HashSet<>();
        for (String color : this.domainColors) {
            // for all color add all paints possible
            actions.add(this.paintRear(color));
            actions.add(this.paintFront(color));
            actions.add(this.paintLeft(color));
            actions.add(this.paintRight(color));
            actions.add(this.paintRoof(color));
        }

        for (String comp : this.elementsBool) {
            // for all component add all uniques installations possibles
            actions.add(this.installPiece(comp));
        }
        // add the action of place two wheels
        actions.add(this.installRightWheel());
        actions.add(this.installLeftWheel());
        actions.add(this.installFrontWheel());
        actions.add(this.installRearWheel());


        return actions;
    }

    /**
     * Choice a string value in a set and return it
     * @param setValues the set of values
     * @return the value choose
     */
    public String choiceValue(Set<String> setValues) {
        Random r = new Random();
        // un random value between 0 and the size of the set
        int valueRandom = r.nextInt(setValues.size());
        int i = 0;
        for (String value : setValues) {
            // for all value in the set
            if (i++ == valueRandom) {
                // if this value position (not the position in the set)
                // corresponding to the counter return the value
                return value;
            }
        }
        // never return null
        return null;
    }

    /**
     * Build a car with all color to colorInitial and all pieces to FALSE excepted
     * the chassis
     * @param colorInitial the initial color of all pieces
     * @return a initialized car
     */
    public Map<Variable, String> initializeCar(String colorInitial) {
        Map<Variable, String> voiture = new HashMap<>();
        for (String varBoolean : this.elementsBool) {
            // for all names of boolean components
            // add value of variable to FALSE in the car
            voiture.put(this.mapVar.get(varBoolean), "FALSE");
        }
        // a car has minimum a chassis else it's nothing
        voiture.put(this.mapVar.get("HAS_CHASSIS"), "TRUE");
        for (String component : this.componentsColor) {
            // for all names of components with colors domain
            // initialize in the car this component to GRAY
            voiture.put(this.mapVar.get(component), colorInitial);
        }
        return voiture;
    }

    /**
     * Create a random car
     * @return the builded car
     */
    public Map<Variable, String> generateCar() {
        // take a copy of the initial car to the beginning
        Map<Variable, String> voiture = new HashMap<>(this.voiture);
        // get a random color
        String colorSelect = choiceValue(this.domainColors);
        for (Variable var : this.HAS_ELEMENT) {
            // for all variable in the copy, add all boolean variables to TRUE
            voiture.put(var, "TRUE");
        }
        for (Variable var : this.ELEMENT_COLOR) {
            // for all variable in the copy, add all color variables to the color choose
            voiture.put(var, colorSelect);
        }
        return voiture;
    }

    /**
     * Build a part of rule (premisse or conclusion)
     * @param varStr the string names of variables
     * @param values the values of each variables or the value to assign to each
     * variables
     * @return the builded part of rule
     */
    public Map<Variable, String> buildMap(ArrayList<String> varStr, ArrayList<String> values) {
        Map<Variable, String> map = new HashMap<>();
        for (int i = 0; i<varStr.size(); i++) {
            map.put(
                    // the variable of the name
                    this.mapVar.get(varStr.get(i)),
                    // the modulo if there is one value gift else the value in
                    // the same position as the name of variable
                    values.get(Math.floorMod(i, values.size()))
            );
        }
        return map;
    }

    /**
     * Build the action install the piece if there is the chassis
     * @param piece the piece to install
     * @return the builded action
     */
    public Action installPiece(String piece) {
        Set<Rule> setRules = new HashSet<>();

        // the list of names of variables engaged in the premisse
        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_CHASSIS"
        ));
        // the value assigned to the variable
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the premisse
        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        // the list of names of variables engaged in the conclusion
        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                piece
        ));
        // the value assigned to the variable
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the conclusion
        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);

        // add the rule to the set of rules
        setRules.add(new Rule(premisse1,conclusion1));

        // the cost of the action to install one piece is 2
        return new Action(setRules, 2);
    }

    /**
     * Paint the the roof and the body if there is the body
     * @param color the color of the paint
     * @see AssemblyLine#buildMap
     * @return the builded action
     */
    public Action paintRoof(String color) {
        Set<Rule> setRules = new HashSet<>();

        // the list of names of variables engaged in the premisse
        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_BODY"
        ));

        // the value assigned to the variable
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the premisse
        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        // the list of names of variables engaged in the conclusion
        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                "ROOF_COLOR", "BODY_COLOR"
        ));

        // the value assigned to the variables, here, there is one value in the
        // list, it will be assign to all variables with the modulo
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList(color));

        // build the conclusion
        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);

        // add the rule to the set of rules
        setRules.add(new Rule(premisse1,conclusion1));

        // the cost of the action to paint two pieces is 1
        return new Action(setRules, 1);
    }

    /**
     * Paint the rear and the roof if there is the body
     * @param color the color of the paint
     * @see AssemblyLine#buildMap
     * @return the builded action
     */
    public Action paintRear(String color) {
        Set<Rule> setRules = new HashSet<>();

        // the list of names of variables engaged in the premisse
        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_BODY"
        ));
        // the value assigned to the variable
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the premisse
        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        // the list of names of variables engaged in the conclusion
        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                "REAR_COLOR","ROOF_COLOR"
        ));
        // the value assigned to the variables, here, there is one value in the
        // list, it will be assign to all variables with the modulo
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList(color));

        // build the conclusion
        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);

        // add the rule to the set of rules
        setRules.add(new Rule(premisse1,conclusion1));

        // the cost of the action to paint two pieces is 1
        return new Action(setRules, 1);
    }

    /**
     * Paint the roof and the front if there are the chassis and the body
     * @param color the color of the paint
     * @see AssemblyLine#buildMap
     * @return the builded action
     */
    public Action paintFront(String color) {
        Set<Rule> setRules = new HashSet<>();

        // the list of names of variables engaged in the premisse
        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_CHASSIS", "HAS_BODY"
        ));
        // the value assigned to the variables, here, there is one value in the
        // list, it will be assign to all variables with the modulo
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the premisse
        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        // the list of names of variables engaged in the conclusion
        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                "ROOF_COLOR", "FRONT_COLOR"
        ));
        // the value assigned to the variables, here, there is one value in the
        // list, it will be assign to all variables with the modulo
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList(color));

        // build the conclusion
        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);

        // add the rule to the set of rules
        setRules.add(new Rule(premisse1,conclusion1));

        // the cost of the action to paint two pieces is 1
        return new Action(setRules, 1);
    }

    /**
     * Paint the roof, front and rear left wheel and the left if there are the
     * chassis, body, front and rear left wheel
     * @param color the color of the paint
     * @see AssemblyLine#buildMap
     * @return the builded action
     */
    public Action paintLeft(String color) {
        Set<Rule> setRules = new HashSet<>();

        // the list of names of variables engaged in the premisse
        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_CHASSIS", "HAS_BODY", "HAS_FRONT_LEFT_WHEEL", "HAS_REAR_LEFT_WHEEL"
        ));
        // the value assigned to the variables, here, there is one value in the
        // list, it will be assign to all variables with the modulo
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the premisse
        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        // the list of names of variables engaged in the conclusion
        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                "ROOF_COLOR", "FRONT_LEFT_WHEEL_COLOR", "REAR_LEFT_WHEEL_COLOR", "LEFT_COLOR"
        ));
        // the value assigned to the variables, here, there is one value in the
        // list, it will be assign to all variables with the modulo
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList(color));

        // build the conclusion
        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);

        // add the rule to the set of rules
        setRules.add(new Rule(premisse1,conclusion1));

        // the cost of the action to paint two or more pieces is 1
        return new Action(setRules, 1);
    }

    /**
     * Paint the roof, front and rear right wheel and the right if there are the
     * chassis, body, front and rear right wheel
     * @param color the color of the paint
     * @see AssemblyLine#buildMap
     * @return the builded action
     */
    public Action paintRight(String color) {
        Set<Rule> setRules = new HashSet<>();

        // the list of names of variables engaged in the premisse
        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_CHASSIS", "HAS_BODY", "HAS_FRONT_RIGHT_WHEEL", "HAS_REAR_RIGHT_WHEEL"
        ));
        // the value assigned to the variables, here, there is one value in the
        // list, it will be assign to all variables with the modulo
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the premisse
        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        // the list of names of variables engaged in the conclusion
        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                "ROOF_COLOR", "FRONT_RIGHT_WHEEL_COLOR", "REAR_RIGHT_WHEEL_COLOR", "RIGHT_COLOR"
        ));
        // the value assigned to the variables, here, there is one value in the
        // list, it will be assign to all variables with the modulo
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList(color));

        // build the conclusion
        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);

        // add the rule to the set of rules
        setRules.add(new Rule(premisse1,conclusion1));

        // the cost of the action to paint two or more pieces is 1
        return new Action(setRules, 1);
    }

    /**
     * install the right wheels
     * @see AssemblyLine#buildMap
     * @return the builded action
     */
    public Action installRightWheel() {
        Set<Rule> setRules = new HashSet<>();

        // the list of names of variables engaged in the premisse
        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_CHASSIS"
        ));
        // the value assigned to the variables
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the premisse
        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        // the list of names of variables engaged in the conclusion
        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                "HAS_FRONT_RIGHT_WHEEL", "HAS_REAR_RIGHT_WHEEL"
        ));
        // the value assigned to the variables, here, there is one value in the
        // list, it will be assign to all variables with the modulo
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the conclusion
        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);

        // add the rule to the set of rules
        setRules.add(new Rule(premisse1,conclusion1));

        // the cost of the action to paint two or more pieces is 1
        return new Action(setRules, 1);
    }

    /**
     * install the left wheels
     * @see AssemblyLine#buildMap
     * @return the builded action
     */
    public Action installLeftWheel() {
        Set<Rule> setRules = new HashSet<>();

        // the list of names of variables engaged in the premisse
        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_CHASSIS"
        ));
        // the value assigned to the variables
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the premisse
        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        // the list of names of variables engaged in the conclusion
        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                "HAS_FRONT_LEFT_WHEEL", "HAS_REAR_LEFT_WHEEL"
        ));
        // the value assigned to the variables, here, there is one value in the
        // list, it will be assign to all variables with the modulo
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the conclusion
        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);

        // add the rule to the set of rules
        setRules.add(new Rule(premisse1,conclusion1));

        // the cost of the action to paint two or more pieces is 1
        return new Action(setRules, 1);
    }

    /**
     * install the front wheels
     * @see AssemblyLine#buildMap
     * @return the builded action
     */
    public Action installFrontWheel() {
        Set<Rule> setRules = new HashSet<>();

        // the list of names of variables engaged in the premisse
        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_CHASSIS"
        ));
        // the value assigned to the variables
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the premisse
        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        // the list of names of variables engaged in the conclusion
        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                "HAS_FRONT_LEFT_WHEEL", "HAS_FRONT_RIGHT_WHEEL"
        ));
        // the value assigned to the variables, here, there is one value in the
        // list, it will be assign to all variables with the modulo
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the conclusion
        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);

        // add the rule to the set of rules
        setRules.add(new Rule(premisse1,conclusion1));

        // the cost of the action to paint two or more pieces is 1
        return new Action(setRules, 1);
    }

    /**
     * install the rear wheels
     * @see AssemblyLine#buildMap
     * @return the builded action
     */
    public Action installRearWheel() {
        Set<Rule> setRules = new HashSet<>();

        // the list of names of variables engaged in the premisse
        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_CHASSIS"
        ));
        // the value assigned to the variables
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the premisse
        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        // the list of names of variables engaged in the conclusion
        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                "HAS_REAR_LEFT_WHEEL", "HAS_REAR_RIGHT_WHEEL"
        ));
        // the value assigned to the variables, here, there is one value in the
        // list, it will be assign to all variables with the modulo
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList("TRUE"));

        // build the conclusion
        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);

        // add the rule to the set of rules
        setRules.add(new Rule(premisse1,conclusion1));

        // the cost of the action to paint two or more pieces is 1
        return new Action(setRules, 1);
    }
}
