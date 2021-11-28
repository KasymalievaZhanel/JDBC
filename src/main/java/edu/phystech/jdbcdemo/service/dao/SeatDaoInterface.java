package edu.phystech.jdbcdemo.service.dao;

import edu.phystech.jdbcdemo.domain.Seat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public interface SeatDaoInterface {
    Seat createSeat(ResultSet resultSet) throws SQLException;

    void saveSeat(Collection<Seat> Seat) throws SQLException;

    Set<Seat> getSeat() throws SQLException;
}
