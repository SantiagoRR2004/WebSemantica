package practica.sparql;

import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
  static final String inputFileName =
      Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "europe.ttl")
          .toString();

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

    // Consulta 1: Países cuyos nombres comienzan con la letra 'A'
    String q1 = readQuery("q1.sparql");
    runner.runQuery(q1);

    // Consulta 2: Paises cuyo nombre termina por  "a":
    String q2 = readQuery("q2.sparql");
    runner.runQuery(q2);

    // Consulta 3: Empiezan por "A" y terminan por "a"
    String q3 = readQuery("q3.sparql");
    runner.runQuery(q3);

    // Consulta 4: Cuyo PIB per capita es mayor que 20.000
    String q4 = readQuery("q4.sparql");
    runner.runQuery(q4);

    // Consulta 5:  PIB es mayor que 20 000 y su población es menor de 40 millones
    String q5 = readQuery("q5.sparql");
    runner.runQuery(q5);

    // Consulta 6: País con mayor PIB
    String q6 = readQuery("q6.sparql");
    runner.runQuery(q6);

    // Consulta 7: Calcular PIB medio
    String q7 = readQuery("q7.sparql");
    runner.runQuery(q7);

    // Consulta 8: Países con PIB superior al PIB medio
    String q8 = readQuery("q8.sparql");
    runner.runQuery(q8);

    // Consulta 9: Países con población similar a España (+- 30%) con mayor PIB.
    String q9 = readQuery("q9.sparql");
    runner.runQuery(q9);

    // Consulta 10: Crear propiedad que indique el PIB en euros.
    String q10 = readQuery("q10.sparql");
    runner.runConstructQuery(q10); // We have added this method call to run the CONSTRUCT query

    // Consulta 11: Crear una nueva propiedad llamada ex:gdpRank, cuyo objetivo es indicar la
    // posición de
    // cada país europeo en función de su PIB per cápita, de mayor a menor.
    // The only way I found to do this is by counting how many countries have a higher GDP per
    // capita than the current one.
    // Use OPTIONAL because the highest GDP country will not have any.
    String q11 = readQuery("q11.sparql");
    runner.runConstructQuery(q11);

    /*
     *
     * SAME CONSULTATIONS BUT WITH http://localhost:3030/europe/sparql
     *
     */

    // Consulta 1: Países cuyos nombres comienzan con la letra 'A'
    q1 = wrapWithService(q1, "http://localhost:3030/europe/sparql");
    runner.runQuery(q1);

    // Consulta 2: Paises cuyo nombre termina por  "a":
    q2 = wrapWithService(q2, "http://localhost:3030/europe/sparql");
    runner.runQuery(q2);

    // Consulta 3: Empiezan por "A" y terminan por "a"
    q3 = wrapWithService(q3, "http://localhost:3030/europe/sparql");
    runner.runQuery(q3);

    // Consulta 4: Cuyo PIB per capita es mayor que 20.000
    q4 = wrapWithService(q4, "http://localhost:3030/europe/sparql");
    runner.runQuery(q4);

    // Consulta 5:  PIB es mayor que 20 000 y su población es menor de 40 millones
    q5 = wrapWithService(q5, "http://localhost:3030/europe/sparql");
    runner.runQuery(q5);

    // Consulta 6: País con mayor PIB
    q6 = wrapWithService(q6, "http://localhost:3030/europe/sparql");
    runner.runQuery(q6);

    // Consulta 7: Calcular PIB medio
    q7 = wrapWithService(q7, "http://localhost:3030/europe/sparql");
    runner.runQuery(q7);

    // Consulta 8: Países con PIB superior al PIB medio
    q8 = wrapWithService(q8, "http://localhost:3030/europe/sparql");
    runner.runQuery(q8);

    // Consulta 9: Países con población similar a España (+- 30%) con mayor PIB.
    q9 = wrapWithService(q9, "http://localhost:3030/europe/sparql");
    runner.runQuery(q9);

    // Consulta 10: Crear propiedad que indique el PIB en euros.
    q10 = wrapWithService(q10, "http://localhost:3030/europe/sparql");
    runner.runConstructQuery(q10); // We have added this method call to run the CONSTRUCT query

    // Consulta 11: Crear una nueva propiedad llamada ex:gdpRank, cuyo objetivo es indicar la
    // posición de
    // cada país europeo en función de su PIB per cápita, de mayor a menor.
    // The only way I found to do this is by counting how many countries have a higher GDP per
    // capita than the current one.
    // Use OPTIONAL because the highest GDP country will not have any.
    q11 = wrapWithService(q11, "http://localhost:3030/europe/sparql");
    runner.runConstructQuery(q11);
  }
}
