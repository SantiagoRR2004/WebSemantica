package simpson;

import java.nio.file.Paths;
import java.util.List;
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

public class Part3 {
  static final String inputFileName =
      Paths.get(System.getProperty("user.dir"), "family.ttl").toString();
  static final String outputFileName =
      Paths.get(System.getProperty("user.dir"), "complete.ttl").toString();

  static final String progenitorRules =
      Paths.get(System.getProperty("user.dir"), "rules", "progenitor.rules").toString();
  static final String relatedRules =
      Paths.get(System.getProperty("user.dir"), "rules", "related.rules").toString();
  static final String siblingRules =
      Paths.get(System.getProperty("user.dir"), "rules", "sibling.rules").toString();

  static final int width = 110;
  static final int length = (width - 40) / 2;

  public static void main(String[] args) {
    // Create an empty model
    Model model = ModelFactory.createDefaultModel();

    // Open the file
    RDFDataMgr.read(model, inputFileName);

    Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
    InfModel infModel = ModelFactory.createInfModel(reasoner, model);

    // Show the basic inferences
    System.out.println("-".repeat(length * 2 + " Basic Inferences ".length()));
    System.out.println("-".repeat(length) + " Basic Inferences " + "-".repeat(length));
    System.out.println("-".repeat(length * 2 + " Basic Inferences ".length()));
    Model deductions = infModel.getDeductionsModel();

    StmtIterator it = deductions.listStatements();
    printIterator(it, infModel);

    // Now the related rules
    System.out.println("-".repeat(length * 2 + " Related Rules Inferences ".length()));
    System.out.println("-".repeat(length) + " Related Rules Inferences " + "-".repeat(length));
    System.out.println("-".repeat(length * 2 + " Related Rules Inferences ".length()));
    List<Rule> rules = Rule.rulesFromURL(relatedRules);
    Reasoner ruleReasoner = new GenericRuleReasoner(rules);

    infModel = ModelFactory.createInfModel(ruleReasoner, model);
    StmtIterator iter = infModel.getDeductionsModel().listStatements();
    printIterator(iter, infModel);

    // Now the progenitor rules
    System.out.println("-".repeat(length * 2 + " Progenitor Rules Inferences ".length()));
    System.out.println("-".repeat(length) + " Progenitor Rules Inferences " + "-".repeat(length));
    System.out.println("-".repeat(length * 2 + " Progenitor Rules Inferences ".length()));
    rules = Rule.rulesFromURL(progenitorRules);
    ruleReasoner = new GenericRuleReasoner(rules);

    infModel = ModelFactory.createInfModel(ruleReasoner, model);

    iter = infModel.getDeductionsModel().listStatements();
    printIterator(iter, infModel);

    // Now the sibling rules
    System.out.println("-".repeat(length * 2 + " Sibling Rules Inferences ".length()));
    System.out.println("-".repeat(length) + " Sibling Rules Inferences " + "-".repeat(length));
    System.out.println("-".repeat(length * 2 + " Sibling Rules Inferences ".length()));
    rules = Rule.rulesFromURL(siblingRules);
    ruleReasoner = new GenericRuleReasoner(rules);

    infModel = ModelFactory.createInfModel(ruleReasoner, model);
    iter = infModel.getDeductionsModel().listStatements();
    printIterator(iter, infModel);

    // Save the file
    try (java.io.FileOutputStream out = new java.io.FileOutputStream(outputFileName)) {
      RDFDataMgr.write(out, model, org.apache.jena.riot.Lang.TURTLE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static void printAligned(String s, String p, String o) {
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
    if (sWidth <= width) line.replace(0, Math.min(sWidth, width), s);

    // Center align `p`
    if (pStart + pWidth <= width) line.replace(pStart, pStart + pWidth, p);

    // Right align `o`
    if (oStart + oWidth <= width) line.replace(oStart, oStart + oWidth, o);

    System.out.println(line.toString());
  }

  static void printIterator(StmtIterator it, Model model) {

    while (it.hasNext()) {
      Statement stmt = it.next();

      // Add them to the original model
      model.add(stmt);

      String s = model.shortForm(stmt.getSubject().getURI());

      String p = model.shortForm(stmt.getPredicate().getURI());

      RDFNode obj = stmt.getObject();
      String o;
      if (obj.isResource()) {
        o = model.shortForm(obj.asResource().getURI());
      } else {
        // Literal: print lexical form (Jena's toString includes datatype/lang)
        o = obj.toString();
      }

      printAligned(s, p, o);
    }
  }
}
