package ca.concordia.soen6441.project;

/**
 * The readMapFile method is called to read the .map file
 * The input parameter includes the file l_path to the .map file
 * */

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
    public void readMapFile(String p_filePath, GameContext p_gameEngine){
        //Validate first
        try {
            Path l_path = Paths.get(p_filePath);
            if (Files.exists(l_path) && Files.isRegularFile(l_path)) {
                readFile(p_filePath, p_gameEngine);
            } else {
                System.out.println("The file does not exist or is not a file.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    private void readFile(String p_filePath, GameContext p_gameEngine) {
        //RiskMap object to manipulate add/remove/modify the l_continent/countries
        // RiskMap p_gameEngine = new RiskMap();
        boolean l_mapIsValid = true;

        //String mapPicName = null; //Name of map picture as per .map file
        try (BufferedReader l_br = new BufferedReader(new FileReader(p_filePath))) {
            String l_line;
            //Map<String, List<String>> sections = new HashMap<>();
            Map<String, List<String>> sections = new LinkedHashMap<>();
            String l_currentSection = null;

            while ((l_line = l_br.readLine()) != null) {
                l_line = l_line.trim();

                if (l_line.isEmpty() || l_line.startsWith(";")) continue; // Ignore empty lines and comments

                if (l_line.startsWith("[")) {
                    l_currentSection = l_line.substring(1, l_line.length() - 1);
                    sections.putIfAbsent(l_currentSection, new ArrayList<>());
                } else if (l_currentSection != null) {
                    sections.get(l_currentSection).add(l_line);
                }
            }
            // Read map l_data
            for (Map.Entry<String, List<String>> entry : sections.entrySet()) {
                String l_section = entry.getKey();
                List<String> l_data = entry.getValue();

                System.out.println("Loading [" + l_section + "] from map...");
                switch (l_section){
                    case "files":
                        for (String l_lineInSection : l_data) {
                            String[] l_parts = l_lineInSection.split("\\s+");
                            if (l_parts.length != 2){
                                System.out.println("Invalid files in map file.");
                                l_mapIsValid = false; //VALIDATION
                            }
//                            else{
//                                String l_fileType = l_parts[0];
//                                String l_fileName = l_parts[1];
//                                // p_gameEngine.addFile(fileType, fileName);
//                            }
                        }
                        break;
                    case "continents":
                        int l_continentID = 1;
                        for (String l_lineInSection : l_data) {
                            String[] l_parts = l_lineInSection.split("\\s+");
                            if (l_parts.length != 3){
                                System.out.println("Invalid continents in map file.");
                                l_mapIsValid = false; //VALIDATION
                            }else{
                                String l_name = l_parts[0];
                                int l_bonus = Integer.parseInt(l_parts[1]);
                                String l_color = l_parts[2];
                                // Add a l_continent
                                p_gameEngine.addContinent(l_continentID, l_name, l_bonus, l_color);
                                l_continentID++;
                            }
                        }
                        break;
                    case "countries":
                        for (String l_lineInSection : l_data) {
                            String[] l_parts = l_lineInSection.split("\\s+");
                            if (l_parts.length != 5){
                                System.out.println("Invalid countries in map file.");
                                l_mapIsValid = false; //VALIDATION
                            }else{

                            int id = Integer.parseInt(l_parts[0]);
                            String l_name = l_parts[1];
                            int continentNumericID = Integer.parseInt(l_parts[2]);
                            int l_x = Integer.parseInt(l_parts[3]);
                            int l_y = Integer.parseInt(l_parts[4]);

                            Continent l_continent = p_gameEngine.getContinentByNumericID(continentNumericID);
                            p_gameEngine.addCountry(id, l_name, l_continent.getID(), l_x, l_y);
                            }
                        }
                        break;
                    case "borders":
                        for (String l_lineInSection : l_data) {
                            String[] l_parts = l_lineInSection.split("\\s+");
                            if (l_parts.length < 2){
                                System.out.println("Invalid borders in map file.");
                                l_mapIsValid = false; //VALIDATION
                            }else{
                                int l_countryId = Integer.parseInt(l_parts[0]);
                                Country l_country = p_gameEngine.getCountryByNumericID(l_countryId);

                                for (int i = 1; i < l_parts.length; i++) {
                                    int neighborId = Integer.parseInt(l_parts[i]);
                                    Country neighbor = p_gameEngine.getCountryByNumericID(neighborId);
                                    if (l_country != null && neighbor != null) {
                                        l_country.addNeighbor(neighbor);
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
        if(l_mapIsValid){
            // p_gameEngine.addMapFilePath(p_filePath, p_gameEngine);
            System.out.println("\nSUCCESS! Map loaded...");
        }else{
            System.out.println("\nSORRY! There was a problem loading the map...");
            // p_gameEngine.clearMapData();
        }
    }
}
