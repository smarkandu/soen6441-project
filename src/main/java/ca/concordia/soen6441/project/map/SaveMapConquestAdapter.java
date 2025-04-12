package ca.concordia.soen6441.project.map;

import ca.concordia.soen6441.project.interfaces.MapComponent;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SaveMapConquestAdapter implements MapComponent{
    @Override
    public String toMapString()
    {
        SaveMapConquestFormatter l_saveMapConquestFormatter = new SaveMapConquestFormatter();
        return l_saveMapConquestFormatter.conquestFormatter();
    }
}
