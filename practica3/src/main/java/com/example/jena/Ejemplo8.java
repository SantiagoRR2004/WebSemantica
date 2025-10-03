package com.example.jena;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;

public class Ejemplo8 extends Object {
    
    static final String inputFileName1 = "practica3/vc-db-3.rdf";    
    static final String inputFileName2 = "practica3/vc-db-4.rdf";
    
    public static void main (String args[]) {
        // 1. Crear dos modelos vacíos
        Model model1 = ModelFactory.createDefaultModel();
        Model model2 = ModelFactory.createDefaultModel();

        // 2. Leer los archivos RDF
        RDFDataMgr.read(model1, inputFileName1);
        RDFDataMgr.read(model2, inputFileName2);
        
        // merge the graphs
        Model model = model1.union(model2);
        
        // print the graph as RDF/XML
        model.write(System.out, "RDF/XML-ABBREV");
        System.out.println();
    }
}