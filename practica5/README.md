# Europe

## Mostrar países cuyo nombre empieza por “A”

```text
--------------------------
| country    | name      |
==========================
| ex:Albania | "Albania" |
| ex:Andorra | "Andorra" |
| ex:Armenia | "Armenia" |
| ex:Austria | "Austria" |
--------------------------
```

## Paises cuyo nombre termina por  "a"

```text
------------------------------------------------------
| country                 | name                     |
======================================================
| ex:Albania              | "Albania"                |
| ex:Andorra              | "Andorra"                |
| ex:Armenia              | "Armenia"                |
| ex:Austria              | "Austria"                |
| ex:BosniaAndHerzegovina | "Bosnia and Herzegovina" |
| ex:Bulgaria             | "Bulgaria"               |
| ex:Croatia              | "Croatia"                |
| ex:Estonia              | "Estonia"                |
| ex:Georgia              | "Georgia"                |
| ex:Latvia               | "Latvia"                 |
| ex:Lithuania            | "Lithuania"              |
| ex:Malta                | "Malta"                  |
| ex:Moldova              | "Moldova"                |
| ex:NorthMacedonia       | "North Macedonia"        |
| ex:Romania              | "Romania"                |
| ex:Russia               | "Russia"                 |
| ex:Serbia               | "Serbia"                 |
| ex:Slovakia             | "Slovakia"               |
| ex:Slovenia             | "Slovenia"               |
------------------------------------------------------
```

## Empiezan por "A" y terminan por "a"

```text
--------------------------
| country    | name      |
==========================
| ex:Albania | "Albania" |
| ex:Andorra | "Andorra" |
| ex:Armenia | "Armenia" |
| ex:Austria | "Austria" |
--------------------------
```

## Cuyo PIB per capita es mayor que 20.000

```text
-----------------------------
| country          | gdp    |
=============================
| ex:Andorra       | 42200  |
| ex:Austria       | 55900  |
| ex:Belgium       | 58400  |
| ex:Cyprus        | 30200  |
| ex:CzechRepublic | 26800  |
| ex:Denmark       | 67500  |
| ex:Estonia       | 28500  |
| ex:Finland       | 53400  |
| ex:France        | 43700  |
| ex:Germany       | 51600  |
| ex:Greece        | 22600  |
| ex:Iceland       | 59100  |
| ex:Ireland       | 100200 |
| ex:Italy         | 35500  |
| ex:Latvia        | 20500  |
| ex:Liechtenstein | 180000 |
| ex:Lithuania     | 23800  |
| ex:Luxembourg    | 135700 |
| ex:Malta         | 32700  |
| ex:Monaco        | 173000 |
| ex:Netherlands   | 57400  |
| ex:Norway        | 89200  |
| ex:Portugal      | 25300  |
| ex:SanMarino     | 51800  |
| ex:Slovakia      | 20900  |
| ex:Slovenia      | 28400  |
| ex:Spain         | 34100  |
| ex:Sweden        | 59800  |
| ex:Switzerland   | 88400  |
| ex:UnitedKingdom | 46900  |
-----------------------------
```

## PIB es mayor que 20 000 y su población es menor de 40 millones

```text
----------------------------------------
| country          | gdp    | p        |
========================================
| ex:Andorra       | 42200  | 79000    |
| ex:Austria       | 55900  | 9006000  |
| ex:Belgium       | 58400  | 11590000 |
| ex:Cyprus        | 30200  | 1220000  |
| ex:CzechRepublic | 26800  | 10500000 |
| ex:Denmark       | 67500  | 5830000  |
| ex:Estonia       | 28500  | 1330000  |
| ex:Finland       | 53400  | 5540000  |
| ex:Greece        | 22600  | 10700000 |
| ex:Iceland       | 59100  | 372000   |
| ex:Ireland       | 100200 | 5030000  |
| ex:Latvia        | 20500  | 1850000  |
| ex:Liechtenstein | 180000 | 39000    |
| ex:Lithuania     | 23800  | 2790000  |
| ex:Luxembourg    | 135700 | 645000   |
| ex:Malta         | 32700  | 520000   |
| ex:Monaco        | 173000 | 39000    |
| ex:Netherlands   | 57400  | 17700000 |
| ex:Norway        | 89200  | 5400000  |
| ex:Portugal      | 25300  | 10300000 |
| ex:SanMarino     | 51800  | 34000    |
| ex:Slovakia      | 20900  | 5450000  |
| ex:Slovenia      | 28400  | 2100000  |
| ex:Sweden        | 59800  | 10500000 |
| ex:Switzerland   | 88400  | 8750000  |
----------------------------------------
```

