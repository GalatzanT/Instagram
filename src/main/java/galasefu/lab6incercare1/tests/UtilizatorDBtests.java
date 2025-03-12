package galasefu.lab6incercare1.tests;

import galasefu.lab6incercare1.domain.Utilizator;
import galasefu.lab6incercare1.domain.validators.UtilizatorValidator;
import galasefu.lab6incercare1.repository.db.UtilizatorDBrepo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UtilizatorDBtests {

    public void test() {
        System.out.println("--- Starting User DB tests ---");

        String DBurl = "jdbc:postgresql://localhost:6969/DataBase_lab6";
        String DBusername = "postgres";
        String DBpassword = "postgres";

        UtilizatorDBrepo DBrepo = new UtilizatorDBrepo(DBurl, DBpassword, DBusername, new UtilizatorValidator());

        // Add 10 users
        for (int i = 1; i <= 10; i++) {
            Utilizator user = new Utilizator("lastName" + i, "firstName" + i, "user" + i + "@example.com", "password" + i,"","","");
            Optional<Utilizator> saveResult = DBrepo.save(user);
            if (saveResult.isEmpty()) {
                System.out.println("User " + i + " saved successfully.");
            } else {
                System.out.println("User " + i + " already exists or could not be saved.");
            }
        }

        // Find a specific user
        Optional<Utilizator> foundUser = DBrepo.findOne(5L); // Assuming IDs are sequential and start from 1
        if (foundUser.isPresent()) {
            System.out.println("FindOne test passed: Found user with ID 5 -> " + foundUser.get());
        } else {
            System.out.println("FindOne test failed: User with ID 5 not found.");
        }

        // Retrieve all users
        Iterable<Utilizator> allUsers = DBrepo.findAll();
        System.out.println("FindAll test: Retrieved all users.");
        for (Utilizator user : allUsers) {
            System.out.println(user);
        }

        // Update a user
        Utilizator userToUpdate = new Utilizator("updatedLastName", "updatedFirstName", "updatedUser@example.com", "updatedPassword","","","");
        userToUpdate.setId(5L); // Assuming user with ID 5 exists
        Optional<Utilizator> updateResult = DBrepo.update(userToUpdate);
        if (updateResult.isEmpty()) {
            System.out.println("Update test passed: User with ID 5 updated successfully.");
        } else {
            System.out.println("Update test failed: User with ID 5 could not be updated.");
        }

        // Delete specific users
        for (int i = 1; i <= 20; i++) {
            Optional<Utilizator> deleteResult = DBrepo.delete((long) i);
            if (deleteResult.isPresent()) {
                System.out.println("Delete test passed: User with ID " + i + " deleted successfully.");
            } else {
                System.out.println("Delete test failed: User with ID " + i + " could not be deleted.");
            }
        }

        // Verify remaining users
        System.out.println("Remaining users after deletions:");
        allUsers = DBrepo.findAll();
        for (Utilizator user : allUsers) {
            System.out.println(user);
        }


        try (Connection connection = DriverManager.getConnection(DBurl, DBusername, DBpassword);
             Statement stmt = connection.createStatement()) {
            stmt.execute("SELECT setval('utilizatori_id_seq', 1, false);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        Utilizator user1= new Utilizator("galatan","tudor","tudorbosuu@gmail.com","123456");
//        Utilizator user2= new Utilizator("ana","maria","tudorbosuuana@gmail.com","123456");
//        Utilizator user3= new Utilizator("baicu","andrei","tudorbosuugabu@gmail.com","123456");
//        Utilizator user4= new Utilizator("claus","mirela","tudorboasdsuu@gmail.com","123456");
//        Utilizator user5= new Utilizator("lapovita","dana","tasudorbosuu@gmail.com","123456");
//        Utilizator user6= new Utilizator("marian","darius","5tudorbosuu@gmail.com","123456");
//        Utilizator user7= new Utilizator("popa","alexandra","4tudorbosuuana@gmail.com","123456");
//        Utilizator user8= new Utilizator("lucacel","andreius","3tudorbosuugabu@gmail.com","123456");
//        Utilizator user9= new Utilizator("berar","cristi","2tudorboasdsuu@gmail.com","123456");
//        Utilizator user10= new Utilizator("ciucas","andru","1tasudorbosuu@gmail.com","123456");
//        DBrepo.save(user1);
//        DBrepo.save(user2);
//        DBrepo.save(user3);
//        DBrepo.save(user4);
//        DBrepo.save(user5);
//        DBrepo.save(user6);
//        DBrepo.save(user7);
//        DBrepo.save(user8);
//        DBrepo.save(user9);
//        DBrepo.save(user10);
        System.out.println("--- User DB tests ended ---");
    }
}
