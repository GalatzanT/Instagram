package galasefu.lab6incercare1.controller;

import galasefu.lab6incercare1.HelloApplication;
import galasefu.lab6incercare1.domain.CererePrietenie;
import galasefu.lab6incercare1.domain.Utilizator;
import galasefu.lab6incercare1.service.UserFriendsService;
import galasefu.lab6incercare1.utils.events.UtilizatorEvent;
import galasefu.lab6incercare1.utils.observer.Observer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class InstaController implements Observer<UtilizatorEvent> {

    private UserFriendsService userFriendsService;
    private Utilizator owner;

    @FXML private ImageView profileImage;
    @FXML private Label usernameLabel;
    @FXML private Label fullNameLabel;
    @FXML private Label occupationLabel;
    @FXML private Label bioLabel;
    @FXML private Hyperlink websiteLink;
    @FXML private Label postsCountLabel;
    @FXML private Label followersCountLabel;
    @FXML private Label followingCountLabel;
    @FXML private Label followersPreviewLabel;
    @FXML private GridPane postsGrid;
    @FXML private Button followButton;

    @FXML
    private ImageView profileImageView;

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Utilizator> searchResultsListView;

    private ObservableList<Utilizator> list = FXCollections.observableArrayList();




    @FXML
    private Button messageButton;


    @FXML
    public void initialize() {
        // Initialize UI components
        setupButtons();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            performSearch(newValue);
        });
        searchResultsListView.setItems(list);
    }

    private void setupButtons() {
        followButton.setOnAction(event -> handleFollowUnfollow());
        messageButton.setOnAction(event -> handleMessage());
    }

    public void setUserFriendsService(UserFriendsService service) {
        this.userFriendsService = service;
        this.userFriendsService.addObserver(this);
    }

    public void setOwner(Utilizator u) {
        this.owner = u;
        updateProfileInfo();
        loadFollowersPreview();
        updateStatistics();
    }

    private void updateProfileInfo() {
        usernameLabel.setText(owner.getNume());
        fullNameLabel.setText(owner.getPrenume());
        // Set other profile info
        occupationLabel.setText(owner.getPoza());
        bioLabel.setText(owner.getBio());
        postsCountLabel.setText("0");

        // poza
        System.out.println(owner.getPoza());
        String imagePath = owner.getOcupatie();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image profileImage = new Image("file:C:\\Users\\tudor\\IdeaProjects\\lab8\\src\\main\\resources\\galasefu\\lab6incercare1\\images\\" + imagePath); // Prefix "file:" pentru încărcare locală
           profileImageView.setImage(profileImage);
        } else {
            // Imagine implicită dacă utilizatorul nu are una setată
            profileImageView.setImage(new Image("C:\\Users\\tudor\\IdeaProjects\\lab8\\src\\main\\resources\\galasefu\\lab6incercare1\\images\\profil.jpg"));
        }



    }

    private void loadFollowersPreview() {
        StringBuilder preview = new StringBuilder();
        int count = 0;
        for (Utilizator follower : userFriendsService.prieteni_user(owner)) {
            if (count < 2) {
                if (count > 0) preview.append(", ");
                preview.append(follower.getNume());
                count++;
            } else break;
        }
        preview.append(" și încă alții");
        followersPreviewLabel.setText(preview.toString());
    }

    private void updateStatistics() {
        int friendCount = 0;
        for (Utilizator friend : userFriendsService.prieteni_user(owner)) {
            friendCount++;
        }
        followersCountLabel.setText(String.valueOf(friendCount));
        followingCountLabel.setText(String.valueOf(friendCount));
    }

    private void handleFollowUnfollow() {
        // Implement follow/unfollow logic
        if (followButton.getText().equals("Urmărești")) {
            followButton.setText("Urmărește");
            // Implement unfollow logic
        } else {
            followButton.setText("Urmărești");
            // Implement follow logic
        }
    }



    private void performSearch(String query) {
        list.clear();
        if (!query.isEmpty()) {
            Iterable<Utilizator> results = userFriendsService.cautaUseri(query, owner);
            results.forEach(list::add);
        }
    }

    @FXML
    private void handleMessage() {
        Utilizator selectedUser = searchResultsListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            openChatWindow(selectedUser);
        } else {
            System.out.println("No user selected for messaging!");
        }
    }

    private void openChatWindow(Utilizator selectedUser) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/chat-view.fxml"));
            Parent root = loader.load();

            ChatController chatController = loader.getController();
            chatController.initializeChat(owner, selectedUser, userFriendsService);

            Stage stage = new Stage();
            stage.setTitle("Chat with " + selectedUser.getNume());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(UtilizatorEvent event) {
        Platform.runLater(() -> {
            updateStatistics();
            loadFollowersPreview();
        });
    }


}


