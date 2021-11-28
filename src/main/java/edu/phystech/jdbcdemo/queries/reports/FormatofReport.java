package edu.phystech.jdbcdemo.queries.reports;

import java.util.ArrayList;
import java.util.Arrays;

public interface FormatofReport {
    static ArrayList<String > headers = null;

    public ArrayList<Object> getItems();

    public void printItems();
}
