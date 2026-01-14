package boardgame;

public class Team {

    private String name;

    protected Team(String name) {
        this.name = name;
    }


    public boolean isOpponent(Team other) {
        if (other == null) return false;
        return !this.name.equals(other.getName());
    }


    public String getName() {
        return name;
    }


    public String toString() {
        return name;
    }
}
