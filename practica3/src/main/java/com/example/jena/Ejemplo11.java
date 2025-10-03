package com.example.jena;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

public class Ejemplo11 extends Object {
    
    public static void main (String args[]) {
        // create an empty graph
        Model model = ModelFactory.createDefaultModel();

        // create the resource
        Resource r = model.createResource();                                     

        // add the property
        r.addProperty(RDFS.label, model.createLiteral("cat", "en"))
         .addProperty(RDFS.label, model.createLiteral("chat", "fr"))
         .addProperty(RDFS.label, model.createLiteral("gato", "es"));
        
        // Literal con XMLLiteral
        Literal lit = model.createTypedLiteral("<em>chat</em>", RDF.dtXMLLiteral);

        // Asignarlo al recurso
        r.addProperty(RDFS.label, lit);
            
        // write out the graph
        model.write(System.out, "RDF/XML");
        System.out.println();
      
        // create an empty graph
        model = ModelFactory.createDefaultModel();

        // create the resource
        r = model.createResource();                                     

        // add the property
        r.addProperty(RDFS.label, "11")
         .addLiteral(RDFS.label, 11);
      
        // write out the graph
        model.write( System.out, "N-TRIPLE");
      }
}