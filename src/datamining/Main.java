
package datamining;

import java.io.*;
import java.util.*;
import representations.*;
import examples.Examples;

public class Main {
    public static void main(String[] args) throws IOException {

        Examples ex = new Examples();
        DatabaseReader data = new DatabaseReader(ex.getVariables());
        
        try {
            data.importDB("datamining/example_db.csv");
        } catch(IOException excep) {
            excep.printStackTrace();
        }
    }
}
