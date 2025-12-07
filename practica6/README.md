# Enrich authors

In this practice, we enrich the information about a set of authors stored in a local RDF graph using data from [WikiData](https://www.wikidata.org/) and [DBpedia](https://www.dbpedia.org/) SPARQL endpoints.

There are several queries to perform the enrichment, each one stored in the [`queries`](./queries/) folder.

Use [this script](src/main/java/Main.java) to execute all the queries in order.

For each query, the resulting triples are added to the a file with the name `qX.ttl`, where `X` is the number of the query in the [`outputs`](./outputs/) folder.

The file authors-enriched.ttl contains all the triples resulting from executing all the queries.

## [Birthday 4.1](./queries/q1.sparql)

In this query, we retrieve the authors' birth dates and place from WikiData with the label of both properties.

## [Add more properties 4.2](./queries/q2.sparql)

In this query, we retrieve more properties from WikiData with the label of each property. We selected the following properties:

- wd:P21  rdfs:label  "sexo o género"@es .
- wd:P1477  rdfs:label  "nombre de nacimiento"@es
- wd:P106  rdfs:label  "ocupación"@es .
- wd:P27  rdfs:label  "país de nacionalidad"@es .
- schema:description rdfs:label  "<http://schema.org/description>" .

Additionally, we retrieve the labels of the properties used to facilitate understanding of the relationships.

## [Enrich with objects and their properties 4.3](./queries/q3.sparql)

In this query, we retrieve 4 relationships for each author that point to objects (not literals) from WikiData. The selected properties are:

- wd:P21  rdfs:label  "sexo o género"@es .
- wd:P27  rdfs:label  "país de nacionalidad"@es .
- wd:P19  rdfs:label  "lugar de nacimiento"@es .
- wd:P1412  rdfs:label  "lenguas habladas, escritas o signadas"@es .

For each of these objects, we also extract their properties:

- rdfs:label - the object's label in Spanish
- schema:description - the object's description in Spanish

Additionally, we retrieve the labels of the properties used to facilitate understanding of the relationships.

## [Information from DBpedia 4.4](./queries/q4.sparql)

This query retrieves the DBpedia IRI for each author using the `owl:sameAs` property from DBpedia with the ID of wikidata.DBpedia IRI is used to get the description, wikipedia links and the films produced by the author from DBpedia.

## [WikiData (no `statements`)](./queries/q5.sparql)

This query gets all triples from WikiData that have the authors as subject or object that do not start `http://www.wikidata.org/entity/statement/`.

We also extract the labels from the properties to find 5 which could be interesting. These 5 were the ones we used in exercise 4.2.

## [WikiData (`statements`)](./queries/q6.sparql)

This query gets all triples from WikiData that have the authors as subject or object and fixes the ones that start with `http://www.wikidata.org/entity/statement/`.

## [IMDb ID](./queries/q7.sparql)

In this query, we find all subjects that have a IMDb ID and we add the triple. The problem is that we can't do anything with those IDs because [IMDB](https://www.imdb.com/) does not provide a IMDb SPARQL endpoint.

## [prop/direct-normalized](./queries/q8.sparql)

This tries to get from all services that are linked in DBpedia, the triples that the authors are subject or object. Most of the domains fail, but a few add new triples.
