package model;

import graphStructures.*;
import resources.BuildMap;

import java.io.InputStream;
import java.util.List;

public class GameMap {
    private WeightedGraph<Room> graph;

    public GameMap() {
        graph = BuildMap.loadGraphFromJSON("util/mapaJSON.json");
    }

    public GameMap(WeightedGraph<Room> graph) {
        this.graph = graph;
    }

    public Room getARandomRoom() {
        List<Node<Room>> allNodes = graph.getNodes();
        int randomIndex = (int) (Math.random() * allNodes.size());
        return allNodes.get(randomIndex).getData();
    }

    public void assignTrapsAndClues(int trapsNum, int cluesNum) {
        /*
        ADD THE LOGIC TO ASSIGN THE TRAPS AND CLUES ON THE MAP
        */
    }

    public boolean isValidMove() {
        /*
        ADD THE LOGIC
        */
        return false;
    }

    public WeightedGraph<Room> getGraph() {
        return graph;
    }
}
