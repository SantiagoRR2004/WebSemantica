# Enrich authors

## [Birthday 4.1](./queries/q1.sparql)

In this query, we retrieve the authors' birth dates and place from DBpedia with the label of both properties.

## [Add more properties 4.2](./queries/q2.sparql)

In this query, we retrieve more properties from DBpedia with the label of each property. We selected the following properties:
wd:P21  rdfs:label  "sexo o género"@es .
wd:P1477  rdfs:label  "nombre de nacimiento"@es
wd:P2671  rdfs:label  "identificador Google Knowledge Graph"@es .
wd:P27  rdfs:label  "país de nacionalidad"@es .
schema:description
        rdfs:label  "<http://schema.org/description>" .

## [prop/direct-normalized](./queries/q4.sparql)

This tries to get from all services that are linked in DBpedia, the triples that the authors are subject or object. Most of the domains fail, but a few add new triples.

## [DBpedia (no `statements`)](./queries/q5.sparql)

This query gets all triples from DBpedia that have the authors as subject or object that do not start `http://www.wikidata.org/entity/statement/`.

We also extract the labels from the properties to find 5 which could be interesting. These 5 were the ones we used in exercise 4.2.

## [DBpedia (`statements`)](./queries/q6.sparql)

This query gets all triples from DBpedia that have the authors as subject or object and fixes the ones that start with `http://www.wikidata.org/entity/statement/`.

## [IMDb ID](./queries/q7.sparql)

In this query, we find all subjects that have a IMDb ID and we add the triple. The problem is that we can't do anything with those IDs because [IMDB](https://www.imdb.com/) does not provide a IMDb SPARQL endpoint.
