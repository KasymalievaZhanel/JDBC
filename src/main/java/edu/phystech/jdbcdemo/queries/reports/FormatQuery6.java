package edu.phystech.jdbcdemo.queries.reports;

import lombok.Data;

import java.util.ArrayList;

@Data
public class FormatQuery6 implements FormatofReport{

    @Override
    public ArrayList<Object> getItems() {
        return null;
    }

    @Override
    public ArrayList<String> getHeaders() {
        return null;
    }

    @Override
    public void printItems() {
        System.out.println("bIGdICK");
    }
}
