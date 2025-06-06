package model;

import graphStructures.*;
import resources.BuildMap;

public class GameMap {
    private WeightedGraph<Room> graph;

    public GameMap() {
        graph = BuildMap.loadGraphFromJSON("util/mapaJSON.json");
    }

    public Room getARandomRoom() {
        /*
        ADD THE LOGIC TO GET A RANDOM ROOM OF THE MAP
         */
        return new Room();
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
}
