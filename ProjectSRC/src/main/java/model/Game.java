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
        setPlayer(new Player(map.getARandomRoom(), Constants.MAX_SCORE));
        setMarlon(new Marlon(map.getARandomRoom()));
        map.assignTrapsAndClues(Constants.TRAPS, Constants.CLUES);
    }

    public void endGame() {
        /*
        ADD THE LOGIC
         */
    }
}

