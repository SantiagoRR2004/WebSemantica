package practica.sparql;

import org.apache.jena.sparql.lang.SPARQLParser;

public class App {
    public static void main( String [ ] args ) {
        SparqlRunner runner = new SparqlRunner ("src/main/resources/europe.ttl" );

        // Consulta 1: Países cuyos nombres comienzan con la letra 'A'
        String q1 = """
                PREFIX ex: <http://example.org/europe#>
                SELECT ?country ?name
                WHERE {
                ?country a ex:Country ;
                ex:countryName ?name .
                FILTER(STRSTARTS(?name, "A"))
                }
                ORDER BY ?name
            """ ;
        runner.runQuery(q1);

        // Consulta 2: Paises cuyo nombre termina por  "a":
        String q2 = """
                PREFIX ex: <http://example.org/europe#>
                SELECT ?country ?name
                WHERE {
                ?country a ex:Country ;
                ex:countryName ?name .
                FILTER(STRENDS(?name, "a"))
                }
                ORDER BY ?name
            """ ;
        runner.runQuery(q2);

        // Consulta 3: Empiezan por "A" y terminan por "a"
        String q3 = """
                PREFIX ex: <http://example.org/europe#>
                SELECT ?country ?name
                WHERE {
                ?country a ex:Country ;
                ex:countryName ?name .
                FILTER(STRSTARTS(?name, "A") && STRENDS(?name, "a"))
                }
                ORDER BY ?name
            """ ;
        runner.runQuery(q3);

        // Consulta 4: Cuyo PIB per capita es mayor que 20.000
        String q4 = """
                PREFIX ex: <http://example.org/europe#>
                SELECT ?country ?gdp
                WHERE {
                ?country a ex:Country ;
                ex:gdpPerCapitaUSD ?gdp.
                FILTER(?gdp > 20000)
                }
                ORDER BY ?name
            """ ;
        runner.runQuery(q4);

        // Consulta 5:  PIB es mayor que 20 000 y su población es menor de 40 millones
        String q5 = """
                PREFIX ex: <http://example.org/europe#>
                SELECT ?country ?gdp ?p
                WHERE {
                ?country a ex:Country ;
                ex:gdpPerCapitaUSD ?gdp;
                ex:population ?p.
                FILTER(?gdp > 20000 && ?p < 40000000)
                }
                ORDER BY ?name
            """ ;
        runner.runQuery(q5);

        // Consulta 6: País con mayor PIB
        String q6 = """
                PREFIX ex: <http://example.org/europe#>
                SELECT ?country ?gdp
                WHERE {
                ?country a ex:Country ;
                ex:gdpPerCapitaUSD ?gdp.
                }
                ORDER BY DESC(?gdp)
                LIMIT 1
            """ ;
        runner.runQuery(q6);

        // Consulta 7: Calcular PIB medio
        String q7 = """
                PREFIX ex: <http://example.org/europe#>
                SELECT (avg(?gdp) AS ?avgGdp)
                WHERE {
                ?country a ex:Country ;
                ex:gdpPerCapitaUSD ?gdp.
                }
            """ ;
        runner.runQuery(q7);

        // Consulta 8: Países con PIB superior al PIB medio
        String q8 = """
                PREFIX ex: <http://example.org/europe#>
                SELECT ?country ?gdp
                WHERE {
                    {
                        SELECT (AVG(?g) AS ?avgGdp)
                        WHERE {
                            ?c a ex:Country ;
                            ex:gdpPerCapitaUSD ?g.
                        }
                    }
                    ?country a ex:Country ;
                    ex:gdpPerCapitaUSD ?gdp.
                    FILTER(?gdp > ?avgGdp)
                }
                ORDER BY DESC(?gdp)
            """ ;
        runner.runQuery(q8);

        // Consulta 9: Países con población similar a España (+- 30%) con mayor PIB.
        String q9 = """
                PREFIX ex: <http://example.org/europe#>
                SELECT ?country ?gdp ?p
                WHERE {
                    {
                        SELECT ?pSpain ?gdpSpain
                        WHERE {
                            ?c a ex:Country;
                            ex:countryName ?name;
                            ex:population ?pSpain;
                            ex:gdpPerCapitaUSD ?gdpSpain.
                            FILTER(?name = "Spain")
                        }
                    }
                    ?country a ex:Country ;
                    ex:gdpPerCapitaUSD ?gdp;
                    ex:population ?p.
                    FILTER(?p >= (?pSpain *0.7) && ?p <= (?pSpain *1.3) && ?gdp > ?gdpSpain)
                }
                ORDER BY DESC(?gdp)
                """;
        runner.runQuery(q9);

        // Consulta 10: Propiedad que indique el PIB en euros.
        // Entiendo que es con CONSTRUCT
    }
}
