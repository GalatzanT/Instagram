package galasefu.lab6incercare1.repository.db;

import galasefu.lab6incercare1.domain.Mesaj;
import galasefu.lab6incercare1.domain.Utilizator;
import galasefu.lab6incercare1.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class MesajDBrepo implements Repository<Long, Mesaj> {

    private final String DBurl;
    private final String DBusername;
    private final String DBpassword;

    public MesajDBrepo(String DBurl, String DBusername, String DBpassword) {
        this.DBurl = DBurl;
        this.DBusername = DBusername;
        this.DBpassword = DBpassword;
    }

    private Mesaj createMesajFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Long idUser1 = resultSet.getLong("id_user1");
        Long idUser2 = resultSet.getLong("id_user2");
        String text = resultSet.getString("text");
        Timestamp timestamp = resultSet.getTimestamp("timp");
        LocalDateTime timp = timestamp.toLocalDateTime();
        Long replyId = resultSet.getLong("raspuns_la");
        if (resultSet.wasNull()) {
            replyId = null;
        }
        Utilizator sender = new Utilizator("","","","","","","");
        sender.setId(idUser1);
        Utilizator recipient = new Utilizator("","","","","","","");
        recipient.setId(idUser2);
        List<Utilizator> recipients = new ArrayList<>();
        recipients.add(recipient);

        Mesaj reply = null;
        if (replyId != null) {
            reply = findOne(replyId).orElse(null); // Recursive fetch for the reply message
        }

        Mesaj mesaj = new Mesaj(sender, recipients,timp, text);
        mesaj.setId(id);
        return mesaj;
    }

    @Override
    public Iterable<Mesaj> findAll() {
        Set<Mesaj> mesaje = new HashSet<>();
        String sql = "SELECT * FROM mesaje";
        try (Connection connection = DriverManager.getConnection(DBurl, DBusername, DBpassword);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                Mesaj mesaj = createMesajFromResultSet(resultSet);
                mesaje.add(mesaj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mesaje;
    }

    @Override
    public Optional<Mesaj> save(Mesaj mesaj) {
        String sql = "INSERT INTO mesaje (id_user1, id_user2, text, timp, raspuns_la) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DBurl, DBusername, DBpassword);
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, mesaj.getFrom().getId());
            ps.setLong(2, mesaj.getTo().get(0).getId());
            ps.setString(3, mesaj.getText());
            ps.setTimestamp(4, Timestamp.valueOf(mesaj.getTimp()));
            if (mesaj.getReply() != null) {
                ps.setLong(5, mesaj.getReply().getId());
            } else {
                ps.setNull(5, Types.BIGINT);
            }

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    mesaj.setId(keys.getLong(1));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(mesaj);
    }

    @Override
    public Optional<Mesaj> findOne(Long id) {
        String sql = "SELECT * FROM mesaje WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DBurl, DBusername, DBpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createMesajFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Mesaj> delete(Long id) {
        throw new UnsupportedOperationException("Delete operation not implemented");
    }

    @Override
    public Optional<Mesaj> update(Mesaj entity) {
        throw new UnsupportedOperationException("Update operation not implemented");
    }
}
