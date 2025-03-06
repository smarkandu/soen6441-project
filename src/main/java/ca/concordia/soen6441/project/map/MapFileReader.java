package ca.concordia.soen6441.project.map;

/**
 * The readMapFile method is called to read the .map file
 * The input parameter includes the file l_path to the .map file
 * */

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.GameContext;

import java.io.*;
import java.util.*;

public class MapFileReader {
    //Checks the existence of file before reading it
    public void readMapFile(String p_filePath, GameContext p_gameEngine) throws FileNotFoundException {
        //Validate first
        try
        {
            readFile(p_filePath, p_gameEngine);
        }
        catch (FileNotFoundException e)
        {
            throw e; // We catch exception (so it's not caught by IOException), and throw it back to caller
        }
        catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    private void readFile(String p_filePath, GameContext p_gameEngine) throws FileNotFoundException {
        //RiskMap object to manipulate add/remove/modify the l_continent/countries
        // RiskMap p_gameEngine = new RiskMap();
        boolean l_mapIsValid = true;

        //String mapPicName = null; //Name of map picture as per .map file
        try (BufferedReader l_br = new BufferedReader(new FileReader(p_filePath))) {
            String l_line;
            Map<String, List<String>> l_sections = new LinkedHashMap<>();
            String l_currentSection = null;

            while ((l_line = l_br.readLine()) != null) {
                l_line = l_line.trim();

                if (l_line.isEmpty() || l_line.startsWith(";")) continue; // Ignore empty lines and comments

                if (l_line.startsWith("[")) {
                    l_currentSection = l_line.substring(1, l_line.length() - 1);
                    l_sections.putIfAbsent(l_currentSection, new ArrayList<>());
                } else if (l_currentSection != null) {
                    l_sections.get(l_currentSection).add(l_line);
                }
            }
            // Read map l_data
            for (Map.Entry<String, List<String>> l_entry : l_sections.entrySet()) {
                String l_section = l_entry.getKey();
                List<String> l_data = l_entry.getValue();

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

                            int l_id = Integer.parseInt(l_parts[0]);
                            String l_name = l_parts[1];
                            int l_continentNumericID = Integer.parseInt(l_parts[2]);
                            int l_x = Integer.parseInt(l_parts[3]);
                            int l_y = Integer.parseInt(l_parts[4]);

                            Continent l_continent = p_gameEngine.getContinentByNumericID(l_continentNumericID);
                            p_gameEngine.addCountry(l_id, l_name, l_continent.getID(), l_x, l_y);
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

                                for (int l_i = 1; l_i < l_parts.length; l_i++) {
                                    int l_neighborId = Integer.parseInt(l_parts[l_i]);
                                    Country l_neighbor = p_gameEngine.getCountryByNumericID(l_neighborId);
                                    if (l_country != null && l_neighbor != null) {
                                        l_country.addNeighbor(l_neighbor);
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        }
        catch (FileNotFoundException e)
        {
            throw e; // We catch exception (so it's not caught by IOException), and throw it back to caller
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if(l_mapIsValid){
            // p_gameEngine.addMapFilePath(p_filePath, p_gameEngine);
            System.out.println("\nMap loaded...");
        }else{
            System.out.println("\nSORRY! There was a problem loading the map...");
            // p_gameEngine.clearMapData();
        }
    }
}
