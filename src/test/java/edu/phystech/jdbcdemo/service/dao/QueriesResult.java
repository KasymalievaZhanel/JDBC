package edu.phystech.jdbcdemo.service.dao;

import edu.phystech.jdbcdemo.queries.Queries;
import edu.phystech.jdbcdemo.queries.reports.*;
import edu.phystech.jdbcdemo.service.db.SimpleJdbcTemplate;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueriesResult {
    static SimpleJdbcTemplate source = new SimpleJdbcTemplate(JdbcConnectionPool.create(
            "jdbc:h2:mem:database;DB_CLOSE_DELAY=-1", "", ""));

    @Test
    void getQuery1() throws SQLException, IOException, NullPointerException {
        Queries g1 = new Queries(source);
        ArrayList<FormatQuery1> res = g1.getCitiesWithNumerousAirports();
        for(FormatQuery1 q : res) {
            q.printItems();
        }
    }

    @Test
    void getQuery2() throws SQLException, IOException, NullPointerException {
        Queries g2 = new Queries(source);
        ArrayList<FormatQuery2> res = g2.getCitiesWithCancelledFlights();
        for(FormatQuery2 q : res) {
            q.printItems();
        }
    }
}