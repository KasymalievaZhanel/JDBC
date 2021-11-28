package edu.phystech.jdbcdemo.service.dao;

import edu.phystech.jdbcdemo.domain.Flight;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public interface FlightDaoInterface {
    Flight createFlight(ResultSet resultSet) throws SQLException;

    void saveFlight(Collection<Flight> flights) throws SQLException;

    Set<Flight> getFlight() throws SQLException;
}
