package edu.phystech.jdbcdemo.service.dao;

import edu.phystech.jdbcdemo.domain.TicketFlight;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public interface TicketFlightDaoInterface {
    TicketFlight createTicketFlight(ResultSet resultSet) throws SQLException;

    void saveTicketFlight(Collection<TicketFlight> TicketFlight) throws SQLException;

    Set<TicketFlight> getTicketFlight() throws SQLException;
}
