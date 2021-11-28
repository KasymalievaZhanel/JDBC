package edu.phystech.jdbcdemo.service.dao;

import edu.phystech.jdbcdemo.domain.Airport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public interface AiportDaoInterface {
    Airport createAirport(ResultSet resultSet) throws SQLException;

    void saveAirports(Collection<Airport> Airport) throws SQLException;

    Set<Airport> getAllAirports() throws SQLException;
}
