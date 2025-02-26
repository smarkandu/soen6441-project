/**This class records all relevant information from the .map file
 * Holds the current game information relevant to map (all info stored in .map file).
 * It holds accessor and mutator methods for data
 */

import java.io.File;
import java.util.*;

public class RiskMap {
    private static Map<Integer, Continent> continents;  // Map of continentId -> Continent
    private static Map<Integer, Country> countries;     // Map of countryId -> Country
    private static Map<String, String> files;           // Map of file type -> file name
    private static String mapFilePath, directory, fileName;// File path of the .map file that was read
    //e.g. mapFilePath: C:\Users\Dell\Downloads\temp\europass.map
    //e.g. directory:   C:\Users\Dell\Downloads\temp\
    //e.g. fileName:    europass.map
    // Static initialization block
    static {
        continents = new HashMap<>();
        countries = new HashMap<>();
        files = new HashMap<>();
        mapFilePath = "";
        directory = "";
        fileName = "";
    }
    // Add a continent to the game
    public void addContinent(Continent continent) {
        if (continent == null) {
            throw new IllegalArgumentException("Continent cannot be null");
        }
        continents.put(continent.getId(), continent);
    }

    // Add a country to the game and link it to its continent
    public void addCountry(Country country) {
        if (country == null) {
            throw new IllegalArgumentException("Country cannot be null");
        }
        countries.put(country.getId(), country);
        Continent continent = continents.get(country.getContinentId());
        if (continent != null) {
            continent.addCountry(country);
        }
    }

    // Add files as per the .map file
    public void addFile(String fileType, String fileName){
        if (fileType == null || fileName == null) {
            throw new IllegalArgumentException("File type or file name cannot be null");
        }
        files.put(fileType, fileName);
    }

    // Record file path, directory, and fileName of the .map file
    public void addMapFilePath(String mapFilePathx){
        mapFilePath = mapFilePathx;
        // Create a File object
        File file = new File(mapFilePath);
        // Get the parent directory and file name
        directory = file.getParent() + File.separator;
        fileName = file.getName();
    }

    // Remove all data before reloading the map
    public void clearMapData(){
        continents.clear();
        countries.clear();
        files.clear();
        mapFilePath = "";
        directory = "";
        fileName = "";
    }

    public String getMapFilePath() {
        return mapFilePath;
    }

    public String getDirectory() {
        return directory;
    }

    public String getFileName() {
        return fileName;
    }

    public Map<Integer, Continent> getContinents() {
        return continents;
    }

    public Map<Integer, Country> getCountries() {
        return countries;
    }

    public Map<String, String> getFiles(){
        return files;
    }
    @Override
    public String toString() {
        return "RiskMap{" +
                "\ncontinents=" + continents +
                ",\n countries=" + countries +
                ",\n files=" + files +
                ",\n mapFilePath='" + mapFilePath + '\'' +
                ",\n directory='" + directory + '\'' +
                ",\n fileName='" + fileName + '\'' +
                "\n}";
    }
}
