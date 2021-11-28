package edu.phystech.jdbcdemo.service.dao;

import edu.phystech.jdbcdemo.service.db.SimpleJdbcTemplate;
import lombok.AllArgsConstructor;
import edu.phystech.jdbcdemo.domain.Aircraft;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class AircraftDao implements AircraftDaoInterface {
    private final SimpleJdbcTemplate source;

    /**
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    @Override
    public Aircraft createAircraft(ResultSet resultSet) throws SQLException {
        return new Aircraft(resultSet.getString("aircraft_code"),
                resultSet.getString("model"),
                resultSet.getInt("range"));
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public Set<Aircraft> getAllAircrafts() throws SQLException {
        return source.statement(stmt -> {
            Set<Aircraft> allaircrafts = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from aircrafts");
            while (resultSet.next()) {
                allaircrafts.add(createAircraft(resultSet));
            }
            return allaircrafts;
        });
    }

    /**
     *
     * @param aircrafts
     */
    @Override
    public void saveAircrafts(Collection<Aircraft> aircrafts) throws SQLException {
        source.preparedStatement("insert into aircrafts(aircraft_code, model, range) values (?, ?, ?)",
                insertaircraft -> {
                    for (Aircraft aircraft : aircrafts) {
                        insertaircraft.setString(1, aircraft.getAircraftCode());
                        insertaircraft.setString(2, aircraft.getModel());
                        insertaircraft.setInt(3, aircraft.getRange());
                        insertaircraft.execute();
                    }
                });
    }
}
