package com.example.jena;

import java.nio.file.Paths;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.util.iterator.ExtendedIterator;



/** Ejemplo 6 - método de demostración de selectores
 */
public class Ejemplo6 extends Object {

    // Calculate absolute path dynamically
    static final String inputFileName = Paths.get(System.getProperty("user.dir"), "vc-db-1.rdf").toString();
    
    public static void main (String args[]) {
        // 1. Crear un modelo vacío
        Model model = ModelFactory.createDefaultModel();

        // 2. Leer el archivo RDF
        RDFDataMgr.read(model, inputFileName);
        
        // 3. Obtener todos los statements con foaf:name
        StmtIterator allNames = model.listStatements(null, FOAF.name, (RDFNode) null);

        // 4. Filtrar los que terminan en "Smith"
        ExtendedIterator<Statement> smiths = allNames.filterKeep(stmt ->
            stmt.getObject().isLiteral() && stmt.getString().endsWith("Smith")
        );

        if (smiths.hasNext()) {
            System.out.println("Personas cuyos nombres terminan en 'Smith':");           
            while (smiths.hasNext()) {
                Statement stmt = smiths.next();
                System.out.println("  " + stmt.getString());
            }
        } else {
            System.out.println("No se encontraron nombres que terminen en 'Smith'");
        }
    }
}