import org.apache.jena.base.Sys;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.reasoner.Reasoner ;
import org.apache.jena.reasoner.ReasonerRegistry;

public class Main {
    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();
        String ex = "http://example.org/universidad#";

        // Define classes
        Resource persona = model.createResource(ex + "Persona")
                .addProperty(RDF.type, RDFS.Class);
        Resource curso = model.createResource(ex + "Curso")
                .addProperty(RDF.type, RDFS.Class);

        // Define subclasses
        Resource miembroEquipoAcademico = model.createResource(ex + "MiembroEquipoAcademico");
        Resource profesor = model.createResource(ex + "Profesor");
        Resource alumno = model.createResource(ex + "Alumno");
        Resource alumnoGrado = model.createResource(ex + "AlumnoGrado");
        Resource alumnoPosgrado = model.createResource(ex + "AlumnoPosgrado");
        Resource asignatura = model.createResource(ex + "Asignatura");
        Resource asignaturaTeorica = model.createResource(ex + "Teorica");
        Resource asignaturaPractica = model.createResource(ex + "Practica");

        // Subclasses
        model.add(miembroEquipoAcademico, RDFS.subClassOf, persona);
        model.add(profesor, RDFS.subClassOf, miembroEquipoAcademico);
        model.add(alumno, RDFS.subClassOf, persona);
        model.add(alumnoGrado, RDFS.subClassOf, alumno);
        model.add(alumnoPosgrado, RDFS.subClassOf, alumno);
        model.add(asignatura, RDFS.subClassOf, curso);
        model.add(asignaturaTeorica, RDFS.subClassOf, asignatura);
        model.add(asignaturaPractica, RDFS.subClassOf, asignatura);

        // Subclass relationships
        // Other way apart from the model.add() method
        /*   
        miembroEquipoAcademico.addProperty(RDFS.subClassOf, persona);
        profesor.addProperty(RDFS.subClassOf, miembroEquipoAcademico);
        alumno.addProperty(RDFS.subClassOf, persona);
        alumnoGrado.addProperty(RDFS.subClassOf, alumno);
        alumnoPosgrado.addProperty(RDFS.subClassOf, alumno);
        asignatura.addProperty(RDFS.subClassOf, curso);
        asignaturaTeorica.addProperty(RDFS.subClassOf, asignatura);
        asignaturaPractica.addProperty(RDFS.subClassOf, asignatura);
        */

        // Create properties
        Property participaEn = model.createProperty(ex + "participaEn");
        Property esMentorDe = model.createProperty(ex + "esMentorDe");
        Property ensena = model.createProperty(ex + "ensena");
        Property evalua = model.createProperty(ex + "evalua");
        Property matriculadoEn = model.createProperty(ex + "matriculadoEn");
        Property aprueba = model.createProperty(ex + "aprueba");

        // Subproperty relationships
        model.add(participaEn, RDF.type, RDF.Property);
        model.add(esMentorDe, RDF.type, RDF.Property);
        model.add(ensena, RDFS.subPropertyOf, participaEn);
        model.add(evalua, RDFS.subPropertyOf, participaEn);
        model.add(matriculadoEn, RDFS.subPropertyOf, participaEn);
        model.add(aprueba, RDFS.subPropertyOf, participaEn);

        // The other way apart from the model.add() method
        /*
        participaEn.addProperty(RDF.type, RDF.Property);
        esMentorDe.addProperty(RDF.type, RDF.Property);
        ensena.addProperty(RDF.type, participaEn);
        evalua.addProperty(RDF.type, participaEn);
        matriculadoEn.addProperty(RDF.type, participaEn);
        aprueba.addProperty(RDF.type, participaEn);
        */

        participaEn.addProperty(RDFS.domain, persona);
        participaEn.addProperty(RDFS.range, RDFS.Class); //TODO: It should be "curso" I think.
        ensena.addProperty(RDFS.domain, profesor);
        ensena.addProperty(RDFS.range, asignatura);
        evalua.addProperty(RDFS.domain, profesor);
        evalua.addProperty(RDFS.range, alumno);
        matriculadoEn.addProperty(RDFS.domain, alumno);
        matriculadoEn.addProperty(RDFS.range, asignatura);
        aprueba.addProperty(RDFS.domain, alumno);
        aprueba.addProperty(RDFS.range, asignatura);
        esMentorDe.addProperty(RDFS.domain, persona);
        esMentorDe.addProperty(RDFS.range, alumno);

        // Instancias
        Resource mr = model.createResource(ex + "MiguelR", profesor);
        Resource jf = model.createResource(ex + "JuanF", profesor);
        Resource alicia = model.createResource(ex + "Alicia", alumnoGrado);
        Resource ws = model.createResource(ex + "WebSemantica", asignatura);
        Resource cuarto = model.createResource(ex + "4cursoGRIA", curso);

        mr.addProperty(ensena, ws); // mr ensena ws
        mr.addProperty(evalua, alicia); // mr evalua alicia
        alicia.addProperty(matriculadoEn, ws);
        alicia.addProperty(aprueba, ws);

        Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);

        System.out.println("----- Statements inferred about MiguelR -----");
        System.out.println("----- Types: -----");
        // Consultar tipos de mr
        StmtIterator iter = infModel.listStatements(mr, RDF.type, (RDFNode) null);
        while (iter.hasNext()) {
            System.out.println(iter.nextStatement());
        }
        System.out.println("----- Properties: -----");
        // Consultar propiedades de mr
        StmtIterator props = infModel.listStatements(mr, null, (RDFNode) null);
        while (props.hasNext()) {
            System.out.println(props.nextStatement());
        }

    }
}