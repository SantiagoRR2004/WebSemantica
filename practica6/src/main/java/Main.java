import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;

public class Main {

  static final String inputFileName = Paths.get(System.getProperty("user.dir"), "authors.ttl").toString();
  static final String queryFile = Paths.get(System.getProperty("user.dir"), "queries", "q1.sparql").toString();

  public static void main(String[] args) {
    // Create an empty model
    Model model = ModelFactory.createDefaultModel();

    // Open the file
    RDFDataMgr.read(model, inputFileName);

    String queryString = "";

    try {
      queryString = Files.readString(Paths.get(queryFile));
    } catch (Exception e) {
      System.err.println("Error reading " + queryFile + ": " + e.getMessage());
    }

    System.out.println(queryString);


    Query query = QueryFactory.create(queryString);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSetFormatter.out(System.out, qexec.execSelect(), query);
    }


  }
}
