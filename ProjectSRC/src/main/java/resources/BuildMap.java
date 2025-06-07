package resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

// models
import customExceptions.MapLoadingException;
import graphStructures.WeightedGraph;
import model.Room;

public class BuildMap {

    /*
    It's used the GSON library to work with the .json
    Classes DTO are used to move the data from the JSON to the graph. (DTO = Data Transfer Object)
     */
    public static WeightedGraph<Room> loadGraphFromJSON(String path) throws MapLoadingException {

        Gson gson = new GsonBuilder().create();

        try (Reader reader = new FileReader(path)) {

            MapDTO dto = gson.fromJson(reader, MapDTO.class);
            WeightedGraph<Room> graph = new WeightedGraph<>();
            Map<String, Room> roomMap = new HashMap<>(); // To save the unique references on memory

            // Create the nodes
            for (RoomDTO roomDTO : dto.nodes) {
                Room room = new Room(roomDTO.name, roomDTO.hasTrap, roomDTO.hasClue, roomDTO.isSpecial);
                graph.addNode(room);
                roomMap.put(room.getName(), room); // To save the unique references on memory
            }

            // Create the edges
            for (EdgeDTO edgeDTO : dto.edges) {
                Room from = roomMap.get(edgeDTO.from); // get the value from the map
                Room to = roomMap.get(edgeDTO.to);
                graph.addEdge(from, to, edgeDTO.weight);
            }

            return graph;

        } catch (IOException e) {
            throw new MapLoadingException("Failed to read the file." );
        }
    }
}

