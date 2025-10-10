package renfe;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class CSVReader {
    public static List<Stop> parseStops(String csvPath) {
        List<Stop> stops = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            // Skip header
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Stop stop = new Stop(values);
                stops.add(stop);
                //System.out.println(stop);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stops;
    }
    public static void main(String[] args) {
        String csvFile = "data/stops.txt";
        List<Stop> stops = parseStops(csvFile);
    }
}
