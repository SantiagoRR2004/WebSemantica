package github;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.codec.Resources;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;

import java.net.URI;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) {
        // Open the GitHubGraphComplete.json file
        String jsonFilePath = "GitHubGraphComplete.json";

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Load JSON file
            JsonNode root = mapper.readTree(new File(jsonFilePath));

            // Create RDF model
            Model model = ModelFactory.createDefaultModel();

            // Convert JSON to RDF
            Iterator<Map.Entry<String, JsonNode>> fields = root.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String key = entry.getKey();

                Resource o = model.createResource(key);

                JsonNode userNode = entry.getValue();
                Iterator<Map.Entry<String, JsonNode>> userFields = userNode.fields();

                while (userFields.hasNext()) {
                    Map.Entry<String, JsonNode> userEntry = userFields.next();
                    String prop = userEntry.getKey();

                    // Check if the value is an url
                    if (userEntry.getValue().isArray()) {

                        for (JsonNode arrayItem : userEntry.getValue()) {
                            String value = arrayItem.asText();
                            if (isValidURL(value)) {
                                o.addProperty(model.createProperty("http://example.org/" + prop),
                                        model.createResource(value));
                            }
                        }
                    } else {
                        String value = userEntry.getValue().asText();
                        if (isValidURL(value)) {
                            o.addProperty(model.createProperty("http://example.org/" + prop),
                                    model.createResource(value));
                        } else {
                            o.addProperty(model.createProperty("http://example.org/" + prop), value);
                        }
                    }
                }
            }

            try (java.io.FileOutputStream out = new java.io.FileOutputStream("GitHub.ttl")) {
                RDFDataMgr.write(out, model, org.apache.jena.riot.Lang.TURTLE);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static boolean isValidURL(String urlString) {
        try {
            URI uri = new URI(urlString); // Validate URI syntax
            uri.toURL(); // Convert to URL (throws MalformedURLException if invalid)
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}