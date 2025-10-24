package simpson;

import java.nio.file.Paths;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;

public class Main {
    static final String outputFileName = Paths.get(System.getProperty("user.dir"), "simpson.ttl").toString();  
    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();
        String familyNs = "http://www.esei.uvigo.es/ws/familia#";
        String foafNs = "http://xmlns.com/foaf/0.1/";
        String rdfNs = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
        String xsdNs = "http://www.w3.org/2001/XMLSchema#";
        String simNs = "http://www.esei.uvigo.es/ws/simpsons#";

        model.setNsPrefix("fam", familyNs);
        model.setNsPrefix("foaf", foafNs);
        model.setNsPrefix("rdf", rdfNs);
        model.setNsPrefix("xsd", xsdNs);
        model.setNsPrefix("sim", simNs);

        // Create class family:
        Resource familyClass = model.createResource(familyNs + "Family");
        // Create properties
        Property hasMemberFamily = model.createProperty(familyNs + "hasMemberFamily");
        Property hasBrother = model.createProperty(familyNs + "hasBrother");
        Property hasSister = model.createProperty(familyNs + "hasSister");
        Property hasProgenitor = model.createProperty(familyNs + "hasProgenitor");
        Property hasFather = model.createProperty(familyNs + "hasFather");
        Property hasMother = model.createProperty(familyNs + "hasMother");
        Property hasSpouse = model.createProperty(familyNs + "hasSpouse");
        
        // Create family Simpson
        Resource simpsonFamily = model.createResource(simNs + "SimpsonFamily")
                .addProperty(RDF.type, familyClass);
        
        // Create members of the Simpson family
        Resource homer = model.createResource(simNs + "HomerSimpson")
                .addProperty(RDF.type, FOAF.Person)
                .addProperty(FOAF.name, "Homer Simpson")
                .addLiteral(FOAF.age, 36);
        Resource marge = model.createResource(simNs + "MargeSimpson")
                .addProperty(RDF.type, FOAF.Person)
                .addProperty(FOAF.name, "Marge Simpson")
                .addLiteral(FOAF.age, 34);
        Resource bart = model.createResource(simNs + "BartSimpson")
                .addProperty(RDF.type, FOAF.Person)
                .addProperty(FOAF.name, "Bart Simpson")
                .addLiteral(FOAF.age, 10);
        Resource lisa = model.createResource(simNs + "LisaSimpson")
                .addProperty(RDF.type, FOAF.Person)
                .addProperty(FOAF.name, "Lisa Simpson")
                .addLiteral(FOAF.age, 8);
        Resource maggie = model.createResource(simNs + "MaggieSimpson")
                .addProperty(RDF.type, FOAF.Person)
                .addProperty(FOAF.name, "Maggie Simpson")
                .addLiteral(FOAF.age, 1);
        Resource abe = model.createResource(simNs + "AbeSimpson")
                .addProperty(RDF.type, FOAF.Person)
                .addProperty(FOAF.name, "Abe Simpson")
                .addLiteral(FOAF.age, 87);
        Resource mona = model.createResource(simNs + "MonaSimpson")
                .addProperty(RDF.type, FOAF.Person)
                .addProperty(FOAF.name, "Mona Simpson")
                .addLiteral(FOAF.age, 87);
        

        // Define hasMemberFamily relationships
        simpsonFamily.addProperty(hasMemberFamily, homer);
        simpsonFamily.addProperty(hasMemberFamily, marge);
        simpsonFamily.addProperty(hasMemberFamily, bart);
        simpsonFamily.addProperty(hasMemberFamily, lisa);
        simpsonFamily.addProperty(hasMemberFamily, maggie);
        simpsonFamily.addProperty(hasMemberFamily, abe);
        simpsonFamily.addProperty(hasMemberFamily, mona);

        // Homer relationships
        homer.addProperty(hasSpouse, marge);
        homer.addProperty(hasFather, abe);
        homer.addProperty(hasMother, mona);

        // Marge relationships
        marge.addProperty(hasSpouse, homer);

        // Abe relationships
        abe.addProperty(hasSpouse, mona);

        // Bart relationships
        bart.addProperty(hasFather, homer);
        bart.addProperty(hasMother, marge);
        bart.addProperty(hasSister, lisa);
        bart.addProperty(hasSister, maggie);

        // Lisa relationships
        lisa.addProperty(hasFather, homer);
        lisa.addProperty(hasMother, marge);
        lisa.addProperty(hasBrother, bart);
        lisa.addProperty(hasSister, maggie);

        // Maggie relationships
        maggie.addProperty(hasFather, homer);
        maggie.addProperty(hasMother, marge);
        maggie.addProperty(hasBrother, bart);
        maggie.addProperty(hasSister, lisa);

        // Save the file
        try(java.io.FileOutputStream out = new java.io.FileOutputStream(outputFileName)) {
            RDFDataMgr.write(out, model, org.apache.jena.riot.Lang.TURTLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}