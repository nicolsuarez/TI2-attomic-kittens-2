package controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class MapController {

    @FXML
    private Canvas mapCanvas;

    @FXML
    private Button moveUpBtn, moveDownBtn, moveLeftBtn, moveRightBtn;

    private GraphicsContext gc;

    @FXML
    public void initialize() {
        gc = mapCanvas.getGraphicsContext2D();
        dibujarMapaInicial();

        moveUpBtn.setOnAction(e -> moverJugador("UP"));
        moveDownBtn.setOnAction(e -> moverJugador("DOWN"));
        moveLeftBtn.setOnAction(e -> moverJugador("LEFT"));
        moveRightBtn.setOnAction(e -> moverJugador("RIGHT"));
    }

    private void dibujarMapaInicial() {
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        gc.setFill(Color.BLUE);
        gc.fillOval(100, 100, 30, 30); // jugador (ejemplo)
    }

    private void moverJugador(String direccion) {
        System.out.println("Mover jugador: " + direccion);
    }
}
