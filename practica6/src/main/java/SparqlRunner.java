import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;

public class SparqlRunner {
  private Model model;

  public SparqlRunner(String rdfFilePath) {
    model = ModelFactory.createDefaultModel();
    model.read(rdfFilePath);
  }

  public void runConstructQuery(String queryStr) {
    Query query = QueryFactory.create(queryStr);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      Model constructModel = qexec.execConstruct();
      constructModel.write(System.out, "TURTLE");
      // Add all statements from the construct result to the current model
      model.add(constructModel);
    }
  }

  public void saveToFile(String fileName) {
    try (java.io.FileOutputStream out = new java.io.FileOutputStream(fileName)) {
      RDFDataMgr.write(out, model, org.apache.jena.riot.Lang.TURTLE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