## País con mayor PIB

```text
-----------------------------
| country          | gdp    |
=============================
| ex:Liechtenstein | 180000 |
-----------------------------
```

## Calcular PIB medio

```text
-------------
| avgGdp    |
=============
| 40156.875 |
-------------
```

## Países con PIB superior al PIB medio

```text
-----------------------------
| country          | gdp    |
=============================
| ex:Liechtenstein | 180000 |
| ex:Monaco        | 173000 |
| ex:Luxembourg    | 135700 |
| ex:Ireland       | 100200 |
| ex:Norway        | 89200  |
| ex:Switzerland   | 88400  |
| ex:Denmark       | 67500  |
| ex:Sweden        | 59800  |
| ex:Iceland       | 59100  |
| ex:Belgium       | 58400  |
| ex:Netherlands   | 57400  |
| ex:Austria       | 55900  |
| ex:Finland       | 53400  |
| ex:SanMarino     | 51800  |
| ex:Germany       | 51600  |
| ex:UnitedKingdom | 46900  |
| ex:France        | 43700  |
| ex:Andorra       | 42200  |
-----------------------------
```

## Países con población similar a España (+- 30%) con mayor PIB

```text
-------------------------------
| country  | gdp   | p        |
===============================
| ex:Italy | 35500 | 59500000 |
-------------------------------
```

## Crear una propiedad que indique el PIB en euros

```turtle
PREFIX ex:   <http://example.org/europe#>
PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#>

ex:SanMarino  ex:gdpPerCapitaEUR  44030.0 .

ex:Bulgaria  ex:gdpPerCapitaEUR  10710.0 .

ex:Sweden  ex:gdpPerCapitaEUR  50830.0 .

ex:Ireland  ex:gdpPerCapitaEUR  85170.0 .

ex:Russia  ex:gdpPerCapitaEUR  11390.0 .

ex:Portugal  ex:gdpPerCapitaEUR  21505.0 .

ex:Austria  ex:gdpPerCapitaEUR  47515.0 .

ex:Monaco  ex:gdpPerCapitaEUR  147050.0 .

ex:France  ex:gdpPerCapitaEUR  37145.0 .

ex:Turkey  ex:gdpPerCapitaEUR  8925.0 .

ex:Lithuania  ex:gdpPerCapitaEUR  20230.0 .

ex:Moldova  ex:gdpPerCapitaEUR  4845.0 .

ex:Netherlands  ex:gdpPerCapitaEUR  48790.0 .

ex:Latvia  ex:gdpPerCapitaEUR  17425.0 .

ex:Italy  ex:gdpPerCapitaEUR  30175.0 .

ex:Georgia  ex:gdpPerCapitaEUR  6120.0 .

ex:Iceland  ex:gdpPerCapitaEUR  50235.0 .

ex:CzechRepublic  ex:gdpPerCapitaEUR  22780.0 .

ex:Armenia  ex:gdpPerCapitaEUR  5312.5 .

ex:Greece  ex:gdpPerCapitaEUR  19210.0 .

ex:Estonia  ex:gdpPerCapitaEUR  24225.0 .

ex:Spain  ex:gdpPerCapitaEUR  28985.0 .

ex:NorthMacedonia  ex:gdpPerCapitaEUR  6120.0 .

ex:UnitedKingdom  ex:gdpPerCapitaEUR  39865.0 .

ex:Ukraine  ex:gdpPerCapitaEUR  3910.0 .

ex:Denmark  ex:gdpPerCapitaEUR  57375.0 .

ex:Belgium  ex:gdpPerCapitaEUR  49640.0 .

ex:Cyprus  ex:gdpPerCapitaEUR  25670.0 .

ex:Norway  ex:gdpPerCapitaEUR  75820.0 .

ex:Serbia  ex:gdpPerCapitaEUR  8160.0 .

ex:Malta  ex:gdpPerCapitaEUR  27795.0 .

ex:Montenegro  ex:gdpPerCapitaEUR  7310.0 .

ex:Poland  ex:gdpPerCapitaEUR  15810.0 .

ex:Croatia  ex:gdpPerCapitaEUR  15130.0 .

ex:Germany  ex:gdpPerCapitaEUR  43860.0 .

ex:Luxembourg  ex:gdpPerCapitaEUR  115345.0 .

ex:Hungary  ex:gdpPerCapitaEUR  16065.0 .

ex:Slovenia  ex:gdpPerCapitaEUR  24140.0 .

ex:Andorra  ex:gdpPerCapitaEUR  35870.0 .

ex:Switzerland  ex:gdpPerCapitaEUR  75140.0 .

ex:Albania  ex:gdpPerCapitaEUR  5593.0 .

ex:Finland  ex:gdpPerCapitaEUR  45390.0 .

ex:Belarus  ex:gdpPerCapitaEUR  6035.0 .

ex:BosniaAndHerzegovina
        ex:gdpPerCapitaEUR  6120.0 .

ex:Slovakia  ex:gdpPerCapitaEUR  17765.0 .

ex:Liechtenstein  ex:gdpPerCapitaEUR  153000.0 .

ex:Kosovo  ex:gdpPerCapitaEUR  4590.0 .

ex:Romania  ex:gdpPerCapitaEUR  14280.0 .
```

