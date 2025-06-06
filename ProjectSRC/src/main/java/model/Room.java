package model;

public class Room {
    private String name;
    private boolean hasTrap;
    private boolean hasClue;
    private boolean isSpecial;

    public Room() { }

    public Room(String name, boolean isSpecial) {
        this.name = name;
        this.hasTrap = false;
        this.hasClue = false;
        this.isSpecial = isSpecial;
    }

    public Room(String name, boolean hasTrap, boolean hasClue, boolean isSpecial) {
        this.name = name;
        this.hasTrap = hasTrap;
        this.hasClue = hasClue;
        this.isSpecial = isSpecial;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public boolean isHasTrap() { return hasTrap; }

    public void setHasTrap(boolean hasTrap) { this.hasTrap = hasTrap; }

    public boolean isSpecial() { return isSpecial; }

    public void setSpecial(boolean special) { isSpecial = special; }

    public boolean isHasClue() { return hasClue; }

    public void setHasClue(boolean hasClue) { this.hasClue = hasClue; }
}