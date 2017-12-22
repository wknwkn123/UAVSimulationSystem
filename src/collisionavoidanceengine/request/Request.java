package collisionavoidanceengine.request;

/**
 * Created by StevenShi on 18/12/17.
 *
 * Request to deliver goods from source to destination
 *
 *
 */
public class Request {
    private String originID;
    private String destinationID;
    private String UAVID;
    private int startTime;

    public Request(String originID, String destinationID, int startTime) {
        this.originID = originID;
        this.destinationID = destinationID;
        this.startTime = startTime;
    }

    public void setUAVID(String UAVID) {
        this.UAVID = UAVID;
    }

    public String getOriginID() {
        return originID;
    }

    public void setOriginID(String originID) {
        this.originID = originID;
    }

    public String getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(String destinationID) {
        this.destinationID = destinationID;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public String getUAVID() {
        return UAVID;
    }
}
