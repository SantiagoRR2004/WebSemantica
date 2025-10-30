package simpson;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.XSD;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Part1 {
  static final String outputFileName =
      Paths.get(System.getProperty("user.dir"), "simpson.ttl").toString();

  // Dictionary of known Simpson characters and their ages
  private static final java.util.Map<String, Integer> KNOWN_AGES =
      new java.util.HashMap<String, Integer>() {
        {
          put("Homer_Simpson", 36);
          put("Marge_Simpson", 34);
          put("Bart_Simpson", 10);
          put("Lisa_Simpson", 8);
          put("Maggie_Simpson", 1);
        }
      };

  public static void main(String[] args) {
    Model model = ModelFactory.createDefaultModel();
    String familyNs = "http://www.esei.uvigo.es/ws/familia#";
    String foafNs = "http://xmlns.com/foaf/0.1/";
    String rdfNs = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    String rdfsNs = "http://www.w3.org/2000/01/rdf-schema#";
    String xsdNs = "http://www.w3.org/2001/XMLSchema#";
    String simNs = "http://www.esei.uvigo.es/ws/simpsons#";

    model.setNsPrefix("fam", familyNs);
    model.setNsPrefix("foaf", foafNs);
    model.setNsPrefix("rdf", rdfNs);
    model.setNsPrefix("rdfs", rdfsNs);
    model.setNsPrefix("xsd", xsdNs);
    model.setNsPrefix("sim", simNs);

    // Create classes
    Resource familyClass = model.createResource(familyNs + "Family");
    Resource genderClass = model.createResource(familyNs + "Gender");
    Resource maleClass = model.createResource(familyNs + "Masc");
    Resource femaleClass = model.createResource(familyNs + "Fem");
    Resource UnknownGenderClass = model.createResource(familyNs + "UnknownGender");

    // Create properties
    Property hasMemberFamily = model.createProperty(familyNs + "hasMemberFamily");
    Property hasSiblings = model.createProperty(familyNs + "hasSiblings");
    Property hasBrother = model.createProperty(familyNs + "hasBrother");
    Property hasSister = model.createProperty(familyNs + "hasSister");
    Property hasProgenitor = model.createProperty(familyNs + "hasProgenitor");
    Property hasFather = model.createProperty(familyNs + "hasFather");
    Property hasMother = model.createProperty(familyNs + "hasMother");
    Property hasSpouse = model.createProperty(familyNs + "hasSpouse");
    Property hasGender = model.createProperty(familyNs + "hasGender");
    Property isRelatedTo = model.createProperty(familyNs + "isRelatedTo");

    // Create family Simpson
    Resource simpsonFamily =
        model.createResource(simNs + "SimpsonFamily").addProperty(RDF.type, familyClass);

    List<String> list = new ArrayList<>();
    list.add("/wiki/Homer_Simpson");

    int i = 0;
    while (i < list.size()) {

      try {
        String url = "https://simpsons.fandom.com" + list.get(i);

        String resourceName = url.substring(url.lastIndexOf("/") + 1);
        System.out.println("Resource Name: " + resourceName.replace("_", " "));

        Resource person =
            model
                .createResource(
                    simNs + resourceName.replaceAll("[^A-Za-z0-9_-]", "").replace("_", ""))
                .addProperty(RDF.type, FOAF.Person)
                .addProperty(FOAF.name, resourceName.replace("_", " "));

        // Add the person to the Simpson family
        simpsonFamily.addProperty(hasMemberFamily, person);

        // Fetch the document
        Document doc = Jsoup.connect(url).get();

        // Sleep between 0.5 and 2 seconds
        Thread.sleep(ThreadLocalRandom.current().nextInt(500, 2001));

        // Extract the info box
        Element infobox = doc.select("aside.portable-infobox").first();

        // Gender
        Element genderElement = infobox.selectFirst("[data-source=sex]");
        String foundGender = genderElement != null ? genderElement.text() : "Unknown";
        if (foundGender.strip().equals("Gender Male ♂")) {
          model.add(person, hasGender, maleClass);
        } else if (foundGender.strip().equals("Gender Female ♀")) {
          model.add(person, hasGender, femaleClass);
        } else {
          System.out.println("Gender not found");
          model.add(person, hasGender, UnknownGenderClass);
        }

        // Age
        Element ageElement = infobox.select("[data-source=age]").first();
        int age = -1;
        if (ageElement != null) {
          // Extract text content (numbers and other text)
          String text = ageElement.text();
          // Use regex to find the first number
          Matcher matcher = Pattern.compile("\\d+").matcher(text);
          if (matcher.find()) {
            age = Integer.parseInt(matcher.group());
          }
        }

        // If age not found on webpage, check the dictionary
        if (age == -1 && KNOWN_AGES.containsKey(resourceName)) {
          age = KNOWN_AGES.get(resourceName);
          System.out.println("Using known age from dictionary: " + age);
        }

        person.addProperty(
            FOAF.age, model.createTypedLiteral(String.valueOf(age), XSD.xint.getURI()));

        // Get the relationships
        Element relativesElement = infobox.select("[data-source=relatives]").first();

        // The parents
        Elements parents = extractLinksFor(relativesElement, "Parents:");
        for (Element link : parents) {

          // Remove %22 and other similar
          String relativeUrl = URLDecoder.decode(link.attr("href"), StandardCharsets.UTF_8);

          Resource relative =
              model.createResource(
                  simNs
                      + relativeUrl
                          .substring(relativeUrl.lastIndexOf("/") + 1)
                          .replaceAll("[^A-Za-z0-9_-]", "")
                          .replace("_", ""));

          person.addProperty(hasProgenitor, relative);

          if (!list.contains(relativeUrl)) {
            list.add(relativeUrl);
          }
        }

        // Wives
        Elements wives = extractLinksFor(relativesElement, "Wife:");
        for (Element link : wives) {

          // Remove %22 and other similar
          String relativeUrl = URLDecoder.decode(link.attr("href"), StandardCharsets.UTF_8);

          Resource relative =
              model.createResource(
                  simNs
                      + relativeUrl
                          .substring(relativeUrl.lastIndexOf("/") + 1)
                          .replaceAll("[^A-Za-z0-9_-]", "")
                          .replace("_", ""));

          person.addProperty(hasSpouse, relative);

          if (!list.contains(relativeUrl)) {
            list.add(relativeUrl);
          }
        }

        // Husband
        Elements husbands = extractLinksFor(relativesElement, "Husband:");
        for (Element link : husbands) {

          // Remove %22 and other similar
          String relativeUrl = URLDecoder.decode(link.attr("href"), StandardCharsets.UTF_8);

          Resource relative =
              model.createResource(
                  simNs
                      + relativeUrl
                          .substring(relativeUrl.lastIndexOf("/") + 1)
                          .replaceAll("[^A-Za-z0-9_-]", "")
                          .replace("_", ""));

          person.addProperty(hasSpouse, relative);

          if (!list.contains(relativeUrl)) {
            list.add(relativeUrl);
          }
        }

        // Children
        Elements children = extractLinksFor(relativesElement, "Children:");
        for (Element link : children) {

          // Remove %22 and other similar
          String relativeUrl = URLDecoder.decode(link.attr("href"), StandardCharsets.UTF_8);

          // We do not add the child, we just add it to the list to be processed later
          if (!list.contains(relativeUrl)) {
            list.add(relativeUrl);
          }
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

      i++;
    }

    // Save the file
    try (java.io.FileOutputStream out = new java.io.FileOutputStream(outputFileName)) {
      RDFDataMgr.write(out, model, org.apache.jena.riot.Lang.TURTLE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Elements extractLinksFor(Element relativesElement, String label) {
    // Find the <b> element that contains the label
    Element labelElement = relativesElement.select("b:matchesOwn(^" + label + "$)").first();
    if (labelElement != null) {
      // Get all links until the next <b> or <br>
      StringBuilder selector = new StringBuilder();
      Element current = labelElement.nextElementSibling();
      while (current != null && !current.tagName().equals("b") && !current.tagName().equals("br")) {
        selector.append(current.outerHtml());
        current = current.nextElementSibling();
      }

      // Parse the collected HTML fragment
      Document fragment = Jsoup.parse(selector.toString());
      Elements links = fragment.select("a[href]");

      return links;
    }

    return new Elements();
  }
}
