package com.example.jena;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.vocabulary.FOAF;


/** Ejemplo 5 - selecting the resources
 */
public class Ejemplo5 extends Object {
    
    static final String inputFileName = "practica3/vc-db-1.rdf";
    
    public static void main (String args[]) {
        // 1. Crear un modelo vacío
        Model model = ModelFactory.createDefaultModel();

        // 2. Leer el archivo RDF
        RDFDataMgr.read(model, inputFileName);

        // 3. Seleccionar todos los sujetos con la propiedad foaf:name
        ResIterator iter = model.listSubjectsWithProperty(FOAF.name);

        if (iter.hasNext()) {
            System.out.println("La base de conocimiento contiene las siguientes personas:");
            while (iter.hasNext()) {
                Resource person = iter.nextResource();
                String fullName = person.getRequiredProperty(FOAF.name).getString();
                System.out.println("  " + fullName);
            }
        } else {
            System.out.println("No se encontraron personas en la base de conocimiento");
        }         
    }
}
