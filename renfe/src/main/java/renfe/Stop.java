package renfe;

public class Stop {
    private String stopId; // Important
    private String stopCode;
    private String stopName; // Important
    private String stopDesc;
    private Float stopLat; // Important
    private Float stopLon; // Important
    private String zoneId;
    private String stopUrl;
    private String locationType;
    private String parentStation;
    private String stopTimezone;
    private String wheelchairBoarding;
    
    // Constructor
    public Stop(String[] csvData) {
        this.stopId = csvData[0];
        this.stopCode = csvData[1];
        this.stopName = csvData[2];
        this.stopDesc = csvData[3];
        this.stopLat = Float.parseFloat(csvData[4]);
        this.stopLon = Float.parseFloat(csvData[5]);
        this.zoneId = csvData[6];
        this.stopUrl = csvData[7];
        this.locationType = csvData[8];
        this.parentStation = csvData[9];
        this.stopTimezone = csvData[10];
        this.wheelchairBoarding = csvData[11];
    }
    
    // Getters y setters
    public String getStopId() { return stopId; }
    public String getStopName() { return stopName; }
    public Float getStopLat() { return stopLat; }
    public Float getStopLon() { return stopLon; }
    
    @Override
    public String toString() {
        return String.format("Stop{id='%s', name='%s', lat='%s', lon='%s'}", 
            stopId, stopName, stopLat, stopLon);
    }
}