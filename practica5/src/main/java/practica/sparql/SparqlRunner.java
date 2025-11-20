package practica.sparql;

import java.io.ByteArrayOutputStream;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class SparqlRunner {
  private Model model;

  public SparqlRunner(String rdfFilePath) {
    model = ModelFactory.createDefaultModel();
    model.read(rdfFilePath);
  }

  public void runQuery(String queryStr) {
    System.out.println(" \n==== EJECUTANDO CONSULTA ====\n");
    Query query = QueryFactory.create(queryStr);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet results = qexec.execSelect();
      ResultSetFormatter.out(System.out, results, query);
    }
  }

  public String runQueryAndCapture(String queryStr) {
    Query query = QueryFactory.create(queryStr);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet results = qexec.execSelect();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ResultSetFormatter.out(baos, results, query);
      return baos.toString();
    }
  }

  public void runConstructQuery(String queryStr) {
    System.out.println(" \n==== EJECUTANDO CONSULTA CONSTRUCT ====\n");
    Query query = QueryFactory.create(queryStr);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      Model constructModel = qexec.execConstruct();
      constructModel.write(System.out, "TURTLE");
    }
  }

  public String runConstructQueryAndCapture(String queryStr) {
    Query query = QueryFactory.create(queryStr);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      Model constructModel = qexec.execConstruct();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      constructModel.write(baos, "TURTLE");
      
      // Sort the output lines to ensure consistent ordering for comparison
      String output = baos.toString();
      String[] lines = output.split("\n");
      java.util.Arrays.sort(lines);
      return String.join("\n", lines);
    }
  }
}
