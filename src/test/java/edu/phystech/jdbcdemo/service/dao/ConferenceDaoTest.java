package edu.phystech.jdbcdemo.service.dao;

import edu.phystech.jdbcdemo.Hello;
import edu.phystech.jdbcdemo.domain.*;
import edu.phystech.jdbcdemo.queries.Queries;
import edu.phystech.jdbcdemo.queries.reports.FormatQuery1;
import edu.phystech.jdbcdemo.queries.reports.FormatQuery2;
import edu.phystech.jdbcdemo.service.dao.*;
import edu.phystech.jdbcdemo.service.db.DbFill;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import edu.phystech.jdbcdemo.domain.Aircraft;
import edu.phystech.jdbcdemo.service.dao.AircraftDao;
import edu.phystech.jdbcdemo.service.db.DbInit;
import edu.phystech.jdbcdemo.service.db.SimpleJdbcTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static edu.phystech.jdbcdemo.service.dao.TestData.FIRSTAIRCRAFT;
import static edu.phystech.jdbcdemo.service.dao.TestData.SECONDAIRCRAFT;

public class ConferenceDaoTest {

    static SimpleJdbcTemplate source = new SimpleJdbcTemplate(JdbcConnectionPool.create(
            "jdbc:h2:mem:database;DB_CLOSE_DELAY=-1", "", ""));

    private static AircraftDao aircraftDao = new AircraftDao(source);
    private static AirportDao airportDao = new AirportDao(source);
    private static BoardPassDao boardPassDao = new BoardPassDao(source);

    @BeforeEach
    void setupDB() throws IOException, SQLException {
        new DbInit(source).create();
        new DbFill(source).fillDb();
    }

    @AfterEach
    void tearDownDB() throws SQLException, IOException {
        source.statement(stmt -> {
            stmt.execute("drop all objects;");
        });
    }

    private Collection<Aircraft> getTestConferences() {
        return Arrays.asList(FIRSTAIRCRAFT, SECONDAIRCRAFT);
    }

//    @Test
//    public void saveANDgetAllAircrafts() throws SQLException {
//        Set<Aircraft> allaircrafts = aircraftDao.getAllAircrafts();
//        System.out.println("//////////AIRCRAFTS///////////////");
//        for (Aircraft aircraft: allaircrafts) {
//            System.out.println(aircraft.getAircraftCode() + aircraft.getModel() + aircraft.getRange());
//        }
//    }

//    @Test
//    public void saveANDgetAllAirports() throws SQLException {
//        Set<Airport> allairports = airportDao.getAllAirports();
////        System.out.println("/////////////////AIRPORT///////////////");
//        for (Airport airport: allairports) {
//            System.out.println(airport.getAirportCode() + airport.getAirportName() + airport.getCity()
//                    + airport.getTimezone() + airport.getCoordinates());
//        }
//    }

//    @Test
//    public void saveANDgetAllBoardingPasses() throws SQLException {
//        Set<BoardingPass> allairports = boardPassDao.getBoardingPass();
//        System.out.println("/////////////////PASS///////////////");
//        for (BoardingPass boardingPass: allairports) {
//            System.out.println(boardingPass.getBoardingNo() + " " + boardingPass.getFlightId() + "\n" +
//                    boardingPass.getSeatNo() + " " +
//                    boardingPass.getTicketNo());
//        }
//    }
//
//    @Test
//    void getQuery1() throws SQLException, IOException, NullPointerException {
//        Queries g1 = new Queries(source);
//        ArrayList<FormatQuery1> res = g1.getCitiesWithNumerousAirports();
//        for(FormatQuery1 q : res) {
//            q.printItems();
//        }
//    }


//    @Test
//    void getQuery2() throws SQLException, IOException, NullPointerException {
//        Queries g2 = new Queries(source);
//        ArrayList<FormatQuery2> res = g2.getCitiesWithCancelledFlights();
//        for(FormatQuery2 q : res) {
//            q.printItems();
//        }
//    }

}
