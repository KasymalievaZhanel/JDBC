package edu.phystech.jdbcdemo.service.dao;

import lombok.AllArgsConstructor;
import edu.phystech.jdbcdemo.domain.Flight;
import edu.phystech.jdbcdemo.service.db.SimpleJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class FlightDao implements FlightDaoInterface{
    private final SimpleJdbcTemplate source;

    /**
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @Override
    public Flight createFlight(ResultSet resultSet) throws SQLException {
        return new Flight(resultSet.getLong("flight_id"),
                resultSet.getString("flight_no"),
                resultSet.getTimestamp("scheduled_departure"),
                resultSet.getTimestamp("scheduled_arrival"),
                resultSet.getString("departure_airport"),
                resultSet.getString("arrival_airport"),
                resultSet.getString("status"),
                resultSet.getString("aircraft_code"),
                resultSet.getTimestamp("actual_departure"),
                resultSet.getTimestamp("actual_arrival"));
    }

    /**
     *
     * @param flights
     * @throws SQLException
     */
    @Override
    public void saveFlight(Collection<Flight> flights) throws SQLException {
        source.preparedStatement("insert into flights(flight_id, " +
                "flight_no, scheduled_departure, scheduled_arrival, departure_airport," +
                "arrival_airport, status, aircraft_code, actual_departure, actual_arrival) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", insertFlight -> {
            for (Flight flight : flights) {
                insertFlight.setLong(1, flight.getFlightId());
                insertFlight.setString(2, flight.getFlightNo());
                insertFlight.setTimestamp(3, flight.getScheduledDeparture());
                insertFlight.setTimestamp(4, flight.getScheduledArrival());
                insertFlight.setString(5, flight.getDepartureAirport());
                insertFlight.setString(6, flight.getArrivalAirport());
                insertFlight.setString(7, flight.getStatus());
                insertFlight.setString(8, flight.getAircraftCode());
                insertFlight.setTimestamp(9, flight.getActualDeparture());
                insertFlight.setTimestamp(10, flight.getActualArrival());
                insertFlight.execute();
            }
        });
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public Set<Flight> getFlight() throws SQLException {
        return source.statement(stmt -> {
            Set<Flight> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from flights");
            while (resultSet.next()) {
                result.add(createFlight(resultSet));
            }
            return result;
        });
    }
}