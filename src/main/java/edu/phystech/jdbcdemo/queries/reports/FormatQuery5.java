package edu.phystech.jdbcdemo.queries.reports;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@AllArgsConstructor
public class FormatQuery5 implements FormatofReport{
    private String day;
    private int incoming;
    private int outcoming;

    private final ArrayList<String> headers
            = new ArrayList<>(Arrays.asList("Day", "Flights to Moscow", "Flights from Moscow"));

    private final ArrayList<Object> items
            = new ArrayList<>(Arrays.asList(day, incoming, outcoming));

    @Override
    public ArrayList<Object> getItems() {
        return items;
    }

    @Override
    public ArrayList<String> getHeaders() {
        return headers;
    }

    @Override
    public void printItems() {
        System.out.println(day + " " + incoming + " " + outcoming);
    }
}
