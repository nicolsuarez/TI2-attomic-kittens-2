package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InstructionsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button okeyButton;

    @FXML
    void closeWindow(ActionEvent event) {
        try{
            Stage currentStage = (Stage) okeyButton.getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MapView.fxml"));
            Parent root = loader.load();

            Stage mapStage = new Stage();
            mapStage.setTitle("Mapa del Juego");
            mapStage.setScene(new Scene(root));
            mapStage.setResizable(false);
            mapStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert okeyButton != null : "fx:id=\"okeyButton\" was not injected: check your FXML file 'instructions_view.fxml'.";

    }

}

