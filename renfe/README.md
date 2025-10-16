# Renfe

To create the [Renfe turtle file](./renfe.ttl), we used the files [stop_times.txt](./data/stop_times.txt) and [stops.txt](./data/stops.txt). For each train station in [stops.txt](./data/stops.txt), we create a Bag with the stops associated to that station, using the `stop_id` field in [stop_times.txt](./data/stop_times.txt) to link both files.
