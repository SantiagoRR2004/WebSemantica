# Simpson

## [Part 1](./src/main/java/simpson/Part1.java)

We decided to scrape the [Simpsons Wiki](https://simpsons.fandom.com/) to extract information about characters from the Simpsons TV show. We only add the minimum information (`fam:hasProgenitor`, `fam:hasSpouse`, `fam:hasGender` and `foaf:age`) to create the RDF model, because more complex relationships will we added when we apply reasoning in Part 3.

## [Part 2](./src/main/java/simpson/Part2.java)

We add the necessary schema information to the RDF model to be able to apply reasoning in Part 3. In this Part we just followed the instructions in the assignment.

## [Part 3](./src/main/java/simpson/Part3.java)

Because there are a lot of family members, a lot of relationships can be inferred and this means we will explain them in batches.

### Basic Inferences

These are the ones that appear just by applying RDFS reasoning. There are some that will be added in every rdfs implementation, like `rdfs:Class rdfs:subClassOf rdfs:Resource` and other that are more specific to our model, like:

- `fam:hasGender rdfs:subPropertyOf fam:hasGender`. Every property will be `rdfs:subPropertyOf` of itself.
- `foaf:Person rdfs:subClassOf rdfs:Resource`. Every class will be `rdfs:subClassOf` of `rdfs:Resource`.

### [Progenitor Rules Inferences](./rules/progenitor.rules)
