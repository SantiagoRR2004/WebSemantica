package com.example.jena;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.util.iterator.ExtendedIterator;

public class Ejemplo10 {
    static final String inputFileName = "practica3/vc-db-1.rdf";
    
    public static void main (String args[]) {
        // 1. Crear un modelo vacío
        Model model = ModelFactory.createDefaultModel();

        // 2. Leer el archivo RDF
        RDFDataMgr.read(model, inputFileName);
        
        // 3. Obtener todos los statements con foaf:name
        StmtIterator allNames = model.listStatements(null, FOAF.name, (RDFNode) null);

        // create a bag
        Bag bag = model.createBag();
        // 4. Filtrar los que terminan en "Smith"
        ExtendedIterator<Statement> iter = allNames.filterKeep(stmt ->
            stmt.getObject().isLiteral() && stmt.getString().endsWith("Smith")
        );
          
        while (iter.hasNext()) {
            Statement stmt = iter.next();
            bag.add(stmt.getSubject());
        }
        model.write(System.out, "RDF/XML");
        System.out.println();

        // print out the members of the bag
        NodeIterator iter2 = bag.iterator();
        System.out.println("The bag contains:");
        while (iter2.hasNext()) {
            System.out.println("  " +
                ((Resource) iter2.next())
                                    .getRequiredProperty(FOAF.name)
                                    .getString());
        }
    }
}
