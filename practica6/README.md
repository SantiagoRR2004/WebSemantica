# Enrich authors

## [Birthday](./queries/q1.sparql)

In this query, we retrieve the authors' birth dates from DBpedia.

## [DBpedia (no `statements`)](./queries/q2.sparql)

This query gets all triples from DBpedia that have the authors as subject or object that do not start `http://www.wikidata.org/entity/statement/`.

## [DBpedia (`statements`)](./queries/q3.sparql)

This query gets all triples from DBpedia that have the authors as subject or object and fixes the ones that start with `http://www.wikidata.org/entity/statement/`.

## [prop/direct-normalized](./queries/q4.sparql)

This tries to get from all services that are linked in DBpedia, the triples that the authors are subject or object. Most of the domains fail, but a few add new triples.
