package renfe;
import java.nio.file.Paths;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFDataMgr;

public class Part2 {

    static final String inputFileName = Paths.get(System.getProperty("user.dir"), "renfe.ttl").toString();

    public static void main(String[] args) {
        // Create an empty model
        Model model = ModelFactory.createDefaultModel();

        // Open the file
        RDFDataMgr.read(model, inputFileName);

        // https://es.wikipedia.org/wiki/Galicia#Geograf%C3%ADa
        float topLatitude = 43.47f;
        float bottomLatitude = 41.49f;
        float leftLongitude = -9.18f;
        float rightLongitude = -6.42f;

        StmtIterator iter = model.listStatements();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement(); 
            Resource  subject = stmt.getSubject();

            Property latProperty = model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat");
            Property lonProperty = model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long");
            Property labelProperty = model.createProperty("http://www.w3.org/2000/01/rdf-schema#label");

            if (subject.hasProperty(latProperty) && subject.hasProperty(lonProperty)) {
                float lat = subject.getProperty(latProperty).getFloat();
                float lon = subject.getProperty(lonProperty).getFloat();
                if (lat <= topLatitude && lat >= bottomLatitude && lon >= leftLongitude && lon <= rightLongitude) {
                    System.out.println(subject.getProperty(labelProperty).getObject());
                }
            } 
        }
    }
}