package renfe;
import java.nio.file.Paths;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdf.model.ResIterator;
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

        // Define properties and type
        Property typeProperty = model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        Resource spatialThingType = model.createResource("http://www.w3.org/2003/01/geo/wgs84_pos#SpatialThing");
        Property latProperty = model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat");
        Property lonProperty = model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long");
        Property labelProperty = model.createProperty("http://www.w3.org/2000/01/rdf-schema#label");

        // Get all subjects that have the type SpatialThing
        ResIterator iter = model.listResourcesWithProperty(typeProperty, spatialThingType);
        
        while (iter.hasNext()) {
            Resource subject = iter.nextResource();

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