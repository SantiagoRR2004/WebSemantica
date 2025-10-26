package renfe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CSVReader {
  public static List<Stop> parseStops(String csvPath) {
    List<Stop> stops = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
      stops =
          br.lines()
              .skip(1) // Skip header line
              .map(line -> new Stop(line.split(",")))
              .collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stops;
  }

  public static Map<String, List<StopTime>> parseStopTimes(String csvPath) {
    try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
      return br.lines()
          .skip(1)
          .map(line -> new StopTime(line.split(",")))
          .collect(Collectors.groupingBy(StopTime::getStopId)); // Group by stopId
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyMap();
    }
  }

  public static void main(String[] args) {
    String csvFile = "data/stops.txt";
    List<Stop> stops = parseStops(csvFile);
  }
}
