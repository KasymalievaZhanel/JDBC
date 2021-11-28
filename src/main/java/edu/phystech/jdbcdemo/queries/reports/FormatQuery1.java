package edu.phystech.jdbcdemo.queries.reports;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;


@AllArgsConstructor
public class FormatQuery1 implements FormatofReport {
    private String city;
    private String airportsList;

    public static final ArrayList<String> headers
            = new ArrayList<>(Arrays.asList("City", "Airports' list"));


    @Override
    public ArrayList<Object> getItems() {
        return new ArrayList<>(Arrays.asList(city, airportsList));
    }


//    @Override
//    public static ArrayList<String> getHeaders() {
//        return headers;
//    }

    @Override
    public void printItems(){
        System.out.println(city + " " + airportsList);
    }
}
