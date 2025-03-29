package ca.concordia.soen6441.project.map;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.context.GameContext;

import java.io.*;
import java.util.*;

public class MapFileReader {

    /**
     * Checks the existence of file before reading it
     * @param p_filePath The file path of the .map file.
     * @param p_gameEngine The game engine context to update with the map data.
     * @throws FileNotFoundException If the specified file is not found.
     */
    public void readMapFile(String p_filePath, GameContext p_gameEngine) throws FileNotFoundException {
        //Validate first
        try {
            readFile(p_filePath, p_gameEngine);
        } catch (FileNotFoundException e) {
            throw e; // We catch exception (so it's not caught by IOException), and throw it back to caller
        } catch (Exception e) {
            // For now, log the error to standard error
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    /**
     * Reads the file and processes its content to extract map details.
     *
     * @param p_filePath   The file path of the .map file.
     * @param p_gameEngine The game engine context to update with the map data.
     * @throws FileNotFoundException If the specified file is not found.
     */
    private void readFile(String p_filePath, GameContext p_gameEngine) throws FileNotFoundException {
        boolean l_mapIsValid = true;
        Map<String, List<String>> l_sections;

        try {
            l_sections = parseMapFile(p_filePath);
        } catch (FileNotFoundException fnfe) {
            // Rethrow FileNotFoundException so that the test can catch it.
            throw fnfe;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        // Process each section separately.
        for (Map.Entry<String, List<String>> l_entry : l_sections.entrySet()) {
            String l_section = l_entry.getKey();
            List<String> l_data = l_entry.getValue();
            System.out.println("Loading [" + l_section + "] from map...");

            switch (l_section) {
                case "files":
                    l_mapIsValid &= processFilesSection(l_data);
                    break;
                case "continents":
                    l_mapIsValid &= processContinentsSection(l_data, p_gameEngine);
                    break;
                case "countries":
                    l_mapIsValid &= processCountriesSection(l_data, p_gameEngine);
                    break;
                case "borders":
                    l_mapIsValid &= processBordersSection(l_data, p_gameEngine);
                    break;
                default:
                    // Unknown sections can be ignored or handled as needed.
                    break;
            }
        }

        if (l_mapIsValid) {
            System.out.println("\nMap loaded...");
        } else {
            System.out.println("\nSORRY! There was a problem loading the map...");
        }
    }

    /**
     * Parses the .map file into sections.
     *
     * @param p_filePath The file path of the .map file.
     * @return A map with section names as keys and their corresponding lines as values.
     * @throws IOException If an I/O error occurs.
     */
    private Map<String, List<String>> parseMapFile(String p_filePath) throws IOException {
        Map<String, List<String>> l_sections = new LinkedHashMap<>();
        String l_currentSection = null;

        try (BufferedReader l_br = new BufferedReader(new FileReader(p_filePath))) {
            String l_line;
            while ((l_line = l_br.readLine()) != null) {
                l_line = l_line.trim();
                if (l_line.isEmpty() || l_line.startsWith(";")) {
                    continue; // Skip empty lines and comments
                }
                if (l_line.startsWith("[")) {
                    l_currentSection = l_line.substring(1, l_line.length() - 1);
                    l_sections.putIfAbsent(l_currentSection, new ArrayList<>());
                } else if (l_currentSection != null) {
                    l_sections.get(l_currentSection).add(l_line);
                }
            }
        }
        return l_sections;
    }

    /**
     * Processes the "files" section of the map file.
     *
     * @param p_lines The list of lines in the "files" section.
     * @return true if the section is valid; false otherwise.
     */
    private boolean processFilesSection(List<String> p_lines) {
        boolean l_mapIsValid = true;
        for (String l_line : p_lines) {
            String[] l_parts = l_line.split("\\s+");
            if (l_parts.length != 2) {
                System.out.println("Invalid files in map file.");
                l_mapIsValid = false;
            }
            // If needed, additional processing for files can be added here.
        }
        return l_mapIsValid;
    }

    /**
     * Processes the "continents" section of the map file.
     *
     * @param p_lines       The list of lines in the "continents" section.
     * @param p_gameEngine The game context to update.
     * @return true if the section is valid; false otherwise.
     */
    private boolean processContinentsSection(List<String> p_lines, GameContext p_gameEngine) {
        boolean l_mapIsValid = true;
        int l_continentID = 1;
        for (String l_line : p_lines) {
            String[] l_parts = l_line.split("\\s+");
            if (l_parts.length != 3) {
                System.out.println("Invalid continents in map file.");
                l_mapIsValid = false;
            } else {
                try {
                    String l_name = l_parts[0];
                    int l_bonus = Integer.parseInt(l_parts[1]);
                    String l_color = l_parts[2];
                    p_gameEngine.getContinentManager().addContinent(l_continentID, l_name, l_bonus, l_color);
                    l_continentID++;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in continents section.");
                    l_mapIsValid = false;
                }
            }
        }
        return l_mapIsValid;
    }

    /**
     * Processes the "countries" section of the map file.
     *
     * @param p_lines       The list of lines in the "countries" section.
     * @param p_gameEngine The game context to update.
     * @return true if the section is valid; false otherwise.
     */
    private boolean processCountriesSection(List<String> p_lines, GameContext p_gameEngine) {
        boolean l_mapIsValid = true;
        for (String l_line : p_lines) {
            String[] l_parts = l_line.split("\\s+");
            if (l_parts.length != 5) {
                System.out.println("Invalid countries in map file.");
                l_mapIsValid = false;
            } else {
                try {
                    int l_id = Integer.parseInt(l_parts[0]);
                    String l_name = l_parts[1];
                    int l_continentNumericID = Integer.parseInt(l_parts[2]);
                    int l_x = Integer.parseInt(l_parts[3]);
                    int l_y = Integer.parseInt(l_parts[4]);
                    Continent l_continent = p_gameEngine.getContinentManager().getContinentByNumericID(l_continentNumericID);
                    p_gameEngine.getCountryManager().addCountry(l_id, l_name, l_continent.getID(), l_x, l_y);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in countries section.");
                    l_mapIsValid = false;
                }
            }
        }
        return l_mapIsValid;
    }

    /**
     * Processes the "borders" section of the map file.
     *
     * @param p_lines       The list of lines in the "borders" section.
     * @param p_gameEngine The game context to update.
     * @return true if the section is valid; false otherwise.
     */
    private boolean processBordersSection(List<String> p_lines, GameContext p_gameEngine) {
        boolean l_mapIsValid = true;
        for (String l_line : p_lines) {
            String[] l_parts = l_line.split("\\s+");
            if (l_parts.length < 2) {
                System.out.println("Invalid borders in map file.");
                l_mapIsValid = false;
            } else {
                try {
                    int l_countryId = Integer.parseInt(l_parts[0]);
                    Country l_country = p_gameEngine.getCountryManager().getCountryByNumericID(l_countryId);
                    for (int l_i = 1; l_i < l_parts.length; l_i++) {
                        int l_neighborId = Integer.parseInt(l_parts[l_i]);
                        Country l_neighbor = p_gameEngine.getCountryManager().getCountryByNumericID(l_neighborId);
                        if (l_country != null && l_neighbor != null) {
                            l_country.addNeighbor(l_neighbor);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in borders section.");
                    l_mapIsValid = false;
                }
            }
        }
        return l_mapIsValid;
    }
}