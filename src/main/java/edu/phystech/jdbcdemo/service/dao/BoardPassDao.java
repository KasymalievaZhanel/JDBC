package edu.phystech.jdbcdemo.service.dao;


import lombok.AllArgsConstructor;
import edu.phystech.jdbcdemo.domain.BoardingPass;
import edu.phystech.jdbcdemo.service.db.SimpleJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class BoardPassDao implements BoardPassDaoInterface{
    private final SimpleJdbcTemplate source;

    /**
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @Override
public BoardingPass createBoardingPass(ResultSet resultSet) throws SQLException {
        return new BoardingPass(resultSet.getString("ticket_no"),
                resultSet.getInt("flight_id"),
                resultSet.getInt("boarding_no"),
                resultSet.getString("seat_no"));
    }

    /**
     *
     * @param boardingPasses
     * @throws SQLException
     */
    @Override
    public void saveBoardingPass(Collection<BoardingPass> boardingPasses) throws SQLException {
        source.preparedStatement("insert into boarding_passes(ticket_no, " +
                "flight_id, boarding_no, seat_no) values (?, ?, ?, ?)", insertBoardingPass -> {
            for (BoardingPass boardingPass : boardingPasses) {
                insertBoardingPass.setString(1, boardingPass.getTicketNo());
                insertBoardingPass.setInt(2, boardingPass.getFlightId());
                insertBoardingPass.setInt(3, boardingPass.getBoardingNo());
                insertBoardingPass.setString(4, boardingPass.getSeatNo());
                insertBoardingPass.execute();
            }
        });
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public Set<BoardingPass> getBoardingPass() throws SQLException {
        return source.statement(stmt -> {
            Set<BoardingPass> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from boarding_passes");
            while (resultSet.next()) {
                result.add(createBoardingPass(resultSet));
            }
            return result;
        });
    }
}