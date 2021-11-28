package edu.phystech.jdbcdemo.queries.reports;

import java.util.ArrayList;
import java.util.Arrays;

public interface FormatofReport {
    static ArrayList<Object> headers = null;
    static ArrayList<Object> items = null;

    public ArrayList<Object> getItems();

    public ArrayList<? extends Object> getHeaders();

    public void printItems();
}
