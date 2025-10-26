import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class Cine {
  public static void main(String[] args) {
    Model model = ModelFactory.createDefaultModel();
    String ex = "http://example.org/cine#";

    // Define classes
    Resource staffMember =
        model.createResource(ex + "StaffMember").addProperty(RDF.type, RDFS.Class);
    Resource movie = model.createResource(ex + "Movie").addProperty(RDF.type, RDFS.Class);

    // Define subclasses
    Resource movieStaff = model.createResource(ex + "MovieStaff");
    Resource director = model.createResource(ex + "Director");
    Resource actor = model.createResource(ex + "Actor");
    Resource writer = model.createResource(ex + "Writer");

    // Subclasses relationships
    movieStaff.addProperty(RDFS.subClassOf, staffMember);
    director.addProperty(RDFS.subClassOf, movieStaff);
    actor.addProperty(RDFS.subClassOf, movieStaff);
    writer.addProperty(RDFS.subClassOf, movieStaff);

    // Create properties
    Property involves = model.createProperty(ex + "involves");
    Property isDirectedBy = model.createProperty(ex + "isDirectedBy");
    Property name = model.createProperty(ex + "name");
    Property phone = model.createProperty(ex + "phone");

    // Subproperty relationships
    involves.addProperty(RDF.type, RDF.Property);
    isDirectedBy.addProperty(RDFS.subPropertyOf, involves);
    name.addProperty(RDF.type, RDF.Property);
    phone.addProperty(RDF.type, RDF.Property);

    involves.addProperty(RDFS.domain, movie);
    involves.addProperty(RDFS.range, staffMember);
    isDirectedBy.addProperty(RDFS.domain, movie);
    isDirectedBy.addProperty(RDFS.range, director);
    name.addProperty(RDFS.domain, staffMember);
    name.addProperty(RDFS.range, RDFS.Literal);
    phone.addProperty(RDFS.domain, staffMember);
    phone.addProperty(RDFS.range, RDFS.Literal);

    Resource pulpFiction = model.createResource(ex + "PulpFiction").addProperty(RDF.type, movie);
    Resource quentinTarantino =
        model
            .createResource(ex + "QuentinTarantino")
            .addProperty(RDF.type, director)
            .addProperty(name, "Quentin Tarantino")
            .addProperty(phone, "123-456-7890");

    pulpFiction.addProperty(isDirectedBy, quentinTarantino);

    Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
    InfModel infModel = ModelFactory.createInfModel(reasoner, model);

    StmtIterator iter = infModel.listStatements(quentinTarantino, null, (RDFNode) null);
    while (iter.hasNext()) {
      System.out.println(iter.nextStatement());
    }
  }
}
