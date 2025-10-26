package simpson;

import java.nio.file.Paths;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class Part2 {
  static final String inputFileName =
      Paths.get(System.getProperty("user.dir"), "simpson.ttl").toString();
  static final String outputFileName =
      Paths.get(System.getProperty("user.dir"), "family.ttl").toString();

  public static void main(String[] args) {
    // Create an empty model
    Model model = ModelFactory.createDefaultModel();

    // Open the file
    RDFDataMgr.read(model, inputFileName);

    String familyNs = "http://www.esei.uvigo.es/ws/familia#";
    String foafNs = "http://xmlns.com/foaf/0.1/";
    String rdfNs = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    String xsdNs = "http://www.w3.org/2001/XMLSchema#";
    String simNs = "http://www.esei.uvigo.es/ws/simpsons#";

    // Get family
    Resource family = model.getResource(familyNs + "Family");
    // Set family as a Class
    family.addProperty(RDF.type, RDFS.Class);

    // Get Person
    Resource person = model.getResource(foafNs + "Person");
    person.addProperty(RDF.type, RDFS.Class);

    // Get Gender
    Resource gender = model.getResource(foafNs + "Gender");
    gender.addProperty(RDF.type, RDFS.Class);

    // Get masculine and feminine
    Resource masculine = model.getResource(familyNs + "Masc");
    masculine.addProperty(RDFS.subClassOf, gender);
    Resource feminine = model.getResource(familyNs + "Fem");
    feminine.addProperty(RDFS.subClassOf, gender);

    // Set properties as Properties
    Property isRelatedTo = model.getProperty(foafNs + "isRelatedTo");
    Property hasGender = model.getProperty(foafNs + "hasGender");
    Property hasMemberFamily = model.getProperty(familyNs + "hasMemberFamily");
    Property hasSiblings = model.getProperty(familyNs + "hasSiblings");
    Property hasBrother = model.getProperty(familyNs + "hasBrother");
    Property hasSister = model.getProperty(familyNs + "hasSister");
    Property hasProgenitor = model.getProperty(familyNs + "hasProgenitor");
    Property hasFather = model.getProperty(familyNs + "hasFather");
    Property hasMother = model.getProperty(familyNs + "hasMother");
    Property hasSpouse = model.getProperty(familyNs + "hasSpouse");

    // Define their types, ranges and domains
    hasGender
        .addProperty(RDF.type, RDF.Property)
        .addProperty(RDFS.domain, person)
        .addProperty(RDFS.range, gender);
    isRelatedTo
        .addProperty(RDF.type, RDF.Property)
        .addProperty(RDFS.domain, person)
        .addProperty(RDFS.range, person);
    hasMemberFamily
        .addProperty(RDF.type, RDF.Property)
        .addProperty(RDFS.domain, person)
        .addProperty(RDFS.range, family);
    hasSiblings
        .addProperty(RDF.type, RDF.Property)
        .addProperty(RDFS.domain, person)
        .addProperty(RDFS.range, person);
    hasBrother.addProperty(RDFS.subPropertyOf, hasSiblings);
    hasSister.addProperty(RDFS.subPropertyOf, hasSiblings);
    hasProgenitor
        .addProperty(RDFS.subPropertyOf, isRelatedTo)
        .addProperty(RDFS.domain, person)
        .addProperty(RDFS.range, person);
    hasFather.addProperty(RDFS.subPropertyOf, hasProgenitor);
    hasMother.addProperty(RDFS.subPropertyOf, hasProgenitor);
    hasSpouse.addProperty(RDF.type, RDF.Property);

    // Save the file
    try (java.io.FileOutputStream out = new java.io.FileOutputStream(outputFileName)) {
      RDFDataMgr.write(out, model, org.apache.jena.riot.Lang.TURTLE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
