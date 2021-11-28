package edu.phystech.jdbcdemo.service.dao;

import lombok.AllArgsConstructor;
import edu.phystech.jdbcdemo.domain.Seat;
import edu.phystech.jdbcdemo.service.db.SimpleJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class SeatDao implements SeatDaoInterface{
    private final SimpleJdbcTemplate source;

    /**
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @Override
    public Seat createSeat(ResultSet resultSet) throws SQLException {
        return new Seat(resultSet.getString("aircraft_code"),
                resultSet.getString("seat_no"),
                resultSet.getString("fare_conditions"));
    }

    /**
     *
     * @param Seat
     * @throws SQLException
     */
    @Override
    public void saveSeat(Collection<Seat> seats) throws SQLException {
        source.preparedStatement("insert into seats(aircraft_code, " +
                "seat_no, fare_conditions) values (?, ?, ?)", insertSeat -> {
            for (Seat seat : seats) {
                insertSeat.setString(1, seat.getAircraftCode());
                insertSeat.setObject(2, seat.getSeatNo());
                insertSeat.setString(3, seat.getFareConditions());
                insertSeat.execute();
            }
        });
    }

    /***
     *
     * @return
     * @throws SQLException
     */
    @Override
    public Set<Seat> getSeat() throws SQLException {
        return source.statement(stmt -> {
            Set<Seat> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from seats");
            while (resultSet.next()) {
                result.add(createSeat(resultSet));
            }
            return result;
        });
    }
}