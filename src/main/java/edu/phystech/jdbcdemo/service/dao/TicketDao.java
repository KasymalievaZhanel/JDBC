package edu.phystech.jdbcdemo.service.dao;

import lombok.AllArgsConstructor;
import edu.phystech.jdbcdemo.domain.Ticket;
import edu.phystech.jdbcdemo.service.db.SimpleJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class TicketDao implements TicketDaoInterface{
    private final SimpleJdbcTemplate source;

    /**
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @Override
    public Ticket createTicket(ResultSet resultSet) throws SQLException {
        return new Ticket(resultSet.getString("ticket_no"),
                resultSet.getString("book_ref"),
                resultSet.getString("passenger_id"),
                resultSet.getString("passenger_name"),
                resultSet.getString("contact_data"));
    }

    /**
     *
     * @param Ticket
     * @throws SQLException
     */
    @Override
    public void saveTicket(Collection<Ticket> tickets) throws SQLException {
        source.preparedStatement("insert into tickets(ticket_no, " +
                "book_ref, passenger_id, passenger_name, contact_data) values (?, ?, ?, ?, ?)", insertTicket -> {
            for (Ticket ticket : tickets) {
                insertTicket.setString(1, ticket.getTicketNo());
                insertTicket.setString(2, ticket.getBookRef());
                insertTicket.setString(3, ticket.getPassengerId());
                insertTicket.setString(4, ticket.getPassengerName());
                insertTicket.setString(5, ticket.getContactData());
                insertTicket.execute();
            }
        });
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public Set<Ticket> getTicket() throws SQLException {
        return source.statement(stmt -> {
            Set<Ticket> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from tickets");
            while (resultSet.next()) {
                result.add(createTicket(resultSet));
            }
            return result;
        });
    }
}