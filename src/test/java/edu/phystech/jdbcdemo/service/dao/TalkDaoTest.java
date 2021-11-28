package edu.phystech.jdbcdemo.service.dao;

import edu.phystech.jdbcdemo.Hello;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

public class TalkDaoTest {
    @Test
    void mainTest() throws SQLException, IOException {
        System.out.println("hellooo");
        String[] str = new String[] {"query1"};
        Hello.main(str);
    }
}