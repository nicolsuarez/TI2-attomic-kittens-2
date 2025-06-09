package model;

import graphStructures.*;
import resources.BuildMap;

import java.util.ArrayList;
import java.util.Collections;
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
        List<Node<Room>> rooms = new ArrayList<>(graph.getNodes());
        Collections.shuffle(rooms);

        int trapCount = 0;
        int clueCount = 0;
        for (Node<Room> node : rooms) {
            Room room = node.getData();
            if (!room.isSpecial() && !room.isHasClue() && !room.isHasTrap()) {
                if (trapCount < trapsNum) {
                    room.setHasTrap(true);
                    trapCount++;
                } else if (clueCount < cluesNum) {
                    room.setHasClue(true);
                    clueCount++;
                }

                if (trapCount == trapsNum && clueCount == cluesNum) break;
            }
        }
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
