package edu.phystech.jdbcdemo.queries;

import edu.phystech.jdbcdemo.domain.Booking;
import edu.phystech.jdbcdemo.domain.Ticket;
import edu.phystech.jdbcdemo.queries.reports.FormatQuery1;
import edu.phystech.jdbcdemo.queries.reports.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;

import edu.phystech.jdbcdemo.service.dao.BookingDao;
import edu.phystech.jdbcdemo.service.dao.TicketDao;
import edu.phystech.jdbcdemo.service.db.SimpleJdbcTemplate;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;

import static edu.phystech.jdbcdemo.service.db.DbInit.getSQL;

@AllArgsConstructor
public class Queries {
    private final SimpleJdbcTemplate source;

    /**
     * Вывести города, в которых несколько аэропортов.
     * @return ArrayList<FormatQuery1> - строки результата запроса в виде элеменотов листа
     * @throws IOException
     * @throws SQLException
     * @throws NullPointerException
     */
    public ArrayList<FormatQuery1> getCitiesWithNumerousAirports() throws IOException, SQLException, NullPointerException {
        String sql = getSQL("queries/query1.sql");
        ArrayList<FormatQuery1> res = new ArrayList<>();
        source.statement(stmt -> {
           ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //System.out.println(getCity(rs.getString("city")) + " " + rs.getString("airportsList"));
                res.add(new FormatQuery1(getCity(rs.getString("city")),
                        rs.getString("airportsList")
                ));
            }
        });
        return res;
    }

    /**
     * Получение города из json
     * @param str
     * @return
     */
    private String getCity(String str) {
        String city = null;
        try {
            JSONObject js = new JSONObject(str);
            city = js.getString("en");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return city;
    }

    /**
     * Вывести города, из которых чаще всего отменяли рейсы.
     * @return ArrayList<FormatQuery2> - строки результата запроса в виде элеменотов листа, каждый элемент которого
     * имеет формат FornatQuery2.
     * @throws IOException
     * @throws SQLException
     */
    public ArrayList<FormatQuery2> getCitiesWithCancelledFlights()
            throws IOException, SQLException {
        String sql = getSQL("queries/query2.sql");
        ArrayList<FormatQuery2> res = new ArrayList<>();
        source.statement(stmt -> {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                res.add(new FormatQuery2(
                        getCity(rs.getString("city")),
                        rs.getString("cnt")
                ));
            }
        });
        return res;
    }

    /**
     * В каждом городе вылета найти самый короткий маршрут. Отсортировать по продолжительности.
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public ArrayList<FormatQuery3> getShortestPaths() throws IOException, SQLException {
        String sql = getSQL("queries/query3.sql");
        ArrayList<FormatQuery3> res = new ArrayList<>();
        source.statement(stmt -> {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                res.add(new FormatQuery3(
                        getCity(rs.getString("departure")),
                        getCity(rs.getString("arrival")),
                        Long.valueOf(rs.getString("avg_time"))
                ));
            }
        });
        return res;
    }

    /**
     * Найти кол-во отмен рейсов по месяцам.
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public ArrayList<FormatQuery4> getCancelledByMonths() throws IOException, SQLException {
        String sql = getSQL("queries/query4.sql");
        ArrayList<FormatQuery4> res = new ArrayList<>();
        source.statement(stmt -> {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int date = Integer.parseInt(rs.getString(1));
                res.add(new FormatQuery4(
                        Month.of(date).toString(),
                        Integer.parseInt(rs.getString("num_cancelled"))
                ));
            }
        });
        return res;
    }

    /**
     * Выведите кол-во рейсов в Москву и из Москвы по дням недели за весь наблюдаемый период.
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public ArrayList<FormatQuery5> getMoscowFlightsByWeekDays() throws IOException, SQLException {
        String sql = getSQL("queries/query5.sql");
        ArrayList<FormatQuery5> res = new ArrayList<>();
        source.statement(stmt -> {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int day = Integer.parseInt(rs.getString(1));
                res.add(new FormatQuery5(
                        DayOfWeek.of(day).toString(),
                        Integer.parseInt(rs.getString("incoming")),
                        Integer.parseInt(rs.getString("outcoming"))
                ));
            }
        });
        return res;
    }

    /**
     * Отменить все рейсы самолета, заданной модели (модель - параметр). Все билеты, относящиеся
     * к удаленным рейсам - удалить.
     * @param model
     * @throws IOException
     * @throws SQLException
     */
    public void doDeleteFlights(String model) throws IOException, SQLException {
        String sql = getSQL("queries/query6.sql");
        source.preparedStatement(sql, stmt -> {
            stmt.setString(1, model);
            stmt.execute();
        });
    }

    /**
     * Отменить все рейсы самолета, заданной модели (модель - параметр). Все билеты,
     * относящиеся к удаленным рейсам - удалить.
     * @param startDate
     * @param endDate
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public ArrayList<FormatQuery7> doCancellCovidMoscowFlights(String startDate, String endDate)
            throws IOException, SQLException {
        String sql1 = getSQL("queries/query7_1.sql");
        String sql2 = getSQL("queries/query7_2.sql");
        ArrayList<FormatQuery7> res = new ArrayList<>();
        source.connection(conn -> {
            conn.setAutoCommit(false);
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            PreparedStatement stmt2 = conn.prepareStatement(sql2);

            // Ищем суммы
            stmt1.setDate(1, Date.valueOf(startDate));
            stmt1.setDate(2, Date.valueOf(endDate));
            stmt2.setDate(1, Date.valueOf(startDate));
            stmt2.setDate(2, Date.valueOf(endDate));

            ResultSet rs = stmt1.executeQuery();
            while (rs.next()) {
                res.add(new FormatQuery7(
                        rs.getString("day_cancelled"),
                        (Double.valueOf(rs.getString("money_loss")).longValue())
                ));
            }

            // Обновляем
            stmt2.execute();
            conn.commit();
        });
        return res;
    }

    /**
     * Добавление нового билета. Проверяется, что такой рейс есть,
     * имеются свободные места,
     * а билет с таким номером еще не был продан.
     *
     * @param ticket         билет на рейс
     * @param booking        бронирование
     * @param flightId       идентификатор рейса
     * @param fareConditions класс обслуживания
     */
    public void addNewTicket(Ticket ticket, Booking booking, int flightId, String fareConditions)
            throws IOException, SQLException {
        String sql8_1 = getSQL("queries/query8_1.sql");
        String sql8_2 = getSQL("queries/query8_2.sql");
        String sql8_3 = getSQL("queries/query8_3.sql");
        String sql8_4 = getSQL("queries/query8_4.sql");
        String sql8_5 = getSQL("queries/query8_5.sql");
        String sql8_6 = getSQL("queries/query8_6.sql");

        source.connection(conn -> {
            conn.setAutoCommit(false);
            try {
                PreparedStatement stmt1 = conn.prepareStatement(sql8_1);
                PreparedStatement stmt2 = conn.prepareStatement(sql8_2);
                PreparedStatement stmt3 = conn.prepareStatement(sql8_3);
                PreparedStatement stmt4 = conn.prepareStatement(sql8_4);
                PreparedStatement stmt5 = conn.prepareStatement(sql8_5);
                PreparedStatement stmt6 = conn.prepareStatement(sql8_6);

                stmt1.setInt(1, flightId);
                stmt2.setString(1, ticket.getTicketNo());
                stmt3.setString(1, booking.getBookRef());

                ResultSet rs1 = stmt1.executeQuery();
                if (!rs1.next()) {
                    throw new Exception();
                }
                String aircraftCode = rs1.getString("aircraft_code");
                ResultSet rs2 = stmt2.executeQuery();
                rs2.next();
                ResultSet rs3 = stmt3.executeQuery();
                rs3.next();
                if ((rs2.getInt(1) > 0) || (rs3.getInt(1) > 0)) {
                    throw new Exception();
                }

                stmt4.setString(1, aircraftCode);
                stmt4.setString(2, fareConditions);
                ResultSet rs4 = stmt4.executeQuery();
                rs4.next();
                stmt5.setInt(1, flightId);
                stmt5.setString(2, fareConditions);
                ResultSet rs5 = stmt5.executeQuery();
                rs5.next();

                // Проверяем наличие свободных мест
                if (rs4.getInt(1) < rs5.getInt(1)) {
                    throw new Exception();
                }

                // Добавляем данные в таблички
                stmt6.setString(1, ticket.getTicketNo());
                stmt6.setInt(2, flightId);
                stmt6.setString(3, fareConditions);
                stmt6.setBigDecimal(4, booking.getTotalAmount());
                stmt6.execute();

                // И для разнообразия другой вариант сохранения в табличку
                BookingDao bookingsDao = new BookingDao(source);
                bookingsDao.saveBooking(new ArrayList<>(Collections.singletonList(booking)));
                TicketDao ticketsDao = new TicketDao(source);
                ticketsDao.saveTicket(new ArrayList<>(Collections.singletonList(ticket)));

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("There is an error in your query");
                conn.rollback();
                conn.setAutoCommit(true);
            }
            conn.setAutoCommit(true);
        });
    }
}
