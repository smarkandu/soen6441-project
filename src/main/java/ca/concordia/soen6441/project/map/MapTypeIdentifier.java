package ca.concordia.soen6441.project.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapTypeIdentifier {
    public String mapType(String p_filename) {
        List<String> l_sectionHeadings = readSectionHeadings(p_filename);
        if (l_sectionHeadings.size() == 3 && l_sectionHeadings.get(0).equals("map") && l_sectionHeadings.get(1).equals("continents") && l_sectionHeadings.get(2).equals("territories")){
            return "ConquestMap";
        } else if(l_sectionHeadings.size() == 4 && l_sectionHeadings.get(0).equals("files") && l_sectionHeadings.get(1).equals("continents") && l_sectionHeadings.get(2).equals("countries") && l_sectionHeadings.get(3).equals("borders")){
            return "DominationMap";
        }else{
            return "UnknownFormat";
        }
    }

    private List<String> readSectionHeadings(String p_filepath) {
        List<String> l_sectionHeadings = new ArrayList<>();
        Pattern l_pattern = Pattern.compile("\\[(.*?)\\]"); // Regex to match text inside square brackets

        try (BufferedReader l_reader = new BufferedReader(new FileReader(p_filepath))) {
            String l_line;
            while ((l_line = l_reader.readLine()) != null) {
                Matcher l_matcher = l_pattern.matcher(l_line);
                if (l_matcher.find()) {
                    l_sectionHeadings.add(l_matcher.group(1).toLowerCase()); // Add the matched text (without brackets)
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return l_sectionHeadings;
    }
}