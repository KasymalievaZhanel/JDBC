package edu.phystech.jdbcdemo.queries.reports;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@AllArgsConstructor
public class FormatQuery2 implements FormatofReport{
    private String city;
    private String numCancelled;

    public static final ArrayList<String> headers
            = new ArrayList<>(Arrays.asList("City", "Number of cancelled flights"));

    @Override
    public ArrayList<Object> getItems() {
        return new ArrayList<>(Arrays.asList(city, numCancelled));
    }
//
//    @Override
//    public static ArrayList<String> getHeaders() {
//        return headers;
//    }

    @Override
    public void printItems(){
        System.out.println(city + " " + numCancelled);
    }
}