## Crear una nueva propiedad llamada ex:gdpRank, cuyo objetivo es indicar la posición de cada país europeo en función de su PIB per cápita, de mayor a menor

```turtle
PREFIX ex:   <http://example.org/europe#>
PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#>

ex:Serbia  ex:gdpRank  37 .

ex:Malta  ex:gdpRank  20 .

ex:France  ex:gdpRank  16 .

ex:Montenegro  ex:gdpRank  38 .

ex:BosniaAndHerzegovina
        ex:gdpRank  39 .

ex:Kosovo  ex:gdpRank  46 .

ex:Slovenia  ex:gdpRank  23 .

ex:Poland  ex:gdpRank  31 .

ex:Finland  ex:gdpRank  12 .

ex:Greece  ex:gdpRank  27 .

ex:Estonia  ex:gdpRank  22 .

ex:Denmark  ex:gdpRank  6 .

ex:Sweden  ex:gdpRank  7 .

ex:Latvia  ex:gdpRank  29 .

ex:Iceland  ex:gdpRank  8 .

ex:NorthMacedonia  ex:gdpRank  39 .

ex:Hungary  ex:gdpRank  30 .

ex:Italy  ex:gdpRank  18 .

ex:Andorra  ex:gdpRank  17 .

ex:Georgia  ex:gdpRank  39 .

ex:Russia  ex:gdpRank  34 .

ex:Germany  ex:gdpRank  14 .

ex:Lithuania  ex:gdpRank  26 .

ex:CzechRepublic  ex:gdpRank  24 .

ex:Albania  ex:gdpRank  43 .

ex:Cyprus  ex:gdpRank  21 .

ex:Bulgaria  ex:gdpRank  35 .

ex:Portugal  ex:gdpRank  25 .

ex:Spain  ex:gdpRank  19 .

ex:Ireland  ex:gdpRank  3 .

ex:Croatia  ex:gdpRank  32 .

ex:Belarus  ex:gdpRank  42 .

ex:Norway  ex:gdpRank  4 .

ex:Netherlands  ex:gdpRank  10 .

ex:SanMarino  ex:gdpRank  13 .

ex:Turkey  ex:gdpRank  36 .

ex:Ukraine  ex:gdpRank  47 .

ex:Luxembourg  ex:gdpRank  2 .

ex:Romania  ex:gdpRank  33 .

ex:Monaco  ex:gdpRank  1 .

ex:Moldova  ex:gdpRank  45 .

ex:UnitedKingdom  ex:gdpRank  15 .

ex:Switzerland  ex:gdpRank  5 .

ex:Liechtenstein  ex:gdpRank  0 .

ex:Slovakia  ex:gdpRank  28 .

ex:Armenia  ex:gdpRank  44 .

ex:Belgium  ex:gdpRank  9 .

ex:Austria  ex:gdpRank  11 .
```
