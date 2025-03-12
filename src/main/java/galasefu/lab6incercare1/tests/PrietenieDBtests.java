package galasefu.lab6incercare1.tests;

import galasefu.lab6incercare1.domain.Prietenie;
import galasefu.lab6incercare1.repository.db.PrietenieDBrepo;

import java.lang.ref.PhantomReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Optional;

public class PrietenieDBtests {

    public void test() {
        System.out.println("--- Starting Friendship DB tests ---");

        // Database connection parameters
        String DBurl = "jdbc:postgresql://localhost:6969/DataBase_lab6";
        String DBusername = "postgres";
        String DBpassword = "postgres";





        try (Connection connection = DriverManager.getConnection(DBurl, DBusername, DBpassword);
             Statement stmt = connection.createStatement()) {
            stmt.execute("SELECT setval('prietenii_id_seq', 1, false);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Initialize the repository and validator
        PrietenieDBrepo prietenieDBrepo = new PrietenieDBrepo(DBurl, DBusername, DBpassword);





        Prietenie p1 = new Prietenie(1L, 2L, LocalDateTime.now());
        Prietenie p2 = new Prietenie(2L, 3L, LocalDateTime.now());

        prietenieDBrepo.save(p1);
        prietenieDBrepo.save(p2);

        Prietenie p3 = new Prietenie(4L, 5L, LocalDateTime.now());
        Prietenie p4 = new Prietenie(5L, 4L, LocalDateTime.now());
        prietenieDBrepo.save(p3);
        prietenieDBrepo.save(p4);


        Optional<Prietenie> p = prietenieDBrepo.findOne(2L);
        prietenieDBrepo.delete(2L);
        prietenieDBrepo.save(p2);
        Iterable<Prietenie> prietenii = prietenieDBrepo.findAll();
        Prietenie p11 = new Prietenie(1L, 8L, LocalDateTime.now());
        Prietenie p12 = new Prietenie(1L, 8L, LocalDateTime.now());
        prietenieDBrepo.save(p11);
        prietenieDBrepo.save(p12);
        for(Prietenie pri : prietenii)
            System.out.println(pri);




        System.out.println("--- finished friends test ---");
    }
}
