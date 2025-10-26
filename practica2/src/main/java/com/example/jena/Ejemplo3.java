package com.example.jena;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;

public class Ejemplo3 {
  // static final String inputFileName = "practica2/vc-db-1.rdf";
  static final String inputFileName = "practica2/personas.ttl";

  public static void main(String args[]) {
    // create an empty model
    Model model = ModelFactory.createDefaultModel();
    // Leer el archivo directamente en el modelo
    RDFDataMgr.read(model, inputFileName);

    // write it to standard out
    model.write(System.out);
  }
}
