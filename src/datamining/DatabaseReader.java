
package datamining;

import java.io.*;
import java.util.*;
import representations.*;

public class DatabaseReader {

    private Set<Variable> variables;

    public DatabaseReader(Set<Variable> variables) {
        this.variables = variables;
    }

    public Database importDB(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader (filename));
        Database database = this.readDB(reader);
        reader.close();
        return database;
    }

    public Database readDB(BufferedReader in) throws IOException {
        String variableLine = in.readLine();
        List<Variable> orderedVariables = this.readVariables(variableLine);
        List<Map<Variable, String>> transactions = this.readTransactions(in, orderedVariables);
        return new Database(orderedVariables, transactions);
    }

    public List<Variable> readVariables(String line) throws IOException {
        List<Variable> variables = new ArrayList<>();
        String[] variableNames = line.split(";");
        for (String variableName : variableNames) {
            boolean found = false;
            for (Variable var : this.variables) {
                if (var.getName().equals(variableName)) {
                    variables.add(var);
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IOException("Unknown variable name: " + variableName);
            }
        }
        return variables;
    }

    public List<Map<Variable, String>> readTransactions(
            BufferedReader in, List<Variable> orderedVariables
        )  throws IOException {

        List<Map<Variable, String>> transactions = new ArrayList<>();
        String line;
        int lineNb = 1;
        while ((line = in.readLine()) != null) {
            String[] values = line.split(";");
            if (values.length != orderedVariables.size()) {
                throw new IOException("Wrong number of fields on line " + lineNb);
            }
            Map<Variable, String> transaction = new HashMap<>();
            for (int i = 0; i < values.length; i++) {
                transaction.put(orderedVariables.get(i), values[i]);
            }
            transactions.add(transaction);
            lineNb += 1;
        }
        System.out.println("nb = " + lineNb);
        return transactions;
    }
}
