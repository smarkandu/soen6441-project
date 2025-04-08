package ca.concordia.soen6441.project.map;
import ca.concordia.soen6441.project.interfaces.MapReader;

import java.io.FileNotFoundException;

public class ConquestMapAdapter implements MapReader
{

    private ConquestMapReader conquestReader;

    public ConquestMapAdapter() {
        this.conquestReader = new ConquestMapReader();
    }

    /**
     * Adapts the Conquest map file reading to the MapReader interface.
     *
     * @param filePath the file path of the Conquest map.
     * @throws FileNotFoundException if the file is not found.
     */
    @Override
    public void readMapFile(String filePath) throws FileNotFoundException {
        boolean valid = conquestReader.readConquestMap(filePath);
        if (!valid) {
            System.out.println("Conquest map is invalid.");
        }
    }
}
