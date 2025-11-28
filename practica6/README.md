# Enrich authors

## [Birthday](./queries/q1.sparql)

In this query, we retrieve the authors' birth dates from DBpedia.

## [DBpedia](./queries/q2.sparql)

This query gets all triples from DBpedia that have the authors as subject or object.

## [prop/direct-normalized](./queries/q3.sparql)

This tries to get from all services that are linked in DBpedia, the triples that the authors are subject or object. Most of the domains fail, but a few add new triples.
