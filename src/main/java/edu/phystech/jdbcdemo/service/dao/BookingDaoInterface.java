package edu.phystech.jdbcdemo.service.dao;

import edu.phystech.jdbcdemo.domain.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public interface BookingDaoInterface {
    Booking createBooking(ResultSet resultSet) throws SQLException;

    void saveBooking(Collection<Booking> Booking) throws SQLException;

    Set<Booking> getBooking() throws SQLException;
}
