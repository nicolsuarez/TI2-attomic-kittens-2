package controller;

import auxiliarStructures.LinkedList;
import graphStructures.Algorithms;
import graphStructures.Edge;
import graphStructures.Node;
import graphStructures.WeightedGraph;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Game;
import model.GameMap;
import model.Player;
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
    private Game game;
    private WeightedGraph<Room> graph;
    private Map<Room, double[]> graphicPositions;
    private int turn = 0;
    private int playerMoves = 0;

    @FXML
    public void initialize() {
        try {
            System.out.println("Entrando a initialize() de MapController");
            gc = mapCanvas.getGraphicsContext2D();

            loadMapFromJson();

            game = new Game();
            game.setMap(new GameMap(graph));
            game.startGame();
            graph = game.getMap().getGraph();

            assignManualPositions();
            drawGraph();

            mapCanvas.setOnMouseClicked(event -> {
                double clickX = event.getX();
                double clickY = event.getY();

                for (Room room : graphicPositions.keySet()) {
                    double[] pos = graphicPositions.get(room);
                    double dx = clickX - (pos[0] + 20);
                    double dy = clickY - (pos[1] + 20);
                    double distancia = Math.sqrt(dx * dx + dy * dy);

                    if (distancia <= 20) {
                        moverJugador(room);
                        break;
                    }
                }
            });

            System.out.println("Mapa cargado correctamente");

        } catch (Exception e) {
            System.err.println("ERROR en initialize()");
            e.printStackTrace();
        }
    }

    private void loadMapFromJson() {
        try {
            System.out.println("Cargando JSON desde BuildMap");

            InputStream is = getClass().getResourceAsStream("/util/mapaJSON.json");
            if (is == null) {
                throw new RuntimeException("No se encontró el archivo JSON");
            }

            File tempFile = File.createTempFile("mapa_temp", ".json");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                byte[] buffer = is.readAllBytes();
                fos.write(buffer);
            }

            graph = BuildMap.loadGraphFromJSON(tempFile.getAbsolutePath());

        } catch (Exception e) {
            System.err.println("ERROR en loadMapFromJson()");
            e.printStackTrace();
            throw new RuntimeException("Error al cargar el mapa desde JSON", e);
        }
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

        double maxX = 0, maxY = 0;
        for (double[] pos : graphicPositions.values()) {
            maxX = Math.max(maxX, pos[0]);
            maxY = Math.max(maxY, pos[1]);
        }
        mapCanvas.setWidth(maxX + 100);
        mapCanvas.setHeight(maxY + 100);
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

        double[] playerPos = graphicPositions.get(game.getPlayer().getPosition());
        gc.setFill(Color.BLUE);
        gc.fillOval(playerPos[0]+10, playerPos[1]+10, 20, 20);

        double[] marlonPos = graphicPositions.get(game.getMarlon().getPosition());
        gc.setFill(Color.RED);
        gc.fillOval(marlonPos[0]+10, marlonPos[1]+10, 20, 20);
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
            case "zonaverde_1" -> new double[]{50, 120};
            case "bb" -> new double[]{164.2, 120};
            case "cafeteria_1" -> new double[]{278.4, 120};
            case "bd" -> new double[]{400,120};
            case "be" -> new double[]{525,120};
            case "cafeteria_3" -> new double[]{637.5,120};
            case "bg" -> new double[]{750,120};
            // -----------------------------------
            case "ca" -> new double[]{50, 190};
            case "cb" -> new double[]{164.2, 190};
            case "cc" -> new double[]{278.4, 190};
            case "cd" -> new double[]{400,190};
            case "ce" -> new double[]{525,190};
            case "cf" -> new double[]{637.5,190};
            case "cg" -> new double[]{750, 190};
            // ------------------------------------
            case "da" -> new double[]{50, 260};
            case "zonaverde_2" -> new double[]{164.2, 260};
            case "dc" -> new double[]{278.4, 260};
            case "zonaverde_3" -> new double[]{400, 260};
            case "de" -> new double[]{525, 260};
            case "df" -> new double[]{637.5, 260};
            case "zonaverde_4" -> new double[]{750,260};
            // ------------------------------------
            case "ea" -> new double[]{50, 330};
            case "eb" -> new double[]{164.2, 330};
            case "boreal" -> new double[]{278.4, 330};
            case "ed" -> new double[]{400,330};
            case "gym" -> new double[]{525,330};
            case "ef" -> new double[]{637.5,330};
            case "eg" -> new double[]{750,330};
            //----------------------------------
            case "fa" -> new double[]{50, 400};
            case "biblioteca" -> new double[]{164.2, 400};
            case "fc" -> new double[]{278.4, 400};
            case "fd" -> new double[]{400, 400};
            case "oficina_1" -> new double[]{525, 400};
            case "ff" -> new double[]{637.5,400};
            case "oficina_2" -> new double[]{750,400};
            // ---------------------------------------
            case "oficina_3" -> new double[]{50, 470};
            case "gb" -> new double[]{164.2, 470};
            case "gc" -> new double[]{278.4, 470};
            case "gd" -> new double[]{400, 470};
            case "ge" -> new double[]{525, 470};
            case "gf" -> new double[]{637.5, 470};
            case "gg" -> new double[]{750, 470};
            // -----------------------------------------
            case "salida" -> new double[]{400, 590};
            default -> null;
        };
    }

    private void moverJugador(Room destino) {
        Player player = game.getPlayer();
        Room actual = player.getPosition();

        if (actual.equals(destino)) return;

        List<Room> vecinos = graph.getNeighbors(actual);
        if (vecinos.contains(destino)) {
            player.setPosition(destino);
            turn++;

            if (turn % 2 == 0) moverMarlon();

            drawGraph();

            if (destino.equals(game.getMarlon().getPosition())) {
                System.out.println("Marlon atrapó al jugador");
            }
        } else {
            System.out.println("Movimiento inválido");
        }

        playerMoves++;
        if (playerMoves % 2 == 0) moverMarlon();
    }

    private void moverMarlon() {
        Room playerRoom = game.getPlayer().getPosition();
        Room marlonRoom = game.getMarlon().getPosition();

        LinkedList<Room> path = Algorithms.bfs(graph, marlonRoom, playerRoom);

        if (path.getSize() >= 2) {
            try {
                Room nextRoom = path.search(1);
                game.getMarlon().setPosition(nextRoom);
                System.out.println("Marlon se movió a: " + nextRoom.getName());

                if (nextRoom.equals(playerRoom)) {
                    System.out.println("¡Marlon atrapó al jugador!");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        drawGraph();
    }
}
