package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            URL instructionsUrl = getClass().getResource("/fxml/InstructionsView.fxml");
            if (instructionsUrl == null) {
                throw new IllegalStateException("¡Archivo InstructionsView.fxml no encontrado!");
            }

            FXMLLoader instructionLoader = new FXMLLoader(instructionsUrl);
            Parent instructionRoot = instructionLoader.load();

            Stage instructionsStage = new Stage();
            instructionsStage.setTitle("Instrucciones del Juego");
            instructionsStage.setScene(new Scene(instructionRoot));
            instructionsStage.setResizable(false);
            instructionsStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

