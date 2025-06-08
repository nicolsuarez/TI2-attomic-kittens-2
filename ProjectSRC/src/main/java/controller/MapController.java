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
            assignManualPositions();
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

    private double[] getFixedPosition(String name) {
        return switch (name) {
            case "aa" -> new double[]{50, 50};
            case "auditorio" -> new double[]{164.2, 50};
            case "ac" -> new double[]{278.4, 50};
            case "ad" -> new double[]{400,50};
            case "cafeteria_2" -> new double[]{525,50};
            case "af" -> new double[]{637.5,50};
            case "ag" -> new double[]{750,50};
            // ----------------------------------
            case "zonaVerde_1" -> new double[]{50, 150};
            case "bb" -> new double[]{164.2, 150};
            case "cafeteria_1" -> new double[]{278.4, 150};
            case "bd" -> new double[]{400,150};
            case "be" -> new double[]{525,150};
            case "cafeteria_3" -> new double[]{637.5,150};
            case "bg" -> new double[]{750,150};
            // -----------------------------------
            case "ca" -> new double[]{50, 250};
            case "cb" -> new double[]{164.2, 250};
            case "cc" -> new double[]{278.4, 250};
            case "cd" -> new double[]{400,250};
            case "ce" -> new double[]{525,250};
            case "cf" -> new double[]{637.5,250};
            case "cg" -> new double[]{750, 250};
            // ------------------------------------
            case "da" -> new double[]{50, 350};
            case "zonaVerde_2" -> new double[]{164.2, 350};
            case "dc" -> new double[]{278.4, 350};
            case "zonaVerde_3" -> new double[]{400, 350};
            case "de" -> new double[]{525, 350};
            case "df" -> new double[]{637.5, 350};
            case "zonaVerde_4" -> new double[]{750,350};
            // ------------------------------------
            case "ea" -> new double[]{50, 450};
            case "eb" -> new double[]{164.2, 450};
            case "boreal" -> new double[]{278.4, 450};
            case "ed" -> new double[]{400,450};
            case "gym" -> new double[]{525,450};
            case "ef" -> new double[]{637.5,450};
            case "eg" -> new double[]{750,450};
            //----------------------------------
            case "fa" -> new double[]{50, 650};
            case "biblioteca" -> new double[]{164.2, 650};
            case "fc" -> new double[]{278.4, 650};
            case "fd" -> new double[]{400, 650};
            case "oficina_1" -> new double[]{525, 650};
            case "ff" -> new double[]{637.5,650};
            case "oficina_2" -> new double[]{750, 650};
            case "oficina_3" -> new double[]{50, 650};
            // ---------------------------------------
            case "gb" -> new double[]{164.2, 750};
            case "gc" -> new double[]{278.4, 750};
            case "gd" -> new double[]{400, 750};
            case "ge" -> new double[]{525, 750};
            case "gf" -> new double[]{637.5, 750};
            case "gg" -> new double[]{750, 750};
            case "salida" -> new double[]{400, 850};
            default -> null;
        };
    }

    private void assignManualPositions() {
        graphicPositions = new HashMap<>();

        for (Node<Room> node : graph.getNodes()) {
            String name = node.getData().getName();
            double[] pos = getFixedPosition(name);

            if (pos != null) {
                graphicPositions.put(node.getData(), pos);
            } else {
                graphicPositions.put(node.getData(), new double[]{50, 50});
            }
        }
    }

    private void moverJugador(String direccion) {
        System.out.println("Mover jugador: " + direccion);
    }
}
