package edu.phystech.jdbcdemo.service.db;

import edu.phystech.jdbcdemo.Hello;
import edu.phystech.jdbcdemo.domain.*;
import edu.phystech.jdbcdemo.service.dao.*;
import lombok.AllArgsConstructor;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Initializes database
 */
@AllArgsConstructor
public class DbInit {
    private final SimpleJdbcTemplate source;

    /**
     *
     * @param name
     * @return
     * @throws IOException
     */
    public static String getSQL(String name) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(Hello.class.getResourceAsStream(name)),
                        StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.joining("\n"));
        }
    }

    /**
     *
     * @throws SQLException
     * @throws IOException
     */
    public void create() throws SQLException, IOException {
        String sql = getSQL("dbcreate.sql");
        source.statement(stmt -> {
            stmt.execute(sql);
        });
    }







}
