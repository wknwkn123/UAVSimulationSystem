package collisionavoidanceengine.runtime;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ziji Shi on 8/1/18.
 *
 * Take list of string as arguments and write the flight schedule to csv file.
 *
 */
public class ScheduleWriter {
    List<String> requestID;
    List<String> requestedStartTime ;
    List<String> actualStartTime ;
    List<String> travelTime ;
    List<String> schedule ;

    ScheduleWriter(){
        this.requestID= new ArrayList<String>();
        this.requestedStartTime= new ArrayList<String>();
        this.actualStartTime=new ArrayList<String>();
        this.travelTime=new ArrayList<String>();
        this.schedule =new ArrayList<String>();
    }

    public void addSchedule(String requestID, double requestedStartTime, double actualStartTime, double travelTime, String schedule){
        this.requestID.add(requestID);
        this.requestedStartTime.add(String.valueOf(requestedStartTime));
        this.actualStartTime.add(String.valueOf(actualStartTime));
        this.travelTime.add(String.valueOf(travelTime));
        this.schedule.add(schedule);
    }

    public void writeToCsv() {
        FileWriter fileWriter = null;

        String csvHeader = "requestID,requestedStartTime,actualStartTime,travelTime,schedule";

        try {
            fileWriter = new FileWriter("data/RandomRequest.csv");

            fileWriter.append(csvHeader + "\n");

//            for (Request request : requestQueue) {
//                fileWriter.append(request.getRequestID() + ",");
//                fileWriter.append(request.getStartTime() + ",");
//                fileWriter.append(request.getOriginID() + ",");
//                fileWriter.append(request.getDestinationID() + "\n");
            } catch (IOException e1) {
            e1.printStackTrace();
        }
    finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }


        }
    }



}
