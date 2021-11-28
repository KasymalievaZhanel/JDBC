package edu.phystech.jdbcdemo.service.dao;

import edu.phystech.jdbcdemo.domain.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public interface TicketDaoInterface {
    Ticket createTicket(ResultSet resultSet) throws SQLException;

    void saveTicket(Collection<Ticket> Ticket) throws SQLException;

    Set<Ticket> getTicket() throws SQLException;
}
