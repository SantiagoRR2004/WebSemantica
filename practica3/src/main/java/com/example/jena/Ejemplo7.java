package com.example.jena;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.vocabulary.FOAF;

public class Ejemplo7 extends Object {
    
    static final String inputFileName = "practica3/vc-db-1.rdf";
    
    public static void main (String args[]) {
        // 1. Crear un modelo vacío
        Model model = ModelFactory.createDefaultModel();

        // 2. Leer el archivo RDF
        RDFDataMgr.read(model, inputFileName);
        
        // 3. Obtener todos los recursos con FOAF.name
        ResIterator iter = model.listResourcesWithProperty(FOAF.name);
        if (iter.hasNext()) {
            System.out.println("The knowledge database contains foaf:name for:");
            while (iter.hasNext()) {
                System.out.println("  " + iter.nextResource()
                                              .getRequiredProperty(FOAF.name)
                                              .getString() );
            }
        } else {
            System.out.println("No foaf were found in the knowledge database");
        }         
    }
}
