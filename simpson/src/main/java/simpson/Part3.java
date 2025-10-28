package simpson;

import java.nio.file.Paths;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.shared.PrefixMapping;

import java.util.List;

public class Part3 {
  static final String inputFileName = Paths.get(System.getProperty("user.dir"), "family.ttl").toString();
  static final String progenitorRules = Paths.get(System.getProperty("user.dir"), "rules", "progenitor.rules").toString();

  public static void main(String[] args) {
    // Create an empty model
    Model model = ModelFactory.createDefaultModel();

    // Open the file
    RDFDataMgr.read(model, inputFileName);

    Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
    InfModel infModel = ModelFactory.createInfModel(reasoner, model);

    // Show the basic inferences
    System.out.println("----- Basic Inferences -----");
    Model deductions = infModel.getDeductionsModel();

    // Build a prefix mapping that includes standard RDF/RDFS/XSD/OWL prefixes
    // and any prefixes declared in the loaded model (from family.ttl).
    PrefixMapping pm = PrefixMapping.Factory.create();
    pm.setNsPrefixes(PrefixMapping.Standard);
    pm.setNsPrefixes(model);

    StmtIterator it = deductions.listStatements();
    while (it.hasNext()) {
      Statement stmt = it.next();

      // Add them to the original model
      model.add(stmt);

      String s = pm.shortForm(stmt.getSubject().getURI());

      String p = pm.shortForm(stmt.getPredicate().getURI());

      RDFNode obj = stmt.getObject();
      String o;
      if (obj.isResource()) {
        o = pm.shortForm(obj.asResource().getURI());
      } else {
        // Literal: print lexical form (Jena's toString includes datatype/lang)
        o = obj.toString();
      }

      printAligned(s, p, o, 80);
    }

    // Now the progenitor rules
    System.out.println("----- Progenitor Rules Inferences -----");
    List<Rule> rules = Rule.rulesFromURL(progenitorRules);
    Reasoner ruleReasoner = new GenericRuleReasoner(rules);

    infModel = ModelFactory.createInfModel(ruleReasoner, model);

    StmtIterator iter = infModel.getDeductionsModel().listStatements();
    while (iter.hasNext()) {
      Statement stmt = iter.next();

      // Add them to the original model
      model.add(stmt);

      String s = pm.shortForm(stmt.getSubject().getURI());

      String p = pm.shortForm(stmt.getPredicate().getURI());

      RDFNode obj = stmt.getObject();
      String o;
      if (obj.isResource()) {
        o = pm.shortForm(obj.asResource().getURI());
      } else {
        // Literal: print lexical form (Jena's toString includes datatype/lang)
        o = obj.toString();
      }

      printAligned(s, p, o, 80);
    }

  }

  static void printAligned(String s, String p, String o, int width) {
    int sWidth = s.length();
    int pWidth = p.length();
    int oWidth = o.length();

    // Calculate center position for `p`
    int center = width / 2;
    int pStart = Math.max(0, center - (pWidth / 2));

    // Right align `o` at end
    int oStart = Math.max(0, width - oWidth);

    // Build output with spaces
    StringBuilder line = new StringBuilder(" ".repeat(width));

    // Left align `s`
    if (sWidth <= width)
      line.replace(0, Math.min(sWidth, width), s);

    // Center align `p`
    if (pStart + pWidth <= width)
      line.replace(pStart, pStart + pWidth, p);

    // Right align `o`
    if (oStart + oWidth <= width)
      line.replace(oStart, oStart + oWidth, o);

    System.out.println(line.toString());
  }

}
