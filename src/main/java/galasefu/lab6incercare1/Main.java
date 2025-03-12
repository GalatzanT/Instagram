package galasefu.lab6incercare1;
import galasefu.lab6incercare1.domain.*;
import galasefu.lab6incercare1.domain.validators.UtilizatorValidator;
import galasefu.lab6incercare1.domain.validators.ValidationException;
import galasefu.lab6incercare1.domain.validators.Validator;
import galasefu.lab6incercare1.repository.db.UtilizatorDBrepo;
import galasefu.lab6incercare1.tests.PrietenieDBtests;
import galasefu.lab6incercare1.tests.UtilizatorDBtests;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        HelloApplication.main(args);

        /*
        Utilizator u1 = new Utilizator("galatan","tudor","tudor@gmail.com","ana123");
        System.out.println(u1);

        Validator<Utilizator> validator_user = new UtilizatorValidator();
        try{
            validator_user.validate(u1);
        }catch (ValidationException e){
            System.out.println(e.getMessage());
        }

        UtilizatorDBtests testeDBuser = new UtilizatorDBtests();
        testeDBuser.test();
        PrietenieDBtests diddyParty = new PrietenieDBtests();
        diddyParty.test();
        */


    }
}
