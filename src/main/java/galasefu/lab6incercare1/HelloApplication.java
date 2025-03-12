package galasefu.lab6incercare1;

import galasefu.lab6incercare1.controller.HomepageController;

import galasefu.lab6incercare1.controller.InstaController;
import galasefu.lab6incercare1.controller.LoginController;
import galasefu.lab6incercare1.domain.*;
import galasefu.lab6incercare1.domain.validators.UtilizatorValidator;
import galasefu.lab6incercare1.repository.Repository;
import galasefu.lab6incercare1.repository.db.CerereDBrepo;
import galasefu.lab6incercare1.repository.db.MesajDBrepo;
import galasefu.lab6incercare1.repository.db.PrietenieDBrepo;
import galasefu.lab6incercare1.repository.db.UtilizatorDBrepo;
import galasefu.lab6incercare1.repository.page.PagingRepo;
import galasefu.lab6incercare1.repository.page.UtilizatorDBPagingRepo;
import galasefu.lab6incercare1.service.UserFriendsService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class HelloApplication extends Application {

    UserFriendsService service;

    public static void main(String[] args){launch(args);}

    @Override
    public void start(Stage primaryStage) throws IOException {
        String username="postgres";
        String pasword="postgres";
        String url="jdbc:postgresql://localhost:6969/DataBase_lab6";

        //Repository<Long, Utilizator> userRepo = new UtilizatorDBrepo(url,pasword,username, new UtilizatorValidator());
        Repository<Long, Utilizator> userRepo = new UtilizatorDBrepo(url,pasword,username, new UtilizatorValidator());
        Repository<Long, Prietenie> friendsRepo = new PrietenieDBrepo(url,username,pasword);
        Repository<Long, CererePrietenie> cereriRepo = new CerereDBrepo(url,username,pasword);
        Repository<Long, Mesaj> mesajeRepo = new MesajDBrepo(url,username,pasword);
        service =new UserFriendsService(userRepo, friendsRepo, cereriRepo, mesajeRepo);
        //initView(primaryStage);
        //primaryStage.setWidth(800);
        //primaryStage.show();
        initViewLogin(primaryStage);

        //primaryStage.setWidth(1000);
        //primaryStage.setTitle("Aplicatie");
        //primaryStage.show();



    }


    private void openHomePage() throws SQLException, IOException {
        FXMLLoader utilizatorLoader = new FXMLLoader(getClass().getResource("views/homepage-view.fxml"));
        Scene utilizatorScene = new Scene(utilizatorLoader.load());

        HomepageController homepageController= utilizatorLoader.getController();
        System.out.println(LoggedIn.getU().getNume());
        homepageController.setUserFriendsService(service);
        homepageController.setOwner(LoggedIn.getU());

        Stage utilizatorStage = new Stage();
        utilizatorStage.setScene(utilizatorScene);
        utilizatorStage.setTitle("Homepage " + LoggedIn.getU().getNume());
        utilizatorStage.show();



    }







    private void initViewLogin(Stage primaryStage) throws IOException {

        FXMLLoader fmxlLoader = new FXMLLoader(HelloApplication.class.getResource("views/login-view.fxml"));
        Scene loginScene = new Scene(fmxlLoader.load());
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();

        LoginController loginController = fmxlLoader.getController();
        loginController.setService(service,primaryStage);
        loginController.setUtilizator_autentificat(() -> {
            try{
                //openHomePage();
                openInstaPage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private void openInstaPage() throws SQLException, IOException {
        FXMLLoader utilizatorLoader = new FXMLLoader(getClass().getResource("views/insta-profile.fxml"));
        Scene utilizatorScene = new Scene(utilizatorLoader.load());

        InstaController instaController = utilizatorLoader.getController();
        System.out.println(LoggedIn.getU().getNume());
        instaController.setUserFriendsService(service);
        instaController.setOwner(LoggedIn.getU());

        Stage utilizatorStage = new Stage();
        utilizatorStage.setScene(utilizatorScene);
        utilizatorStage.setTitle("Homepage " + LoggedIn.getU().getNume());
        utilizatorStage.show();



    }


}