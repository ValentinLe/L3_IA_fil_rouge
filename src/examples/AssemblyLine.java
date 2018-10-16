
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
    private Set<Variable> ELEMENT_COLOR;

    public AssemblyLine() {
        this.elementsBool = new HashSet<>(Arrays.asList(
                "HAS_CHASSIS", "HAS_FRONT_LEFT_WHEEL", "HAS_FRONT_RIGHT_WHEEL",
                "HAS_REAR_LEFT_WHEEL", "HAS_REAR_RIGHT_WHEEL", "HAS_BODY"
        ));

        Set<String> domainBooleans = new HashSet<>(Arrays.asList(
                "TRUE", "FALSE"
        ));

        this.componentsColor = new HashSet<>(Arrays.asList(
                "FRONT_COLOR", "LEFT_COLOR", "REAR_COLOR", "RIGHT_COLOR", "ROOF_COLOR",
                "FRONT_LEFT_WHEEL_COLOR", "FRONT_RIGHT_WHEEL_COLOR", "REAR_LEFT_WHEEL_COLOR",
                "REAR_RIGHT_WHEEL_COLOR", "BODY_COLOR"
        ));
        /*
        this.domainColors = new HashSet<>(Arrays.asList(
                "GRAY", "BLACK", "WHITE", "RED", "BLUE", "GREEN", "ORANGE", "YELLOW"
        ));
        */
        this.domainColors = new HashSet<>(Arrays.asList(
                "GRAY", "BLACK"
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
        this.voiture.put(this.mapVar.get("HAS_CHASSIS"), "TRUE");

        this.ELEMENT_COLOR = new HashSet<>();

        for (String component : this.componentsColor) {
            Variable var = new Variable(component, this.domainColors);
            this.ELEMENT_COLOR.add(var);
            this.voiture.put(var,"GRAY");
            this.mapVar.put(component, var);
        }

        //this.voiture = generateCar();
    }

    public State getInitState() {
        return new State(this.voiture);
    }

    public State getGoal() {
        return new State(this.generateCar());
    }

    public Set<Action> getActions() {
        Set<Action> actions = new HashSet<>();
        for (String color : this.domainColors) {
            actions.add(this.paintRear(color));
            actions.add(this.paintFront(color));
            actions.add(this.paintLeft(color));
            actions.add(this.paintRight(color));
            actions.add(this.paintRoof(color));
        }

        for (String comp : this.elementsBool) {
            actions.add(this.installPiece(comp));
        }
        return actions;
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
        for (Variable var : this.ELEMENT_COLOR) {
            voiture.put(var, colorSelect);
        }
        return voiture;
    }

    public Map<Variable, String> buildMap(ArrayList<String> varStr, ArrayList<String> values) {
        Map<Variable, String> map = new HashMap<>();
        for (int i = 0; i<varStr.size(); i++) {
            map.put(this.mapVar.get(varStr.get(i)),values.get(Math.floorMod(i,values.size())));
        }
        return map;
    }

    public Action installPiece(String piece) {
        Set<Rule> setRules = new HashSet<>();

        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_CHASSIS"
        ));
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                piece
        ));
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList("TRUE"));

        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);


        setRules.add(new Rule(premisse1,conclusion1));

        return new Action(setRules, 2);
    }

    public Action paintRoof(String color) {
        Set<Rule> setRules = new HashSet<>();

        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_BODY"
        ));
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                "ROOF_COLOR", "BODY_COLOR"
        ));
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList(color));

        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);


        setRules.add(new Rule(premisse1,conclusion1));

        return new Action(setRules, 1);
    }

    public Action paintRear(String color) {
        Set<Rule> setRules = new HashSet<>();

        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_BODY"
        ));
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                "REAR_COLOR","ROOF_COLOR"
        ));
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList(color));

        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);


        setRules.add(new Rule(premisse1,conclusion1));

        return new Action(setRules, 1);
    }

    public Action paintFront(String color) {
        Set<Rule> setRules = new HashSet<>();

        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_CHASSIS", "HAS_BODY"
        ));
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                "ROOF_COLOR", "FRONT_COLOR"
        ));
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList(color));

        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);


        setRules.add(new Rule(premisse1,conclusion1));

        return new Action(setRules, 1);
    }

    public Action paintLeft(String color) {
        Set<Rule> setRules = new HashSet<>();

        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_CHASSIS", "HAS_BODY", "HAS_FRONT_LEFT_WHEEL", "HAS_REAR_LEFT_WHEEL"
        ));
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                "ROOF_COLOR", "FRONT_LEFT_WHEEL_COLOR", "REAR_LEFT_WHEEL_COLOR", "LEFT_COLOR"
        ));
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList(color));

        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);


        setRules.add(new Rule(premisse1,conclusion1));

        return new Action(setRules, 1);
    }

    public Action paintRight(String color) {
        Set<Rule> setRules = new HashSet<>();

        ArrayList<String> varStrP1 = new ArrayList<>(Arrays.asList(
                "HAS_CHASSIS", "HAS_BODY", "HAS_FRONT_RIGHT_WHEEL", "HAS_REAR_RIGHT_WHEEL"
        ));
        ArrayList<String> valeursP1 = new ArrayList<>(Arrays.asList("TRUE"));

        Map<Variable, String> premisse1 = buildMap(varStrP1, valeursP1);

        ArrayList<String> varStrC1 = new ArrayList<>(Arrays.asList(
                "ROOF_COLOR", "FRONT_RIGHT_WHEEL_COLOR", "REAR_RIGHT_WHEEL_COLOR", "RIGHT_COLOR"
        ));
        ArrayList<String> valeursC1 = new ArrayList<>(Arrays.asList(color));

        Map<Variable, String> conclusion1 = buildMap(varStrC1, valeursC1);


        setRules.add(new Rule(premisse1,conclusion1));

        return new Action(setRules, 1);
    }
}
