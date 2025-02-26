/**
* The readMapFile method is called to read the .map file
* The input parameter includes the file path to the .map file
* */
package ca.concordia.soen6441.project;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.GameContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MapFileReader {
    //Checks the existence of file before reading it
    public void readMapFile(String filePath){
        //Validate first
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path) && Files.isRegularFile(path)) {
                readFile(filePath);
            } else {
                System.out.println("The file does not exist or is not a file.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    private void readFile(String filePath) {
        //RiskMap object to manipulate add/remove/modify the continent/countries
        GameEngine gameEngine = new GameEngine();
        boolean mapIsValid = true;

        //String mapPicName = null; //Name of map picture as per .map file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            //Map<String, List<String>> sections = new HashMap<>();
            Map<String, List<String>> sections = new LinkedHashMap<>();
            String currentSection = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith(";")) continue; // Ignore empty lines and comments

                if (line.startsWith("[")) {
                    currentSection = line.substring(1, line.length() - 1);
                    sections.putIfAbsent(currentSection, new ArrayList<>());
                } else if (currentSection != null) {
                    sections.get(currentSection).add(line);
                }
            }
            // Read map data for Files and Continents
            for (Map.Entry<String, List<String>> entry : sections.entrySet()) {
                String section = entry.getKey();
                List<String> data = entry.getValue();

                switch (section){
                    case "files":
                        System.out.println("Loading [" + section + "] from map...");
                        for (String lineInSection : data) {
                            String[] parts = lineInSection.split("\\s+");
                            if (parts.length != 2){
                                System.out.println("Invalid files in map file.");
                                mapIsValid = false; //VALIDATION
                            }else{
                                String l_fileType = parts[0];
                                String l_fileName = parts[1];
                                gameEngine.addFile(l_fileType, l_fileName);
                            }
                        }
                        break;
                    case "continents":
                        System.out.println("Loading [" + section + "] from map...");
                        int continentID = 1;
                        for (String lineInSection : data) {
                            String[] parts = lineInSection.split("\\s+");
                            if (parts.length != 3){
                                System.out.println("Invalid continents in map file.");
                                mapIsValid = false; //VALIDATION
                            }else{
                                String name = parts[0];
                                int bonus = Integer.parseInt(parts[1]);
                                String color = parts[2];
                                // Add a continent
                                gameEngine.addContinent(new ContinentImpl(continentID, name, bonus, color));
                                continentID++;
                            }
                        }
                        break;
                }
            }
            // Read map data for Countries
            for (Map.Entry<String, List<String>> entry : sections.entrySet()) {
                String section = entry.getKey();
                List<String> data = entry.getValue();

                if (section.equals("countries")){
                    System.out.println("Loading [" + section + "] from map...");
                    for (String lineInSection : data) {
                        String[] parts = lineInSection.split("\\s+");
                        if (parts.length != 5){
                            System.out.println("Invalid countries in map file.");
                            mapIsValid = false; //VALIDATION
                        }else{
                            int id = Integer.parseInt(parts[0]);
                            String name = parts[1];
                            int continentId = Integer.parseInt(parts[2]);
                            String continentIdS = gameEngine.getContinentByNumericID(continentId).getID();
                            int x = Integer.parseInt(parts[3]);
                            int y = Integer.parseInt(parts[4]);
                            gameEngine.addCountry(new CountryImpl(id, name, continentIdS, x, y));
                        }
                    }
                }
            }
            //Read map data for Borders
            for (Map.Entry<String, List<String>> entry : sections.entrySet()) {
                String section = entry.getKey();
                List<String> data = entry.getValue();

                if (section.equals("borders")){
                    System.out.println("Loading [" + section + "] from map...");
                    for (String lineInSection : data) {
                        String[] parts = lineInSection.split("\\s+");
                        if (parts.length < 2){
                            System.out.println("Invalid borders in map file.");
                            mapIsValid = false; //VALIDATION
                        }else{
                            int countryId = Integer.parseInt(parts[0]);
                            Country country = gameEngine.getCountryByNumericID(countryId);

                            for (int i = 1; i < parts.length; i++) {
                                int neighborId = Integer.parseInt(parts[i]);
                                Country neighbor = gameEngine.getCountryByNumericID(countryId);
                                if (country != null && neighbor != null) {
                                    country.addNeighbor(neighbor);

                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(mapIsValid){
            gameEngine.addMapFilePath(filePath);
            System.out.println("\nSUCCESS! Map loaded...");
        }else{
            System.out.println("\nSORRY! There was a problem loading the map...");
            gameEngine.clearMapData();
        }
    }
}
