package galasefu.lab6incercare1.controller;

import galasefu.lab6incercare1.domain.LoggedIn;
import galasefu.lab6incercare1.domain.Utilizator;
import galasefu.lab6incercare1.service.UserFriendsService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    private UserFriendsService service;
    private Stage primaryStage;

    @FXML
    private TextField cauta_nume;

    @FXML
    private TextField cauta_parola;

    @FXML
    private Label erori;

    private Runnable utilizatorAutentificat;

    public void setService(UserFriendsService service,   Stage stage) {
        this.service = service;
        this.primaryStage = stage;
    }

    public void setUtilizator_autentificat(Runnable utilizatorAutentificat) {
        this.utilizatorAutentificat = utilizatorAutentificat;
    }


    @FXML
    public void handleLogin() {
        String nume = cauta_nume.getText().trim();
        String parola = cauta_parola.getText().trim();
        if (nume.isEmpty() && parola.isEmpty()) {
            erori.setText("Nume si parola necompletata");
            return;
        }
        if (nume.isEmpty()) {
            erori.setText("Nume necompletat");
            return;
        }
        if (parola.isEmpty()) {
            erori.setText("Parola necompletata");
            return;
        }
        try {
            Utilizator user = service.findByName(nume);
            if (user != null && user.getPassword().equals(parola)) {
                LoggedIn.setU(user);
                if (utilizatorAutentificat != null) {
                    utilizatorAutentificat.run();
                }
            } else{
                erori.setText("Nume, parola gresite");
            }
        } catch (Exception e) {
            e.printStackTrace();
            erori.setText("Eroare la autentificare");
        }
    }
}
