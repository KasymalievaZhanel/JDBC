package edu.phystech.jdbcdemo.queries.reports;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@AllArgsConstructor
public class FormatQuery7 implements FormatofReport{
    private String day;
    private long loss;

    public static final ArrayList<String> headers
            = new ArrayList<>(Arrays.asList("Day", "Money loss"));



    @Override
    public ArrayList<Object> getItems() {
        return  new ArrayList<>(Arrays.asList(day, loss));
    }

    @Override
    public void printItems() {
        System.out.println(day + " " + loss);
    }

//    @Override
//    public static ArrayList<String> getHeaders() {
//        return headers;
//    }
}
