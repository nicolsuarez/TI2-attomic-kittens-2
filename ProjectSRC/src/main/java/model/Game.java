package model;

public class Game {
    private GameMap map;
    private Player player;
    private Marlon marlon;
    private int maxScore;

    public Game() {
        this.map = new GameMap();
        this.maxScore = Constants.MAX_SCORE;
    }

    public GameMap getMap() { return map; }

    public void setMap(GameMap map) { this.map = map; }

    public int getMaxScore() { return maxScore; }

    public void setMaxScore(int maxScore) { this.maxScore = maxScore; }

    public Marlon getMarlon() { return marlon; }

    public void setMarlon(Marlon marlon) { this.marlon = marlon; }

    public Player getPlayer() { return player; }

    public void setPlayer(Player player) { this.player = player; }

    public void startGame() {
        Room playerStart = map.getARandomRoom();
        Room marlonStart;

        do {
            marlonStart = map.getARandomRoom();
        } while (playerStart.equals(marlonStart));

        this.player = new Player(playerStart, maxScore);
        this.marlon = new Marlon(marlonStart);
        this.map.assignTrapsAndClues(Constants.TRAPS, Constants.CLUES);
    }

    public void endGame() {
        /*
        ADD THE LOGIC
         */
    }

    public boolean isValidMove(Room from, Room to) {
        return map.getGraph().hasEdge(from, to);
    }
}

