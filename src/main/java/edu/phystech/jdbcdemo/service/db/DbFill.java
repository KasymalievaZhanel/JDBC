package edu.phystech.jdbcdemo.service.db;

import edu.phystech.jdbcdemo.domain.*;
import edu.phystech.jdbcdemo.service.dao.*;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;


@AllArgsConstructor
public class DbFill implements CSVUrls{

    @FunctionalInterface
    public interface Function<T> {
        T generateNewObject(String[] info);
    }

    /**
     *
     * @param urlStr
     * @param func
     * @param <T>
     * @return
     * @throws IOException
     */
    private <T> ArrayList<T> filltable(String urlStr, Function<T> func) throws IOException {
        URL url = new URL(urlStr);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String s;
        ArrayList<T> objects = new ArrayList<>();
        while ((s = in.readLine()) != null) {
            String[] info = s.split(",");
            objects.add(func.generateNewObject(info));
        }
        return objects;
    }

    private final SimpleJdbcTemplate source;

    /**
     *
     * @throws SQLException
     * @throws IOException
     */
    public void fillDb() throws SQLException, IOException {
//        fillAircrafts();
        fillAirports();
//        fillBoardingPasses();
//        fillBookings();
//        fillFlights();
//        fillSeats();
//        fillTicketFlights();
//        fillTickets();
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    public void fillAircrafts() throws IOException, SQLException {
        AircraftDao airDao = new AircraftDao(source);

        ArrayList<Aircraft> aircrafts = filltable(aircraftUrl,
                (info) ->
                        new Aircraft(info[0], strToJsonFormat(info[1], info[2]), Integer.parseInt(info[3]))
        );
        airDao.saveAircrafts(aircrafts);
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    private void fillAirports() throws IOException, SQLException {
        AirportDao airDao = new AirportDao(source);
        ArrayList<Airport> airports = filltable(airportUrl,
                (info) ->
                        new Airport(info[0], strToJsonFormat(info[1], info[2]),
                                strToJsonFormat(info[3], info[4]),
                                info[5], info[6])
        );
        airDao.saveAirports(airports);
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    private void fillBoardingPasses() throws IOException, SQLException {
        BoardPassDao passDao = new BoardPassDao(source);
        ArrayList<BoardingPass> passes = filltable(
                boardingPassUrl,
                info -> new BoardingPass(info[0], Integer.parseInt(info[1]),
                        Integer.parseInt(info[2]), info[3]));
        passDao.saveBoardingPass(passes);
    }

    private void fillBookings() throws IOException, SQLException {
        BookingDao passDao = new BookingDao(source);
        ArrayList<Booking> bookings = filltable(bookingUrl, info ->
                new Booking(info[0], Timestamp.valueOf(info[1].split("\\+")[0]),
                        new BigDecimal(info[2])));
        passDao.saveBooking(bookings);
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    private void fillFlights() throws IOException, SQLException {
        FlightDao passDao = new FlightDao(source);
        String urlStr = flightUrl;
        URL url = new URL(urlStr);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String s;
        ArrayList<Flight> flights = new ArrayList<>();
        while ((s = in.readLine()) != null) {
            String[] info = s.split(",");
            Timestamp ts1 = (info.length > 8) ? Timestamp.valueOf(info[8].split("\\+")[0]) : null;
            Timestamp ts2 = (info.length > 9) ? Timestamp.valueOf(info[9].split("\\+")[0]) : null;

            flights.add(new Flight(Long.parseLong(info[0]), info[1],
                    Timestamp.valueOf(info[2].split("\\+")[0]),
                    Timestamp.valueOf(info[3].split("\\+")[0]),
                    info[4], info[5], info[6], info[7], ts1, ts2));
        }
        passDao.saveFlight(flights);
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    private void fillSeats() throws IOException, SQLException {
        SeatDao seatsDao = new SeatDao(source);
        ArrayList<Seat> seats = filltable(seatUrl, info ->
                new Seat(info[0], info[1], info[2]));
        seatsDao.saveSeat(seats);
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    private void fillTicketFlights() throws IOException, SQLException {
        TicketFlightDao seatsDao = new TicketFlightDao(source);
        ArrayList<TicketFlight> ticketFlights = filltable(ticketFlightUrl, info ->
                new TicketFlight(info[0], Integer.parseInt(info[1]),
                        info[2], new BigDecimal(info[3])));
        seatsDao.saveTicketFlight(ticketFlights);
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    private void fillTickets() throws IOException, SQLException {
        TicketDao seatsDao = new TicketDao(source);
        String urlStr = ticketUrl;
        URL url = new URL(urlStr);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String s;
        ArrayList<Ticket> tickets = new ArrayList<>();
        while ((s = in.readLine()) != null) {
            String[] info = s.split(",");
            String extra = "";
            for (int i = 4; i < info.length - 1; i++) {
                extra += info[i];
            }
            String data = (info.length > 4) ? extra : null;
            tickets.add(new Ticket(info[0], info[1], info[2], info[3], data));
        }
        seatsDao.saveTicket(tickets);
    }
    /**
     *
     * @param str1
     * @param str2
     * @return
     */
    private String strToJsonFormat(String str1, String str2) {
        return (str1.substring(1) + "," + str2.substring(0, str2.length() - 1))
                .replaceAll("\"\"", "\\\"");
    }

}
