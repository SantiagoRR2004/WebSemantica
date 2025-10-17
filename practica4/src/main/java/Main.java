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

        // add more istances:
        Resource bob = model.createResource(ex + "Bob", alumno);
        Resource ri = model.createResource(ex + "RecuperacionInformacion", asignaturaTeorica);
        jf.addProperty(ensena, ri); // jf ensena ri
        bob.addProperty(matriculadoEn, ri);
        bob.addProperty(esMentorDe, alicia); // bob esMentorDe alicia
        bob.addProperty(aprueba, ws);

        InfModel infModel2 = ModelFactory.createInfModel(reasoner, model);
        System.out.println("----- Statements inferred about Bob -----");
        System.out.println("----- Types: -----");
        StmtIterator iter2 = infModel2.listStatements(bob, RDF.type, (RDFNode) null);
        while (iter2.hasNext()) {
            System.out.println(iter2.nextStatement()); 
        }
        System.out.println("----- Properties: -----");
        StmtIterator props2 = infModel2.listStatements(bob, null, (RDFNode) null);
        while (props2.hasNext()) {
            System.out.println(props2.nextStatement());
        }

        // Exercise 7.3: add a wrong property
        bob.addProperty(ensena, ws);
        InfModel infModel3 = ModelFactory.createInfModel(reasoner, model);
        System.out.println("----- Statements inferred about Bob after adding a wrong property -----");
        System.out.println("----- Types: -----");
        StmtIterator iter3 = infModel3.listStatements(bob, RDF.type, (RDFNode) null);
        while (iter3.hasNext()) {
            System.out.println(iter3.nextStatement());
        }
        // makes bob a profesor by the wrong property added

        // Exercise 7.4: write infer model in Turtle format
        System.out.println("----- Model in Turtle format -----");
        infModel3.write(System.out, "TURTLE");

        // Exercise 7.5: print properties of Alice to check it inferences participaEn
        System.out.println("----- Statements inferred about Alicia -----");
        System.out.println("----- Properties: -----");
        StmtIterator props3 = infModel3.listStatements(alicia, null, (RDFNode) null);
        while (props3.hasNext()) {
            System.out.println(props3.nextStatement());
        }

    }
}