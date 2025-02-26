package ca.concordia.soen6441.project;

public class GameDriver {
    public static void main(String[] p_args) {
        /*
        GameEngine l_gameEngine = new GameEngine();
        l_gameEngine.start();
        */
        MapFileReader mapFileReader = new MapFileReader();
        mapFileReader.readMapFile("C:\\Users\\MyFamily - Dell\\Downloads\\temp\\europass.map");
    }
}