package controller;

import graphStructures.Edge;
import graphStructures.Node;
import graphStructures.WeightedGraph;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Room;
import resources.BuildMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapController {

    @FXML
    private Canvas mapCanvas;

    private GraphicsContext gc;
    private WeightedGraph<Room> graph;
    private Map<Room, double[]> graphicPositions;
    private Room playerPosition;
    private Map<String, Room> roomsByName = new HashMap<>();



    @FXML
    public void initialize() {
        try {
            System.out.println("Entrando a initialize() de MapController");
            gc = mapCanvas.getGraphicsContext2D();

            loadMapFromJson();
            assignAutomaticPositions();
            drawGraph();

            System.out.println("Mapa dibujado correctamente");

        } catch (Exception e) {
            System.err.println("ERROR en initialize()");
            e.printStackTrace();
        }

        mapCanvas.setOnMouseClicked(event -> {
            double clickX = event.getX();
            double clickY = event.getY();

            for (Room room : graphicPositions.keySet()) {
                double[] pos = graphicPositions.get(room);
                double dx = clickX - (pos[0] + 20);
                double dy = clickY - (pos[1] + 20);
                double distancia = Math.sqrt(dx * dx + dy * dy);

                if (distancia <= 20) {
                    intentarMoverJugador(room);
                    break;
                }
            }
        });
    }

    private void loadMapFromJson() {
        try {
            System.out.println("Cargando JSON desde BuildMap");

            InputStream is = getClass().getResourceAsStream("/util/mapaJSON.json");
            if (is == null) {
                throw new RuntimeException("No se encontró /util/mapaJSON.json");
            }

            File tempFile = File.createTempFile("mapa_temp", ".json");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                byte[] buffer = is.readAllBytes();
                fos.write(buffer);
            }

            graph = BuildMap.loadGraphFromJSON(tempFile.getAbsolutePath());

            roomsByName.clear();
            for (Node<Room> node : graph.getNodes()) {
                roomsByName.put(node.getData().getName(), node.getData());
            }

        } catch (Exception e) {
            System.err.println("ERROR en loadMapFromJson()");
            e.printStackTrace();
        }
    }

    private void drawGraph() {
        gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        gc.setStroke(Color.GRAY);
        for (Edge<Room> edge : graph.getEdges()) {
            double[] from = graphicPositions.get(edge.getFrom());
            double[] to = graphicPositions.get(edge.getTo());
            if (from == null || to == null) continue;

            gc.strokeLine(from[0]+20, from[1]+20, to[0]+20, to[1]+20);
            gc.setFill(Color.BLACK);
            gc.fillText(String.valueOf(edge.getWeight()), (from[0]+to[0])/2, (from[1]+to[1])/2);
        }

        for (Node<Room> node : graph.getNodes()) {
            Room room = node.getData();
            double[] pos = graphicPositions.get(room);
            if (pos == null) continue;

            gc.setFill(room.isSpecial() ? Color.PINK : Color.LIGHTBLUE);
            gc.fillOval(pos[0], pos[1], 40, 40);
            gc.setFill(Color.BLACK);
            gc.fillText(room.getName(), pos[0], pos[1] + 55);
        }
    }

    private void intentarMoverJugador(Room destino) {
        if (destino.equals(playerPosition)) return;

        List<Room> vecinos = graph.getNeighbors(playerPosition);

        if (vecinos.contains(destino)) {
            playerPosition = destino;
            drawGraph();
            System.out.println("Jugador se movió a: " + destino.getName());
        } else {
            System.out.println("Movimiento inválido: no es vecino.");
        }
    }

    private void assignAutomaticPositions() {
        graphicPositions = new HashMap<>();
        int cols = 8;
        int spacing = 90;
        int xStart = 50;
        int yStart = 50;

        List<Node<Room>> nodes = graph.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            int row = i / cols;
            int col = i % cols;
            double x = xStart + col * spacing;
            double y = yStart + row * spacing;
            graphicPositions.put(nodes.get(i).getData(), new double[]{x, y});
        }
    }

    private void moverJugador(String direccion) {
        System.out.println("Mover jugador: " + direccion);
    }
}
