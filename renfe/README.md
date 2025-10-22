# Renfe

## [Part 1](./src/main/java/renfe/Part1.java)

To create the [Renfe turtle file](./renfe.ttl), we used the files [stop_times.txt](./data/stop_times.txt) and [stops.txt](./data/stops.txt). For each train station in [stops.txt](./data/stops.txt), we create a Bag with the stops associated to that station, using the `stop_id` field in [stop_times.txt](./data/stop_times.txt) to link both files.

## [Part 2](./src/main/java/renfe/Part2.java)

In this part, we create a rectangle around [Galicia](https://es.wikipedia.org/wiki/Galicia#Geograf%C3%ADa) and we find all the train stations within that rectangle.
