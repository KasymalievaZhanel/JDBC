package edu.phystech.jdbcdemo.service.dao;

import edu.phystech.jdbcdemo.domain.Aircraft;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public interface AircraftDaoInterface {
    Aircraft createAircraft(ResultSet resultSet) throws SQLException;

    Set<Aircraft> getAllAircrafts() throws SQLException;

    void saveAircrafts(Collection<Aircraft> aircrafts) throws SQLException;
}
