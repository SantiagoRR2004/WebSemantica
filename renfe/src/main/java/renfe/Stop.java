package renfe;

public class Stop {
    private String stopId;
    private String stopCode;
    private String stopName;
    private String stopDesc;
    private String stopLat;
    private String stopLon;
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
        this.stopLat = csvData[4];
        this.stopLon = csvData[5];
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
    public String getStopLat() { return stopLat; }
    public String getStopLon() { return stopLon; }
    
    @Override
    public String toString() {
        return String.format("Stop{id='%s', name='%s', lat='%s', lon='%s'}", 
            stopId, stopName, stopLat, stopLon);
    }
}