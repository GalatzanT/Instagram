package galasefu.lab6incercare1.repository.db;

import galasefu.lab6incercare1.domain.CererePrietenie;
import galasefu.lab6incercare1.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CerereDBrepo implements Repository<Long, CererePrietenie> {

    private String DBurl;
    private String DBusername;
    private String DBpassword;

    public CerereDBrepo(String DBurl, String DBusername, String DBpassword) {
        this.DBurl = DBurl;
        this.DBusername = DBusername;
        this.DBpassword = DBpassword;
    }

    private CererePrietenie createCerereFromResultSet(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            Long idUser1 = resultSet.getLong("id_user1");
            Long idUser2 = resultSet.getLong("id_user2");
            Timestamp timestamp = resultSet.getTimestamp("time");
            String status = resultSet.getString("status");
            LocalDateTime time = timestamp.toLocalDateTime();

            CererePrietenie cerere = new CererePrietenie(idUser1, idUser2, time, status);
            cerere.setId(id);
            return cerere;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create CererePrietenie from ResultSet.");
            return null;
        }
    }

    @Override
    public Optional<CererePrietenie> findOne(Long id) {
        String sql = "SELECT * FROM cereri WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DBurl, DBusername, DBpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                CererePrietenie cerere = createCerereFromResultSet(resultSet);
                return Optional.ofNullable(cerere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<CererePrietenie> findAll() {
        Set<CererePrietenie> cereri = new HashSet<>();
        String sql = "SELECT * FROM cereri";
        try (Connection connection = DriverManager.getConnection(DBurl, DBusername, DBpassword);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                CererePrietenie cerere = createCerereFromResultSet(resultSet);
                cereri.add(cerere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cereri;
    }

    @Override
    public Optional<CererePrietenie> save(CererePrietenie cerere) {
        String sql = "INSERT INTO cereri (id_user1, id_user2, time, status) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DBurl, DBusername, DBpassword);
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, cerere.getId_user1());
            ps.setLong(2, cerere.getId_user2());
            ps.setTimestamp(3, Timestamp.valueOf(cerere.getDate()));
            ps.setString(4, cerere.getStatus());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    cerere.setId(keys.getLong(1));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(cerere);
    }

    @Override
    public Optional<CererePrietenie> delete(Long id) {
        Optional<CererePrietenie> cerere = findOne(id);
        if (cerere.isPresent()) {
            String sql = "DELETE FROM cereri WHERE id = ?";
            try (Connection connection = DriverManager.getConnection(DBurl, DBusername, DBpassword);
                 PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setLong(1, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cerere;
    }

    @Override
    public Optional<CererePrietenie> update(CererePrietenie cerere) {
        String sql = "UPDATE cereri SET status = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DBurl, DBusername, DBpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, cerere.getStatus());
            ps.setLong(2, cerere.getId());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(cerere);
    }
}
