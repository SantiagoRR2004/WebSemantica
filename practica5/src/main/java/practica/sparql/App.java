package practica.sparql;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
  static final String inputFileName =
      Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "europe.ttl")
          .toString();
  static final String queryFolder =
      Paths.get(System.getProperty("user.dir"), "queries").toString();

  private static String wrapWithService(String query, String serviceUrl) {
    // Extract PREFIX declarations
    StringBuilder prefixes = new StringBuilder();
    StringBuilder queryBody = new StringBuilder();

    String[] lines = query.split("\n");
    boolean inPrefixes = true;

    for (String line : lines) {
      String trimmedLine = line.trim();
      if (inPrefixes && trimmedLine.toUpperCase().startsWith("PREFIX")) {
        prefixes.append(line).append("\n");
      } else {
        inPrefixes = false;
        queryBody.append(line).append("\n");
      }
    }

    // Find WHERE clause and wrap its content with SERVICE
    String body = queryBody.toString();
    int whereIndex = body.toUpperCase().indexOf("WHERE");
    if (whereIndex == -1) {
      return query; // Return original if no WHERE clause found
    }

    int openBrace = body.indexOf("{", whereIndex);
    int closeBrace = body.lastIndexOf("}");

    if (openBrace == -1 || closeBrace == -1) {
      return query; // Return original if malformed
    }

    String beforeWhere = body.substring(0, openBrace + 1);
    String whereContent = body.substring(openBrace + 1, closeBrace);
    String afterWhere = body.substring(closeBrace);

    return prefixes.toString()
        + beforeWhere
        + "\n"
        + "    SERVICE <"
        + serviceUrl
        + "> {\n"
        + whereContent
        + "    }\n"
        + afterWhere;
  }

  private static String readQuery(String fileName) {
    try {
      return Files.readString(Paths.get(System.getProperty("user.dir"), "queries", fileName));
    } catch (Exception e) {
      System.err.println("Error reading " + fileName + ": " + e.getMessage());
      return null;
    }
  }

  public static void main(String[] args) {
    SparqlRunner runner = new SparqlRunner(inputFileName);

    File folder = new File(queryFolder);
    File[] listOfFiles = folder.listFiles();

    // Sort files to ensure consistent order
    if (listOfFiles != null) {
      java.util.Arrays.sort(listOfFiles);
    }

    for (File file : listOfFiles) {
      String q = readQuery(file.getName());
      String qNormal;
      try {
        qNormal = runner.runQueryAndCapture(q);
      } catch (Exception e) {
        qNormal = runner.runConstructQueryAndCapture(q);
      }

      // Wrap with SERVICE
      q = wrapWithService(q, "http://localhost:3030/europe/sparql");
      String qService;
      try {
        qService = runner.runQueryAndCapture(q);
      } catch (Exception e) {
        qService = runner.runConstructQueryAndCapture(q);
      }

      // Compare results
      if (!qNormal.equals(qService)) {
        throw new RuntimeException(
            "Query results mismatch for "
                + file.getName()
                + ": Normal and SERVICE queries produced different results!");
      }

      try {
        runner.runQuery(q);
      } catch (Exception e) {
        runner.runConstructQuery(q);
      }
    }


  }
}
