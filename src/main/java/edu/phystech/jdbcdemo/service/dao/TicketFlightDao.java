package edu.phystech.jdbcdemo.service.dao;

import lombok.AllArgsConstructor;
import edu.phystech.jdbcdemo.domain.TicketFlight;
import edu.phystech.jdbcdemo.service.db.SimpleJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class TicketFlightDao implements TicketFlightDaoInterface {
    private final SimpleJdbcTemplate source;

    /**
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @Override
    public TicketFlight createTicketFlight(ResultSet resultSet) throws SQLException {
        return new TicketFlight(resultSet.getString("ticket_no"),
                resultSet.getInt("flight_id"),
                resultSet.getString("fare_conditions"),
                resultSet.getBigDecimal("amount"));
    }

    /**
     *
     * @param TicketFlight
     * @throws SQLException
     */
    @Override
    public void saveTicketFlight(Collection<TicketFlight> ticketFlights) throws SQLException {
        source.preparedStatement("insert into ticket_flights(ticket_no, " +
                "flight_id, fare_conditions, amount) values (?, ?, ?, ?)", insertTicketFlight -> {
            for (TicketFlight ticketFlight : ticketFlights) {
                insertTicketFlight.setString(1, ticketFlight.getTicketNo());
                insertTicketFlight.setInt(2, ticketFlight.getFlightId());
                insertTicketFlight.setString(3, ticketFlight.getFareConditions());
                insertTicketFlight.setBigDecimal(4, ticketFlight.getAmount());
                insertTicketFlight.execute();
            }
        });
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public Set<TicketFlight> getTicketFlight() throws SQLException {
        return source.statement(stmt -> {
            Set<TicketFlight> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from ticket_flights");
            while (resultSet.next()) {
                result.add(createTicketFlight(resultSet));
            }
            return result;
        });
    }
}