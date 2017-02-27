package jegan.nbaprimetime;


public class NBATeamsRoster {
    private String playerName, position, age, height, weight;

    public NBATeamsRoster(String playerName, String position, String age, String height, String weight) {
        this.setPlayerName(playerName);
        this.setPlayerPosition(position);
        this.setPlayerAge(age);
        this.setPlayerHeight(height);
        this.setPlayerWeight(weight);
    }

    public String getPlayerPosition() {
        return position;
    }

    public void setPlayerPosition(String position) {
        this.position = position;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerAge() {
        return age;
    }

    public void setPlayerAge(String age) {
        this.age = age;
    }

    public String getPlayerHeight() {
        return height;
    }

    public void setPlayerHeight(String height) {
        this.height = height;
    }

    public void setPlayerWeight(String weight) {
        this.weight = weight;
    }

    public String getPlayerWeight() {
        return weight;
    }
}
