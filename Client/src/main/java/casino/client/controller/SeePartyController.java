package casino.client.controller;

import Model.SocketSingleton;
import Model.StageSingleton;
import Model.UserSingleton;
import casino.client.task.JoinPartyTask;
import casino.client.task.LeavePartyTask;
import casino.client.task.RefreshPartyListTask;
import com.casino.entity.GameInfo;
import com.casino.packet.AskInfoPacket;
import com.casino.packet.GameInfoPacket;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class SeePartyController implements RefreshPartyListTask.PartyListResponse,
                                            JoinPartyTask.JoinPartyResponse {

    public ObservableList<GameInfo> games = FXCollections.observableArrayList();
    public ListView<GameInfo> partyListView = new ListView<>(games);
    public Button refreshButton;
    public Button backButton;
    public AnchorPane partyListViewContainer;
    public Button joinParty;
    public Label moneyLabel;

    @FXML
    void initialize(){


        try {

            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    moneyLabel.setText( UserSingleton.getInstance().getArgent() + "€" );

                    refresh();
                }
            });

        } catch ( Exception e ) {
            System.out.println("Apparu a GameController - init");
            System.out.println(e.getMessage());
        }
    }

    public void back() {
        StageSingleton.getInstance().changeScene("home-view.fxml");
    }
    
    public void refresh() {
        try {
            RefreshPartyListTask refreshPartyListTask = new RefreshPartyListTask(this);
            System.out.println("RESFRESH REQUEST");
        } catch (Exception e ) {
            System.out.println("Apparu à SeePartyController - refresh");
            System.out.println(e.getMessage());
        }
    }

    public void actualizePartyList() {

        try {
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    partyListView.setItems( games );
                    partyListView.setPrefSize( Double.MAX_VALUE, Double.MAX_VALUE );
                    partyListViewContainer.getChildren().clear();
                    partyListViewContainer.getChildren().addAll(partyListView);
                }
            });

            this.partyListView.refresh();
        } catch ( Exception e) {
            System.out.println("Apparu à SeePartyController - actualizePartyList");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void didReceiveList(ObservableList<GameInfo> partyList) {
        this.games = partyList;
        this.actualizePartyList();
    }

    @Override
    public void didFailedGettingList(String errorMsg) {

    }

    public void joinParty() {
        try {

            GameInfo game = partyListView.getSelectionModel().getSelectedItem();

            UserSingleton.getInstance().setGame(game);

            JoinPartyTask joinPartyTask = new JoinPartyTask(this, game);

        } catch ( Exception e) {
            System.out.println("Apparu a SeePartyController - joinParty");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void didJoined() {
        try {
            StageSingleton.getInstance().changeScene("game-view.fxml");
        } catch (Exception e) {
            System.out.println("Apparu a SeePartyController - didJoined");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void didFailedToJoin(String errorMsg) {

    }
}
