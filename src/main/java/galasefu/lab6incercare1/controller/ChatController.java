package galasefu.lab6incercare1.controller;

import galasefu.lab6incercare1.domain.Mesaj;
import galasefu.lab6incercare1.domain.Utilizator;
import galasefu.lab6incercare1.service.UserFriendsService;
import galasefu.lab6incercare1.utils.events.UtilizatorEvent;
import galasefu.lab6incercare1.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.util.List;

public class ChatController implements Observer<UtilizatorEvent> {

    @FXML
    private Label recipientNameLabel;

    @FXML
    private ListView<String> messageListView;

    @FXML
    private TextField messageTextField;

    private Utilizator sender;
    private Utilizator recipient;
    private UserFriendsService service;

    private ObservableList<String> messages = FXCollections.observableArrayList();


    public void initializeChat(Utilizator sender, Utilizator recipient, UserFriendsService service) {
        this.sender = sender;
        this.recipient = recipient;
        this.service = service;
        recipientNameLabel.setText(recipient.getNume() + " " + recipient.getPrenume());
        loadMessages();
        this.service.addObserver(this);
    }

    private void loadMessages() {
        messages.clear();
        Iterable<Mesaj> chatMessages = service.getMessages(sender, recipient);
        for (Mesaj mesaj : chatMessages) {
            messages.add(mesaj.toString());
        }
        messageListView.setItems(messages);
    }



    @FXML
    private void handleSendMessage() {
        String text = messageTextField.getText().trim();
        if (!text.isEmpty()) {
            Mesaj newMessage = new Mesaj(sender, List.of(recipient),LocalDateTime.now(), text);
            service.sendMessage(newMessage);
            messageTextField.clear();
        }
    }

    @Override
    public void update(UtilizatorEvent utilizatorEvent) {

        loadMessages();
    }
}
