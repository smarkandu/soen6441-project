package ca.concordia.soen6441.project.map;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ConquestMapReader {

    /**
     * Reads and processes a Conquest map file.
     *
     * @param p_filePath the path of the Conquest map file.
     * @return true if the map is loaded successfully; false otherwise.
     * @throws FileNotFoundException if the file is not found.
     */
    public boolean readConquestMap(String p_filePath) throws FileNotFoundException {
        boolean l_mapIsValid = true;
        Map<String, List<String>> l_sections;
        try {
            l_sections = parseMapFile(p_filePath);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        // Process [Map] section.
        if (l_sections.containsKey("Map")) {
            //processMapSection(sections.get("Map"));
        } else {
            //System.out.println("No [Map] section found.");
        }

        // Process [Continents] section.
        if (l_sections.containsKey("Continents")) {
            l_mapIsValid &= processContinentsSection(l_sections.get("Continents"));
        } else {
            System.out.println("No [Continents] section found.");
            l_mapIsValid = false;
        }

        // Process [Territories] section.
        if (l_sections.containsKey("Territories")) {
            l_mapIsValid &= processTerritoriesSection(l_sections.get("Territories"));
        } else {
            System.out.println("No [Territories] section found.");
            l_mapIsValid = false;
        }

        if (l_mapIsValid) {
            System.out.println("\nConquest Map loaded...");
        } else {
            System.out.println("\nSORRY! There was a problem loading the Conquest map...");
        }
        return l_mapIsValid;
    }

    /**
     * Parses the map file into sections.
     */
    private Map<String, List<String>> parseMapFile(String p_filePath) throws IOException {
        Map<String, List<String>> l_sections = new LinkedHashMap<>();
        String l_currentSection = null;
        try (BufferedReader l_br = new BufferedReader(new FileReader(p_filePath))) {
            String l_line;
            while((l_line = l_br.readLine()) != null) {
                l_line = l_line.trim();
                if(l_line.isEmpty() || l_line.startsWith(";"))
                    continue;
                if(l_line.startsWith("[")) {
                    l_currentSection = l_line.substring(1, l_line.length() - 1);
                    l_sections.putIfAbsent(l_currentSection, new ArrayList<>());
                } else if(l_currentSection != null) {
                    l_sections.get(l_currentSection).add(l_line);
                }
            }
        }
        return l_sections;
    }

    /**
     * Processes the [Map] section.
     */
    private void processMapSection(List<String> p_lines) {
        for(String l_line : p_lines) {
            // Example: "image=Asia.bmp"
        }
    }

    /**
     * Processes the [Continents] section.
     * Expected format per line: ContinentName=bonus
     */
    private boolean processContinentsSection(List<String> p_lines) {
        boolean l_valid = true;
        int l_continentID = 1;
        for(String l_line : p_lines) {
            String[] l_parts = l_line.split("=");
            if(l_parts.length != 2) {
                System.out.println("Invalid continent format: " + l_line);
                l_valid = false;
                continue;
            }
            try {
                String l_name = l_parts[0].trim();
                int l_bonus = Integer.parseInt(l_parts[1].trim());
                GameDriver.getGameEngine().getContinentManager().addContinent(l_continentID, l_name, l_bonus, "NoColor");
                l_continentID++;
            } catch(NumberFormatException e) {
                System.out.println("Invalid bonus number in continents section: " + l_line);
                l_valid = false;
            }
        }
        return l_valid;
    }

    /**
     * Processes the [Territories] section.
     * Expected format per line: TerritoryName,x,y,Continent,Neighbor1,Neighbor2,...
     */
    private boolean processTerritoriesSection(List<String> p_lines) {
        boolean l_valid = true;
        int l_countryId = 1;
        //Add territories/countries first - before adding neighbors
        for(String l_line : p_lines) {
            String[] l_parts = l_line.split(",");
            if(l_parts.length < 5) {
                System.out.println("Invalid territory format: " + l_line);
                System.out.println("Note: A country should have at least one neighbor.");
                l_valid = false;
                continue;
            }
            String l_countryName = l_parts[0].trim();
            int l_x, l_y;
            try {
                l_x = Integer.parseInt(l_parts[1].trim());
                l_y = Integer.parseInt(l_parts[2].trim());
            } catch(NumberFormatException e) {
                System.out.println("Invalid coordinates in territory: " + l_line);
                l_valid = false;
                continue;
            }
            String l_continentName = l_parts[3].trim();
            // Retrieve the continent using getContinent(String)
            Continent l_continent = GameDriver.getGameEngine().getContinentManager().getContinent(l_continentName);
            if (l_continent == null) {
                System.out.println("Continent not found for territory/country: " + l_line);
                l_valid = false;
                continue;
            }
            // Add the country.
            GameDriver.getGameEngine().getCountryManager().addCountry(l_countryId, l_countryName, l_continent.getID(), l_x, l_y);
            l_countryId++;
        }
        //Add neighbors only after all the countries are added.
        if(l_valid){
            for(String l_line: p_lines){
                String[] l_parts = l_line.split(",");
                Country l_country = getCountryByName(l_parts[0].trim());
                for (int l_i = 4; l_i < l_parts.length; l_i++) {
                    String l_neighborName = l_parts[l_i].trim();
                    Country l_neighbor = getCountryByName(l_neighborName);
                    if (l_country != null && l_neighbor != null) {
                        l_country.addNeighbor(l_neighbor);
                    }
                }
            }
        }
        return l_valid;
    }
    private Country getCountryByName(String p_countryName){
        SortedMap<String, Country> d_Countries = GameDriver.getGameEngine().getCountryManager().getCountries();
        return d_Countries.get(p_countryName);
    }
}
