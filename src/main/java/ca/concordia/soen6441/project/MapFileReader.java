/**
 * The readMapFile method is called to read the .map file
 * The input parameter includes the file path to the .map file
 * */

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
        RiskMap riskMap = new RiskMap();
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
            // Read map data
            for (Map.Entry<String, List<String>> entry : sections.entrySet()) {
                String section = entry.getKey();
                List<String> data = entry.getValue();

                System.out.println("Loading [" + section + "] from map...");
                switch (section){
                    case "files":
                        for (String lineInSection : data) {
                            String[] parts = lineInSection.split("\\s+");
                            if (parts.length != 2){
                                System.out.println("Invalid files in map file.");
                                mapIsValid = false; //VALIDATION
                            }else{
                                String fileType = parts[0];
                                String fileName = parts[1];
                                riskMap.addFile(fileType, fileName);
                            }
                        }
                        break;
                    case "continents":
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
                                riskMap.addContinent(new Continent(continentID, name, bonus, color));
                                continentID++;
                            }
                        }
                        break;
                    case "countries":
                        for (String lineInSection : data) {
                            String[] parts = lineInSection.split("\\s+");
                            if (parts.length != 5){
                                System.out.println("Invalid countries in map file.");
                                mapIsValid = false; //VALIDATION
                            }else{

                            int id = Integer.parseInt(parts[0]);
                            String name = parts[1];
                            int continentId = Integer.parseInt(parts[2]);
                            int x = Integer.parseInt(parts[3]);
                            int y = Integer.parseInt(parts[4]);

                            riskMap.addCountry(new Country(id, name, continentId, x, y));
                            }
                        }
                        break;
                    case "borders":
                        for (String lineInSection : data) {
                            String[] parts = lineInSection.split("\\s+");
                            if (parts.length < 2){
                                System.out.println("Invalid borders in map file.");
                                mapIsValid = false; //VALIDATION
                            }else{
                                int countryId = Integer.parseInt(parts[0]);
                                Country country = riskMap.getCountries().get(countryId);

                                for (int i = 1; i < parts.length; i++) {
                                    int neighborId = Integer.parseInt(parts[i]);
                                    Country neighbor = riskMap.getCountries().get(neighborId);
                                    if (country != null && neighbor != null) {
                                        country.addNeighbor(neighbor);

                                    }
                                }
                            }
                        }
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(mapIsValid){
            riskMap.addMapFilePath(filePath);
            System.out.println("\nSUCCESS! Map loaded...");
        }else{
            System.out.println("\nSORRY! There was a problem loading the map...");
            riskMap.clearMapData();
        }
    }
}
