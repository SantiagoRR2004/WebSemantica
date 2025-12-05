import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

  static final String inputFileName =
      Paths.get(System.getProperty("user.dir"), "authors.ttl").toString();
  static final String queryFolder = Paths.get(System.getProperty("user.dir"), "queries").toString();
  static final String outputFolder =
      Paths.get(System.getProperty("user.dir"), "outputs").toString();
  static final String outputFileName =
      Paths.get(System.getProperty("user.dir"), "authors-enriched.ttl").toString();

  private static String readQuery(String fileName) {
    try {
      return Files.readString(Paths.get(System.getProperty("user.dir"), "queries", fileName));
    } catch (Exception e) {
      System.err.println("Error reading " + fileName + ": " + e.getMessage());
      return null;
    }
  }

  public static void main(String[] args) {

    // Create the runner
    SparqlRunner runner = new SparqlRunner(inputFileName);

    File folder = new File(queryFolder);
    File[] listOfFiles = folder.listFiles();

    // Sort files to ensure consistent order (natural ordering by number)
    if (listOfFiles != null) {
      java.util.Arrays.sort(
          listOfFiles,
          (f1, f2) -> {
            String name1 = f1.getName();
            String name2 = f2.getName();

            // Extract numeric part (e.g., "q1.sparql" -> 1)
            int num1 = Integer.parseInt(name1.replaceAll("\\D+", ""));
            int num2 = Integer.parseInt(name2.replaceAll("\\D+", ""));

            return Integer.compare(num1, num2);
          });
    }

    // Ensure output directory exists
    new File(outputFolder).mkdirs();

    for (File file : listOfFiles) {
      // Execute each file
      String queryString = readQuery(file.getName());
      String result = runner.runConstructQuery(queryString);

      // Save the result to a file
      String resultFileName =
          Paths.get(outputFolder, file.getName().replace(".sparql", ".ttl")).toString();
      try {
        Files.writeString(Paths.get(resultFileName), result);
      } catch (Exception e) {
        System.err.println("Error writing " + resultFileName + ": " + e.getMessage());
      }

      // Nothing went wrong
      System.out.println(file.getName());
    }

    // Save the file
    runner.saveToFile(outputFileName);
  }
}
