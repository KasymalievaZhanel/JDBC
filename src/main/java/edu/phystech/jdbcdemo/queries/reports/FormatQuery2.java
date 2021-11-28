package edu.phystech.jdbcdemo.queries.reports;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@AllArgsConstructor
public class FormatQuery2 implements FormatofReport{
    private String city;
    private String numCancelled;

    private final ArrayList<String> headers
            = new ArrayList<>(Arrays.asList("City", "Number of cancelled flights"));

    private final ArrayList<Object> items
            = new ArrayList<>(Arrays.asList(city, numCancelled));

    @Override
    public ArrayList<Object> getItems() {
        return items;
    }

    @Override
    public ArrayList<String> getHeaders() {
        return headers;
    }

    @Override
    public void printItems(){
        System.out.println(city + " " + numCancelled);
    }
}
