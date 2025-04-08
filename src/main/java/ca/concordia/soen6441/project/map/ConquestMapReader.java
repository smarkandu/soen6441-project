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
     * @param filePath the path of the Conquest map file.
     * @return true if the map is loaded successfully; false otherwise.
     * @throws FileNotFoundException if the file is not found.
     */
    public boolean readConquestMap(String filePath) throws FileNotFoundException {
        boolean mapIsValid = true;
        Map<String, List<String>> sections;
        try {
            sections = parseMapFile(filePath);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        // Process [Map] section.
        if (sections.containsKey("Map")) {
            //processMapSection(sections.get("Map"));
        } else {
            //System.out.println("No [Map] section found.");
        }

        // Process [Continents] section.
        if (sections.containsKey("Continents")) {
            mapIsValid &= processContinentsSection(sections.get("Continents"));
        } else {
            System.out.println("No [Continents] section found.");
            mapIsValid = false;
        }

        // Process [Territories] section.
        if (sections.containsKey("Territories")) {
            mapIsValid &= processTerritoriesSection(sections.get("Territories"));
        } else {
            System.out.println("No [Territories] section found.");
            mapIsValid = false;
        }

        if (mapIsValid) {
            System.out.println("\nConquest Map loaded...");
        } else {
            System.out.println("\nSORRY! There was a problem loading the Conquest map...");
        }
        return mapIsValid;
    }

    /**
     * Parses the map file into sections.
     */
    private Map<String, List<String>> parseMapFile(String filePath) throws IOException {
        Map<String, List<String>> sections = new LinkedHashMap<>();
        String currentSection = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = br.readLine()) != null) {
                line = line.trim();
                if(line.isEmpty() || line.startsWith(";"))
                    continue;
                if(line.startsWith("[")) {
                    currentSection = line.substring(1, line.length() - 1);
                    sections.putIfAbsent(currentSection, new ArrayList<>());
                } else if(currentSection != null) {
                    sections.get(currentSection).add(line);
                }
            }
        }
        return sections;
    }

    /**
     * Processes the [Map] section.
     */
    private void processMapSection(List<String> lines) {
        for(String line : lines) {
            // Example: "image=Asia.bmp"
        }
    }

    /**
     * Processes the [Continents] section.
     * Expected format per line: ContinentName=bonus
     */
    private boolean processContinentsSection(List<String> lines) {
        boolean valid = true;
        int continentID = 1;
        for(String line : lines) {
            String[] parts = line.split("=");
            if(parts.length != 2) {
                System.out.println("Invalid continent format: " + line);
                valid = false;
                continue;
            }
            try {
                String name = parts[0].trim();
                int bonus = Integer.parseInt(parts[1].trim());
                GameDriver.getGameEngine().getContinentManager().addContinent(continentID, name, bonus, "NoColor");
                continentID++;
            } catch(NumberFormatException e) {
                System.out.println("Invalid bonus number in continents section: " + line);
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Processes the [Territories] section.
     * Expected format per line: TerritoryName,x,y,Continent,Neighbor1,Neighbor2,...
     */
    private boolean processTerritoriesSection(List<String> lines) {
        boolean valid = true;
        int countryId = 1;
        //Add territories/countries first - before adding neighbors
        for(String line : lines) {
            String[] parts = line.split(",");
            if(parts.length < 5) {
                System.out.println("Invalid territory format: " + line);
                System.out.println("Note: A country should have at least one neighbor.");
                valid = false;
                continue;
            }
            String countryName = parts[0].trim();
            int x, y;
            try {
                x = Integer.parseInt(parts[1].trim());
                y = Integer.parseInt(parts[2].trim());
            } catch(NumberFormatException e) {
                System.out.println("Invalid coordinates in territory: " + line);
                valid = false;
                continue;
            }
            String continentName = parts[3].trim();
            // Retrieve the continent using getContinent(String)
            Continent continent = GameDriver.getGameEngine().getContinentManager().getContinent(continentName);
            if (continent == null) {
                System.out.println("Continent not found for territory/country: " + line);
                valid = false;
                continue;
            }
            // Add the country.
            GameDriver.getGameEngine().getCountryManager().addCountry(countryId, countryName, continent.getID(), x, y);
            countryId++;
        }
        //Add neighbors only after all the countries are added.
        if(valid){
            for(String line: lines){
                String[] parts = line.split(",");
                Country l_country = getCountryByName(parts[0].trim());
                for (int i = 4; i < parts.length; i++) {
                    String neighborName = parts[i].trim();
                    Country l_neighbor = getCountryByName(neighborName);
                    if (l_country != null && l_neighbor != null) {
                        l_country.addNeighbor(l_neighbor);
                    }
                }
            }
        }
        return valid;
    }
    private Country getCountryByName(String countryName){
        SortedMap<String, Country> d_Countries = GameDriver.getGameEngine().getCountryManager().getCountries();
        return d_Countries.get(countryName);
    }
}
