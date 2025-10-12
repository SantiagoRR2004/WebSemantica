package renfe;

import java.nio.file.Paths;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;

public class Part1 {

    static final String inputFileName = Paths.get(System.getProperty("user.dir"), "data", "stops.txt").toString();
    static final String outputFileName = Paths.get(System.getProperty("user.dir"), "renfe.ttl").toString();

    public static void main(String[] args) {
        List<Stop> stops = CSVReader.parseStops(inputFileName);
        Model model = ModelFactory.createDefaultModel();

        // Define namespaces/prefixes
        String exNS = "http://www.ejemplo.com/";
        String geoNS = "http://www.w3.org/2003/01/geo/wgs84_pos#";
        String rdfsNS = "http://www.w3.org/2000/01/rdf-schema#";
        String xsdNS = "http://www.w3.org/2001/XMLSchema#";

        // Set prefixes for Turtle output
        // https://jena.apache.org/documentation/javadoc/jena/org.apache.jena.core/org/apache/jena/shared/PrefixMapping.html
        model.setNsPrefix("ex", exNS);
        model.setNsPrefix("geo", geoNS);
        model.setNsPrefix("rdfs", rdfsNS);
        model.setNsPrefix("xsd", xsdNS);

        for (Stop stop : stops) {
            Resource stopResource = model.createResource(exNS + stop.getStopId());
            stopResource.addProperty(RDF.type,
                    model.createResource(geoNS + "SpatialThing"));
            stopResource.addLiteral(model.createProperty(geoNS + "lat"),
                    stop.getStopLat());
            stopResource.addLiteral(model.createProperty(geoNS + "long"),
                    stop.getStopLon());
            stopResource.addProperty(model.createProperty(rdfsNS + "label"),
                    stop.getStopName());
        }
        model.write(System.out, "TURTLE");

        // Save the file
        try (java.io.FileOutputStream out = new java.io.FileOutputStream(outputFileName)) {
            RDFDataMgr.write(out, model, org.apache.jena.riot.Lang.TURTLE);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }
}