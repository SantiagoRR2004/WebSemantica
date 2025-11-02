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

### [Related Rules Inferences](./rules/related.rules)

Because to be related to someone, you just need to be in the same family, this rule infers that every character is related to all other characters because we only have one family. This is an example:

- `sim:TrixieStemple fam:isRelatedTo sim:WainwrightMontgomeryBurns`

### [Progenitor Rules Inferences](./rules/progenitor.rules)

These are 2 bidirectional rules that say that if you have a male progenitor, that person is your father, and if you have a female progenitor, that person is your mother. Examples of these inferences are:

- `sim:BartSimpson fam:hasFather sim:HomerSimpson`
- `sim:BartSimpson fam:hasMother sim:MargeSimpson`
- `sim:HomerSimpson fam:hasFather sim:AbeSimpsonII`
- `sim:HomerSimpson fam:hasMother sim:MonaSimpson`

### [Sibling Rules Inferences](./rules/sibling.rules)

These rules infer sibling relationships based on shared progenitors. If two individuals share the same father or mother, they are inferred to be siblings. If a sibling is male, they are inferred to be a brother; if female, a sister. Examples of these inferences are:

- `sim:MaggieSimpson fam:hasBrother sim:BartSimpson`
- `sim:MaggieSimpson fam:hasSister sim:LisaSimpson`
- `sim:MaggieSimpson fam:hasSiblings sim:LisaSimpson`
- `sim:MaggieSimpson fam:hasSiblings sim:BartSimpson`
- `sim:LisaSimpson fam:hasBrother sim:BartSimpson`
- `sim:LisaSimpson fam:hasSister sim:MaggieSimpson`
- `sim:LisaSimpson fam:hasSiblings sim:MaggieSimpson`
- `sim:LisaSimpson fam:hasSiblings sim:BartSimpson`
- `sim:BartSimpson fam:hasSister sim:MaggieSimpson`
- `sim:BartSimpson fam:hasSister sim:LisaSimpson`
- `sim:BartSimpson fam:hasSiblings sim:MaggieSimpson`
- `sim:BartSimpson fam:hasSiblings sim:LisaSimpson`

### [Grandparents Rules Inferences](./rules/grandparent.rules)

These rules infer grandparent relationships based on progenitor relationships. If an individual has a progenitor who also has a progenitor, the latter is inferred to be the grandparent of the former. Additionally, if the grandparent is male, they are inferred to be a grandfather; if female, a grandmother. Examples of these inferences are:

- `sim:BartSimpson fam:hasGrandfather sim:AbeSimpsonII`
- `sim:BartSimpson fam:hasGrandfather sim:ClancyBouvier`
- `sim:BartSimpson fam:hasGrandmother sim:JacquelineBouvier`
- `sim:BartSimpson fam:hasGrandmother sim:MonaSimpson`
- `sim:BartSimpson fam:hasGrandparent sim:ClancyBouvier`
- `sim:BartSimpson fam:hasGrandparent sim:AbeSimpsonII`
- `sim:BartSimpson fam:hasGrandparent sim:MonaSimpson`
- `sim:BartSimpson fam:hasGrandparent sim:JacquelineBouvier`

### [Uncle and Aunt Rules Inferences](./rules/uncle.rules)

These rules infer uncle and aunt relationships based on sibling and progenitor relationships. If an individual's progenitor has a sibling, that sibling is inferred to be the uncle or aunt of the individual, depending on the sibling's gender. Examples of these inferences are:

- `sim:MaggieSimpson fam:hasAunt sim:SelmaBouvier`
- `sim:MaggieSimpson fam:hasAunt sim:PattyBouvier`
- `sim:BartSimpson fam:hasAunt sim:SelmaBouvier`
- `sim:BartSimpson fam:hasAunt sim:PattyBouvier`
- `sim:LisaSimpson fam:hasAunt sim:SelmaBouvier`
- `sim:LisaSimpson fam:hasAunt sim:PattyBouvier`
