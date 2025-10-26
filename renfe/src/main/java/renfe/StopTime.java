package renfe;

public class StopTime {
  private String tripId;
  private String arrivalTime;
  private String departureTime;
  private String stopId;
  private String stopSequence;

  // Constructor
  public StopTime(String[] csvData) {
    this.tripId = csvData[0];
    this.arrivalTime = csvData[1];
    this.departureTime = csvData[2];
    this.stopId = csvData[3];
    this.stopSequence = csvData[4];
  }

  // Getters y setters
  public String getTripId() {
    return tripId;
  }

  public String getArrivalTime() {
    return arrivalTime;
  }

  public String getDepartureTime() {
    return departureTime;
  }

  public String getStopId() {
    return stopId;
  }

  public String getStopSequence() {
    return stopSequence;
  }
}
