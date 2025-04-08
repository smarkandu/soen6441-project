package ca.concordia.soen6441.project.interfaces;
import java.io.FileNotFoundException;

public interface MapReader
{
    /**
     * Reads a map from the given file path.
     *
     * @param filePath the file path of the map.
     * @throws FileNotFoundException if the file is not found.
     */
    void readMapFile(String filePath) throws FileNotFoundException;
}
