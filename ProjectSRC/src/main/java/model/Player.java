package model;

public class Player {
    private Room position;
    private int score;
    private boolean isFrozen;

    public Player(Room start, int startScore) {
        this.position = start;
        this.score = startScore;
        this.isFrozen = false;
    }

    public Room getPosition() { return position; }

    public void setPosition(Room position) { this.position = position; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public boolean isFrozen() { return isFrozen; }

    public void setFrozen(boolean frozen) { isFrozen = frozen; }
}

