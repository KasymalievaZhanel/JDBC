package edu.phystech.jdbcdemo;

import java.io.IOException;


import edu.phystech.jdbcdemo.queries.Queries;
import edu.phystech.jdbcdemo.queries.reports.FormatQuery1;
import edu.phystech.jdbcdemo.queries.reports.FormatofReport;
import edu.phystech.jdbcdemo.service.db.DbFill;
import edu.phystech.jdbcdemo.service.db.DbInit;
import edu.phystech.jdbcdemo.service.db.SimpleJdbcTemplate;
import edu.phystech.jdbcdemo.domain.Booking;
import edu.phystech.jdbcdemo.domain.Ticket;
import edu.phystech.jdbcdemo.queries.reports.*;
import edu.phystech.jdbcdemo.service.db.DbInit;
import edu.phystech.jdbcdemo.service.db.SimpleJdbcTemplate;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Hello{
    private static DataSource s = JdbcConnectionPool.create("jdbc:h2:mem:database;DB_CLOSE_DELAY=-1",
            "", "");
    private static SimpleJdbcTemplate source = new SimpleJdbcTemplate(
            s);

    public static void main(String[] args) throws IOException, SQLException {
        String ticketNo = args[1];
        String bookRef = args[2];
        String passengerId = args[3];
        String passengerName = args[4];
        String contactData = args[5];
        String bookDate = args[6];
        String totalAmount = args[7];
        String flightId = args[8];
        String fareConditions = args[9];


        DbInit db = new DbInit(source);
        db.create();
        DbFill dbf = new DbFill(source);
        dbf.fillDb();
        Queries queries = new Queries(source);

        switch (args[0]) {
            case "query1":
                ArrayList<FormatQuery1> data1 = queries.getCitiesWithNumerousAirports();
                ExcelLoader<FormatQuery1> reportGenerator1 = new ExcelLoader<>();
                reportGenerator1.buildExcelTable(data1, "artifacts/result_1.xls",
                        FormatQuery1.headers);
                break;

            case "query2":
                ArrayList<FormatQuery2> data2 = queries.getCitiesWithCancelledFlights();
                ExcelLoader<FormatQuery2> reportGenerator2 = new ExcelLoader<>();
                reportGenerator2.buildExcelTable(data2, "artifacts/result_2.xls",
                        FormatQuery2.headers);
                break;

            case "query3":
                ArrayList<FormatQuery3> data3 = queries.getShortestPaths();
                ExcelLoader<FormatQuery3> reportGenerator3 = new ExcelLoader<>();
                reportGenerator3.buildExcelTable(data3, "artifacts/result_3.xls",
                        FormatQuery3.headers);
                break;

            case "query4":
                ArrayList<FormatQuery4> data4 = queries.getCancelledByMonths();
                ExcelLoader<FormatQuery4> reportGenerator4 = new ExcelLoader<>();
                reportGenerator4.buildExcelTable(data4, "artifacts/result_4.xls",
                        FormatQuery4.headers);
//                ChartLoader<FormatQuery4> graphGenerator4 = new ChartLoader<>();
//                graphGenerator4.buildChart(data4, "Cancelled flights in months",
//                        FormatQuery4.headers, "artifacts/graph_4.png");
                break;

            case "query5":
                ArrayList<FormatQuery5> data5 = queries.getMoscowFlightsByWeekDays();
                ExcelLoader<FormatQuery5> reportGenerator5 = new ExcelLoader<>();
                reportGenerator5.buildExcelTable(data5, "artifacts/result_5.xls",
                        FormatQuery5.headers);
//                ChartLoader<FormatQuery5> graphGenerator5 = new ChartLoader<>();
//                graphGenerator5.buildChart(data5, "Moscow flights in week days",
//                        FormatQuery5.headers, "artifacts/graph_5.png");
                break;

            case "query6":
                if (args.length < 2) {
                    System.out.println("Not enough arguments");
                    break;
                }
                queries.doDeleteFlights(args[1]);
                // nothing to export here
                break;

            case "query7":
                if (args.length < 3) {
                    System.out.println("Not enough arguments");
                    break;
                }
                ArrayList<FormatQuery7> data7 = queries.doCancellCovidMoscowFlights(args[1], args[2]);
                ExcelLoader<FormatQuery7> reportGenerator7 = new ExcelLoader<>();
                reportGenerator7.buildExcelTable(data7, "artifacts/result_7.xls",
                        FormatQuery7.headers);
//                ChartLoader<FormatQuery7> graphGenerator7 = new ChartLoader<>();
//                graphGenerator7.buildChart(data7, "Lost money amounts",
//                        FormatQuery7.headers, "artifacts/graph_7.png");
                break;

            case "query8":
                if (args.length < 10) {
                    System.out.println("Not enough arguments");
                    System.out.println("You need to identify 9 more arguments: " +
                            "ticketNo, bookRef, passengerId, passengerName, contactData, " +
                            "bookDate(yyyy-mm-dd), totalAmount, flightId, fareConditions");
                    break;
                }

                Ticket myTicket = new Ticket(ticketNo, bookRef, passengerId,
                        passengerName, contactData);
                Booking myBook = new Booking(bookRef,
                        new Timestamp(Date.valueOf(bookDate).getTime()),
                        new BigDecimal(totalAmount));
                queries.addNewTicket(myTicket, myBook, Integer.parseInt(flightId), fareConditions);
                break;
        }
    }
}