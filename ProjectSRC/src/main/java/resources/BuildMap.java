package resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
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
    public static WeightedGraph<Room> loadGraphFromJSON(String resourcePath) throws MapLoadingException {
        Gson gson = new GsonBuilder().create();

        try (InputStream is = BuildMap.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new MapLoadingException("No se encontró el recurso: " + resourcePath);
            }

            try (Reader reader = new InputStreamReader(is)) {
                MapDTO dto = gson.fromJson(reader, MapDTO.class);
                if (dto == null) throw new MapLoadingException("El archivo JSON está vacío o malformado.");

                WeightedGraph<Room> graph = new WeightedGraph<>();
                Map<String, Room> roomMap = new HashMap<>();

                for (RoomDTO roomDTO : dto.nodes) {
                    Room room = new Room(roomDTO.name, roomDTO.hasTrap, roomDTO.hasClue, roomDTO.isSpecial);
                    graph.addNode(room);
                    roomMap.put(room.getName(), room);
                }

                for (EdgeDTO edgeDTO : dto.edges) {
                    Room from = roomMap.get(edgeDTO.from);
                    Room to = roomMap.get(edgeDTO.to);
                    graph.addEdge(from, to, edgeDTO.weight);
                }

                return graph;
            }
        } catch (IOException e) {
            throw new MapLoadingException("Error al leer el archivo JSON: " + e.getMessage());
        }
    }

}

