package edu.phystech.jdbcdemo.service.dao;

import edu.phystech.jdbcdemo.domain.Aircraft;
import edu.phystech.jdbcdemo.domain.Airport;
import edu.phystech.jdbcdemo.domain.BoardingPass;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class TestData {
    public static Aircraft FIRSTAIRCRAFT = new Aircraft("123",
            "{\"en\": \"Boeing-777\", \"ru\": \"Боинг-777\"}",
            12);
    public static Aircraft SECONDAIRCRAFT = new Aircraft("456",
            "{\"en\": \"Boeing-666\", \"ru\": \"Боинг-666\"}",
            5);
}
