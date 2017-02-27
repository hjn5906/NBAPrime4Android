package jegan.nbaprimetime;

public class NBALeaders {
    private String displayName, position, points; // Daily leading player's stats

    public NBALeaders(String displayName, String position, String points) {
        this.setDisplayName(displayName);
        this.setPosition(position);
        this.setPoints(points);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
