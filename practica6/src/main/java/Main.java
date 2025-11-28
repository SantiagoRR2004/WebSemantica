import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

  static final String inputFileName =
      Paths.get(System.getProperty("user.dir"), "authors.ttl").toString();
  static final String queryFile =
      Paths.get(System.getProperty("user.dir"), "queries", "q1.sparql").toString();

  public static void main(String[] args) {
    String queryString = "";
    try {
      queryString = Files.readString(Paths.get(queryFile));
    } catch (Exception e) {
      System.err.println("Error reading " + queryFile + ": " + e.getMessage());
      return;
    }

    SparqlRunner runner = new SparqlRunner(inputFileName);
    runner.runConstructQuery(queryString);
  }
}
