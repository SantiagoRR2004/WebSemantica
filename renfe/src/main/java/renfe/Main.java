package renfe;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String csvFile = "data/stops.txt";
        List<Stop> stops = CSVReader.parseStops(csvFile);
    }
}