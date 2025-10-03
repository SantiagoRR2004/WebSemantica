package com.example.jena;

import java.nio.file.Paths;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.vocabulary.FOAF;

/** Ejemplo 4 navegando a través de un modelo
 */
public class Ejemplo4 extends Object {
    
    // Calculate absolute path dynamically
    static final String inputFileName = Paths.get(System.getProperty("user.dir"), "vc-db-1.rdf").toString();
    static final String aliceSmithURI = "http://example.org/AliceSmith";
    
    public static void main (String args[]) {
        // 1. Crear un modelo vacío
        Model model = ModelFactory.createDefaultModel();

        // 2. Leer el archivo RDF
        RDFDataMgr.read(model, inputFileName);

        // 3. Recuperar el recurso Alice Smith
        Resource resAlice = model.getResource(aliceSmithURI);

        // 4. Predicado structuredName
        Property structuredName = model.createProperty("http://example.org/structuredName");

        // 5. Casos
        // --- Caso A: getRequiredProperty ---
        try {
            Resource structuredNode = resAlice.getRequiredProperty(structuredName).getResource();
            System.out.println("Caso A: [getRequiredProperty] La propiedad existe en el recurso");

            // Obtener firstName y familyName
            String firstName = structuredNode.getRequiredProperty(FOAF.firstName).getString();
            String familyName = structuredNode.getRequiredProperty(FOAF.familyName).getString();
            System.out.println("Caso A: Alice's structured name: " + firstName + " " + familyName);

        } catch (Exception e) {
            System.out.println("[getRequiredProperty] La propiedad no existe en el recurso");
        }

        // --- Caso B: getProperty y verificación ---
        Statement stmt = resAlice.getProperty(structuredName);
        if (stmt != null) {
            RDFNode node = stmt.getObject();
            if (node.isResource()) {
                Resource structuredNode = node.asResource();
                System.out.println("Caso B: [getProperty] El objeto es un recurso");
                StmtIterator props = structuredNode.listProperties();
                System.out.println("Propiedades de structuredNode:");
                while (props.hasNext()) {
                    Statement stmt2 = props.nextStatement();
                    Property p = stmt2.getPredicate();
                    RDFNode o = stmt2.getObject();

                    System.out.print("\tPredicado: " + p.getURI() + " - ");

                    if (o.isLiteral()) {
                        // Caso literal
                        System.out.println("\t\tLiteral: " + o.asLiteral().getString());
                    } else if (o.isResource()) {
                        // Caso recurso (otro nodo o URI)
                        Resource r = o.asResource();
                        if (r.isAnon()) {
                            System.out.println("\t\tNodo vacío (blank node)");
                        } else {
                            System.out.println("\t\tRecurso: " + r.getURI());
                        }
                    }
                }
                System.out.println("Caso B: Alice's structured name mostrado");
            } else {
                System.out.println("[getProperty] El objeto no es un recurso");
            }
        } else {
            System.out.println("[getProperty] La propiedad no existe en el recurso");
        }

        // --- Caso C: propiedad con múltiples valores ---
        StmtIterator iter = resAlice.listProperties(structuredName);
        System.out.println("Caso C: [listProperties] La propiedad puede tener múltiples valores");
        while (iter.hasNext()) {
            Resource structuredNodeMultiple = iter.nextStatement().getResource();
            // Puedo obtener directamente firstName y familyName
            String firstName = structuredNodeMultiple.getRequiredProperty(FOAF.firstName).getString();
            String familyName = structuredNodeMultiple.getRequiredProperty(FOAF.familyName).getString();
            System.out.println("Caso C: Alice's structured name: " + firstName + " " + familyName);
            
            // Puedo recorrer todas sus propiedades
            StmtIterator props = structuredNodeMultiple.listProperties();
            System.out.println("Propiedades de structuredNodeMultiple:");
            while (props.hasNext()) {
                Statement stmt2 = props.nextStatement();
                Property p = stmt2.getPredicate();
                RDFNode o = stmt2.getObject();

                System.out.print("\tPredicado: " + p.getURI() + " - ");

                if (o.isLiteral()) {
                    // Caso literal
                    System.out.println("\t\tLiteral: " + o.asLiteral().getString());
                } else if (o.isResource()) {
                    // Caso recurso (otro nodo o URI)
                    Resource r = o.asResource();
                    if (r.isAnon()) {
                        System.out.println("\t\tNodo vacío (blank node)");
                    } else {
                        System.out.println("\t\tRecurso: " + r.getURI());
                    }
                }
            }
            System.out.println("Caso C: Alice's structured name mostrado");
        }

        // 6. --- Añadir apodos ---
        resAlice.addProperty(FOAF.nick, "Smithy")
                .addProperty(FOAF.nick, "Adman");

        // 6. --- Listar apodos ---
        StmtIterator iter2 = resAlice.listProperties(FOAF.nick);
        System.out.println("Nicknames:");
        while (iter2.hasNext()) {
            RDFNode nickNode = iter2.nextStatement().getObject();
            System.out.println("  " + nickNode.toString());
        }
    }


}