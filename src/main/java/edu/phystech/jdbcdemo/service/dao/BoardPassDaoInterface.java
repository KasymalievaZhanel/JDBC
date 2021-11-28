package edu.phystech.jdbcdemo.service.dao;

import edu.phystech.jdbcdemo.domain.BoardingPass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public interface BoardPassDaoInterface {
    BoardingPass createBoardingPass(ResultSet resultSet) throws SQLException;

    void saveBoardingPass(Collection<BoardingPass> BoardingPass) throws SQLException;

    Set<BoardingPass> getBoardingPass() throws SQLException;
}
