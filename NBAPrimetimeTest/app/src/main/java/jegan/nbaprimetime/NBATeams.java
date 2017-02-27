package jegan.nbaprimetime;


public class NBATeams {
    private String teamName, teamPrefix, conference, division;

    public NBATeams(String teamPrefix, String teamName, String conference, String division) {
        this.setTeamPrefix(teamPrefix);
        this.setTeamName(teamName);
        this.setConference(conference);
        this.setDivision(division);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamPrefix() {
        return teamPrefix;
    }

    public void setTeamPrefix(String teamPrefix) {
        this.teamPrefix = teamPrefix;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
}
