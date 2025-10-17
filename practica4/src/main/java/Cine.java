import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.reasoner.Reasoner ;
import org.apache.jena.reasoner.ReasonerRegistry;

public class Cine {
    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();
        String ex = "http://example.org/cine#";

        // Define classes
        Resource staffMember = model.createResource(ex + "StaffMember")
                .addProperty(RDF.type, RDFS.Class);
        Resource movie = model.createResource(ex + "Movie")
                .addProperty(RDF.type, RDFS.Class);

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

        // Subproperty relationships
        involves.addProperty(RDF.type, RDF.Property);
        isDirectedBy.addProperty(RDFS.subPropertyOf, involves);
    }
}
