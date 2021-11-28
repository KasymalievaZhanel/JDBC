package edu.phystech.jdbcdemo.service.dao;

import lombok.AllArgsConstructor;
import edu.phystech.jdbcdemo.domain.Booking;
import edu.phystech.jdbcdemo.service.db.SimpleJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class BookingDao implements BookingDaoInterface{
    private final SimpleJdbcTemplate source;

    /**
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @Override
    public Booking createBooking(ResultSet resultSet) throws SQLException {
        return new Booking(resultSet.getString("book_ref"),
                resultSet.getTimestamp("book_date"),
                resultSet.getBigDecimal("total_amount"));
    }

    /**
     *
     * @param bookings
     * @throws SQLException
     */
    @Override
    public void saveBooking(Collection<Booking> bookings) throws SQLException {
        source.preparedStatement("insert into bookings(book_ref, " +
                "book_date, total_amount) values (?, ?, ?)", insertBooking -> {
            for (Booking booking : bookings) {
                insertBooking.setString(1, booking.getBookRef());
                insertBooking.setTimestamp(2, booking.getBookDate());
                insertBooking.setBigDecimal(3, booking.getTotalAmount());
                insertBooking.execute();
            }
        });
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public Set<Booking> getBooking() throws SQLException {
        return source.statement(stmt -> {
            Set<Booking> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from bookings");
            while (resultSet.next()) {
                result.add(createBooking(resultSet));
            }
            return result;
        });
    }
}