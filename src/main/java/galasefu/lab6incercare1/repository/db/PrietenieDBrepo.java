package galasefu.lab6incercare1.repository.db;

import galasefu.lab6incercare1.domain.Prietenie;
import galasefu.lab6incercare1.repository.Repository;

import java.io.PipedReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PrietenieDBrepo implements Repository<Long, Prietenie> {

    private String DBurl;
    private String DBusername;
    private String DBpassword;

    public PrietenieDBrepo(String DBurl, String DBusername, String DBpassword) {
        this.DBurl = DBurl;
        this.DBusername = DBusername;
        this.DBpassword = DBpassword;
    }


    public Prietenie createPrietenieFromResultSet(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            Long u1 = resultSet.getLong("id_user1");
            Long u2 = resultSet.getLong("id_user2");
            Timestamp date = resultSet.getTimestamp("friends_from");
            LocalDateTime friendsFrom = date.toLocalDateTime();
            Prietenie p = new Prietenie(u1, u2, friendsFrom);
            p.setId(id);
            return p;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Prietenia nu a putut fi creata din result Setul tau imi pare foarte rau");
            return null;
        }
    }

    @Override
    public Optional<Prietenie> findOne(Long id) {
        Prietenie prietenie;
        String sql = "select * from prietenii P where P.id = ?";
        try (Connection connection = DriverManager.getConnection(DBurl, DBusername, DBpassword); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                prietenie = createPrietenieFromResultSet(resultSet);
                return Optional.ofNullable(prietenie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Prietenie> findAll() {
        Set<Prietenie> prietenii = new HashSet<>();
        Prietenie prietenie;
        try (Connection connection = DriverManager.getConnection(DBurl, DBusername, DBpassword); PreparedStatement ps = connection.prepareStatement("select * from prietenii"); ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                prietenie = createPrietenieFromResultSet(resultSet);
                prietenii.add(prietenie);
            }
            return prietenii;

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return prietenii;
    }

    @Override
    public Optional<Prietenie> save(Prietenie prietenie) {
        String insertsql = "insert into prietenii (id_user1,id_user2,friends_from) values(?,?,?)";
        String check1sql = "select count(*) from prietenii where id_user1 = ? and id_user2=?";
        try (Connection connection = DriverManager.getConnection(DBurl, DBusername, DBpassword); PreparedStatement insert = connection.prepareStatement(insertsql); PreparedStatement check1 = connection.prepareStatement(check1sql)) {

            check1.setLong(1, prietenie.getIdUser1());
            check1.setLong(2, prietenie.getIdUser2());
            ResultSet resultSet = check1.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("Deja esti prieten cu el");
                return Optional.of(prietenie);
            }
            check1.setLong(2, prietenie.getIdUser1());
            check1.setLong(1, prietenie.getIdUser2());
            resultSet = check1.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("Deja esti prieten cu el");
                return Optional.of(prietenie);
            }

            insert.setLong(1, prietenie.getIdUser1());
            insert.setLong(2, prietenie.getIdUser2());
            insert.setTimestamp(3, Timestamp.valueOf(prietenie.getFriendsFrom()));
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Prietenie> delete(Long id) {
        String sql = "delete from prietenii P where P.id = ?";
        try (Connection connection = DriverManager.getConnection(DBurl, DBusername, DBpassword); PreparedStatement ps = connection.prepareStatement(sql)) {
            Optional<Prietenie> p = findOne(id);
            if (!p.isEmpty()) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }
            return p;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Prietenie> update(Prietenie entity) {
        return Optional.empty();
    }
}
