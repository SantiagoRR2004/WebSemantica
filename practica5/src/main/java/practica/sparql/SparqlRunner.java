package practica.sparql;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
public class SparqlRunner {
    private Model model ;
    public SparqlRunner ( String rdfFilePath ) {
        model = ModelFactory.createDefaultModel( ) ;
        model.read(rdfFilePath);
    }
    public void runQuery ( String queryStr ) {
        System.out.println( " \n==== EJECUTANDO CONSULTA ====\n" ) ;
        Query query = QueryFactory.create(queryStr);
        try ( QueryExecution qexec =
                QueryExecutionFactory.create(query, model) ) {
            ResultSet results = qexec.execSelect( ) ;
            ResultSetFormatter.out( System.out , results , query ) ;
        }
    }
}