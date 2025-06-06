package model;

public class Marlon {
    private Room position;
    private boolean isFrozen;

    public Marlon(Room start) {
        this.position = start;
        this.isFrozen = false;
    }

    public Room getPosition() { return position; }

    public void setPosition(Room position) { this.position = position; }

    public boolean isFrozen() { return isFrozen; }

    public void setFrozen(boolean frozen) { isFrozen = frozen; }
}

