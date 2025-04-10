package ca.concordia.soen6441.project.map;

import ca.concordia.soen6441.project.interfaces.MapReader;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.io.FileNotFoundException;

public class ConquestMapAdapter implements MapReader
{

    private ConquestMapReader d_conquestReader;

    public ConquestMapAdapter()
    {
        this.d_conquestReader = new ConquestMapReader();
    }

    /**
     * Adapts the Conquest map file reading to the MapReader interface.
     *
     * @param p_filePath the file path of the Conquest map.
     * @throws FileNotFoundException if the file is not found.
     */
    @Override
    public void readMapFile(String p_filePath) throws FileNotFoundException
    {
        boolean l_valid = d_conquestReader.readConquestMap(p_filePath);
        if (!l_valid)
        {
            LogEntryBuffer.getInstance().appendToBuffer("Conquest map is invalid.", true);
        }
    }
}
