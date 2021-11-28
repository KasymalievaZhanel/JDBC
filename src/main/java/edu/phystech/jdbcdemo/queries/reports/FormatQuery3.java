package edu.phystech.jdbcdemo.queries.reports;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@AllArgsConstructor
public class FormatQuery3 implements FormatofReport {
    private String departure;
    private String arrival;
    private Long avgTime;

   public static final ArrayList<String> headers
            = new ArrayList<>(Arrays.asList("Departure city", "Arrival city", "Average flight duration"));

    @Override
    public ArrayList<Object> getItems() {
        return new ArrayList<>(Arrays.asList(departure, arrival, avgTime));
    }

//    @Override
//    public static ArrayList<String> getHeaders() {
//        return headers;
//    }

    @Override
    public void printItems() {
        System.out.println(departure + " " + arrival + " " + avgTime);
    }
}
