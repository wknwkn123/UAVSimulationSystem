package flightEngine;

import airspaceEngine.AirspaceEngine;
import com.google.gson.Gson;
import config.Config;
import flightPlan.Flight;
import flightPlan.FlightPlanEngine;
import flightPlan.Flight_JSON;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.api.Result;
import flightEngine.json_formatting.Simulation;

import java.io.IOException;
import java.util.List;

/**
 * Runnable for simulation in order to separate main thread and simulation thread.
 */

public class FlightGenerationApp {
    private boolean stopWork;
    private FlightPlanEngine flightPlanEngine;
    private AirspaceEngine airspaceEngine;
    private FlightConfiguration configuration;

    public FlightGenerationApp(Simulation param){
        airspaceEngine = new AirspaceEngine();
        configuration = new FlightConfiguration(param);
        flightPlanEngine = new FlightPlanEngine(this.configuration);
    }

    public void startSimulation() {
        this.configuration.setAirspaceType(configuration.getAirspaceType());
        this.configuration.setFlightScheduleType(configuration.getFlightScheduleType());

        //starting simulation
        // create airspace
        try {
            airspaceEngine.createAirspace(new Config());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //create schedule/demand
        flightPlanEngine.createFlightPlans("RANDOM", airspaceEngine.getAirMap());

        List<Flight> plans = flightPlanEngine.getFlights();
        System.out.println("----- Srart Sending Flight Plans -----");
        for (Flight flt : plans) {
            System.out.println("Sending Flight Plan "+ flt.getFlightID()+", ");
            httpClientSend(new Flight_JSON(flt));
        }
        System.out.println("----- End Sending Flight Plans -----");

    }



    public void httpClientSend( Flight_JSON flight) {
        Gson gson = new Gson();
        String data = gson.toJson(flight).toString();

        HttpClient httpClient = new HttpClient();

        // Configure HttpClient, for example:
        httpClient.setFollowRedirects(false);

        // Start HttpClient
        try {
            httpClient.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


        httpClient.newRequest("http://localhost:3001/uavs")
                .param("uavs", data)
                .send(new Response.CompleteListener()
                {
                    @Override
                    public void onComplete(Result result)
                    {
                        // Your logic here
                        System.out.println("Flight Id : "+flight.getFlightId()+", Status : "+result.getResponse().getStatus());
                    }
                });

    }

}
