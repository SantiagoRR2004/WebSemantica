package com.example.jena;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.riot.RDFDataMgr;

public class Ejercicio4 {
    public static void main(String[] args) {
        // Crear un modelo RDF
        Model model = ModelFactory.createDefaultModel();

        // Crear un recurso
        Resource alice = model.createResource("http://example.org/persona/Alice");

        alice.addProperty(FOAF.age, "23");

        model.write(System.out, "TURTLE");

        // 4.2
        alice.addProperty(FOAF.name, "Alice")
            .addProperty(FOAF.mbox, "<mailto:alice@example.org>");

        // Serialize in RDF/XML
        model.write(System.out, "RDF/XML");

        // 4.3
        Resource bob = model.createResource("http://example.org/persona/Bob");
        bob.addProperty(FOAF.knows, alice)
            .addProperty(FOAF.age, "30");

        model.write(System.out, "TURTLE");

        // 4.4
        // Create property height
        Property height = model.createProperty("http://example.org/property/height");
        alice.addLiteral(height, 1.65);

        //Create property birthDate
        Property birthDate = model.createProperty("http://example.org/property/birthDate");
        bob.addProperty(birthDate, "1993-05-15");

        model.write(System.out, "TURTLE");

        // 4.5
        Resource charlie = model.createResource("http://example.org/persona/Charlie");
        charlie.addProperty(FOAF.age, "28");

        StmtIterator iter = model.listStatements();

        // Consult all resource with property foaf:age
        System.out.println("Resources with foaf:age property:");
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement(); 
            Resource  subject   = stmt.getSubject(); 
            if (subject.hasProperty(FOAF.age)) {
                System.out.println(subject.getURI() + " - " + subject.getProperty(FOAF.age).getObject());
            }
        }
        // 4.6
        alice.addProperty(FOAF.knows, bob);
        charlie.addProperty(FOAF.knows, alice);

        model.write(System.out, "TURTLE");
        System.out.println("Alice knows:");        
        
        // 4.8        
        Resource address = model.createResource("http://example.org/Address");
        Property street = model.createProperty("http://example.org/street");
        Property city = model.createProperty("http://example.org/city");
        Property addressProp = model.createProperty("http://example.org/address");

        address.addProperty(street, "Progreso")
               .addProperty(city, "Ourense");

        alice.addProperty(addressProp, address);

        // 4.7
        try (java.io.FileOutputStream out = new java.io.FileOutputStream("personas.ttl")) {
            RDFDataMgr.write(out, model, org.apache.jena.riot.Lang.TURTLE);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }
}