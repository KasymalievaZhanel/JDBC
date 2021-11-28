package edu.phystech.jdbcdemo.queries.reports;
import lombok.AllArgsConstructor;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Arrays;

@AllArgsConstructor
public class FormatQuery4 implements FormatofReport{
    private String month;
    private int numCancelled;

    public static final ArrayList<String> headers
            = new ArrayList<>(Arrays.asList("Month", "Number of cancelled"));

    @Override
    public ArrayList<Object> getItems() {
        return new ArrayList<>(Arrays.asList(month, numCancelled));
    }

//    @Override
//    public static ArrayList<String> getHeaders() {
//        return headers;
//    }

    @Override
    public void printItems() {
        System.out.println(month + " " + numCancelled);
    }
}
