package edu.phystech.jdbcdemo.service.dao;

import lombok.AllArgsConstructor;
import edu.phystech.jdbcdemo.domain.Airport;
import edu.phystech.jdbcdemo.service.db.SimpleJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class AirportDao implements AiportDaoInterface {
    private final SimpleJdbcTemplate source;

    /**
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @Override
    public Airport createAirport(ResultSet resultSet) throws SQLException {
        return new Airport(resultSet.getString("airport_code"),
                resultSet.getString("airport_name"),
                resultSet.getString("city"),
                resultSet.getString("coordinates"),
                resultSet.getString("timezone"));
    }

    /**
     *
     * @param airports
     * @throws SQLException
     */
    @Override
    public void saveAirports(Collection<Airport> airports) throws SQLException {
        source.preparedStatement("insert into airports(airport_code, " +
                "airport_name, city, coordinates, timezone) values (?, ?, ?, ?, ?)", insertAirport -> {
            for (Airport airport : airports) {
                insertAirport.setString(1, airport.getAirportCode());
                insertAirport.setString(2, airport.getAirportName().toString());
                insertAirport.setString(3, airport.getCity().toString());
                insertAirport.setString(4, airport.getCoordinates());
                insertAirport.setString(5, airport.getTimezone());
                insertAirport.execute();
            }
        });
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public Set<Airport> getAllAirports() throws SQLException {
        return source.statement(stmt -> {
            Set<Airport> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from airports");
            while (resultSet.next()) {
                result.add(createAirport(resultSet));
            }
            return result;
        });
    }
}