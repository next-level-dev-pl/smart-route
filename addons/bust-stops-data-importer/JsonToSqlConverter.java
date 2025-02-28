import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


/**
 * Bus Stops Data Converter
 * ========================
 * Simple JSON to PostgresSQL data converter for bus stops.
 * <p>
 * This utility converts JSON data containing bus stop information into SQL INSERT
 * statements that can be directly imported into a PostgreSQL database. The converter
 * handles the specific JSON structure where each bus stop record consists of key-value
 * pairs for attributes like location coordinates, station name, and effective dates.
 * 
 * @author Dawid Bielecki ('dawciobiel')
 * @version 1.0.0-alpha
 * @since 2025-02-28
 * 
 * @see <a href="https://github.com/next-level-dev-pl/smart-route/">Project Repository</a>
 * 
 * <pre>
 * Organization: next-level-dev-pl
 * Project: smart-route
 * URL: https://github.com/next-level-dev-pl/smart-route/
 * </pre>
 */
public class JsonToSqlConverter {

    public static void main(String[] args) {
        String inputFile;
        String outputFile;
        Scanner scanner = new Scanner(System.in);
        
        if (args.length >= 2) {
            inputFile = args[0];
            outputFile = args[1];
        } else {
            System.out.print("Enter input JSON filename (e.g., bus-stops.json): ");
            inputFile = scanner.nextLine().trim();
            if (inputFile.isEmpty()) {
                inputFile = "bus-stops.json";
            }
            
            System.out.print("Enter output SQL filename (e.g., bus-stops.sql): ");
            outputFile = scanner.nextLine().trim();
            if (outputFile.isEmpty()) {
                String baseName = inputFile.contains(".") ? 
                    inputFile.substring(0, inputFile.lastIndexOf('.')) : 
                    inputFile;
                outputFile = baseName + ".sql";
            }
        }
        
        scanner.close();
        
        File input = new File(inputFile);
        if (!input.exists()) {
            System.out.println("Error: File " + inputFile + " does not exist.");
            return;
        }
        
        try {
            boolean success = convertJsonToSql(inputFile, outputFile);
            if (success) {
                System.out.println("Conversion completed successfully. Data has been saved to " + outputFile);
                System.out.println("You can import the file to PostgreSQL using the command:");
                System.out.println("psql -U username -d database_name -f " + outputFile);
            }
        } catch (Exception e) {
            System.out.println("An error occurred during conversion: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static boolean convertJsonToSql(String inputFile, String outputFile) throws IOException {
        JSONObject jsonData;
        
        try (FileReader reader = new FileReader(inputFile)) {
            JSONTokener tokener = new JSONTokener(reader);
            jsonData = new JSONObject(tokener);
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("-- Creating bus stops table\n");
            writer.write("CREATE TABLE IF NOT EXISTS bus_stops (\n");
            writer.write("    zespol TEXT,\n");
            writer.write("    slupek TEXT,\n");
            writer.write("    nazwa_zespolu TEXT,\n");
            writer.write("    id_ulicy TEXT,\n");
            writer.write("    szer_geo NUMERIC,\n");
            writer.write("    dlug_geo NUMERIC,\n");
            writer.write("    kierunek TEXT,\n");
            writer.write("    obowiazuje_od TIMESTAMP\n");
            writer.write(");\n\n");
            writer.write("-- Inserting data into the bus stops table\n");
            
            if (jsonData.has("result")) {
                JSONArray resultArray = jsonData.getJSONArray("result");
                
                for (int i = 0; i < resultArray.length(); i++) {
                    JSONObject item = resultArray.getJSONObject(i);
                    
                    if (item.has("values")) {
                        JSONArray valuesArray = item.getJSONArray("values");
                        Map<String, String> valuesMap = new HashMap<>();
                        
                        for (int j = 0; j < valuesArray.length(); j++) {
                            JSONObject valueObj = valuesArray.getJSONObject(j);
                            if (valueObj.has("key") && valueObj.has("value")) {
                                String key = valueObj.getString("key");
                                String value = valueObj.getString("value");
                                valuesMap.put(key, value);
                            }
                        }
                        
                        String[] keys = {"zespol", "slupek", "nazwa_zespolu", "id_ulicy", 
                                        "szer_geo", "dlug_geo", "kierunek", "obowiazuje_od"};
                        
                        StringBuilder insertValues = new StringBuilder();
                        for (int k = 0; k < keys.length; k++) {
                            String key = keys[k];
                            String value = valuesMap.getOrDefault(key, "");
                            
                            if (key.equals("szer_geo") || key.equals("dlug_geo")) {
                                try {
                                    Float.parseFloat(value);
                                    insertValues.append(value);
                                } catch (NumberFormatException e) {
                                    insertValues.append("NULL");
                                }
                            } else {
                                insertValues.append("'").append(value.replace("'", "''")).append("'");
                            }
                            
                            if (k < keys.length - 1) {
                                insertValues.append(", ");
                            }
                        }
                        
                        writer.write("INSERT INTO bus_stops (zespol, slupek, nazwa_zespolu, id_ulicy, szer_geo, dlug_geo, kierunek, obowiazuje_od)\n");
                        writer.write("VALUES (" + insertValues.toString() + ");\n");
                    }
                }
            }
            
            System.out.println("Successfully generated SQL file: " + outputFile);
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return false;
        }
    }
}
