package com.teamtreehouse.model;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Team implements Comparable<Team> {

    private String teamName;
    private String coach;
    private Set<Player> players;

    public Team(String teamName, String coach) {
        this.teamName = teamName;
        this.coach = coach;
        players = new TreeSet<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public String getCoach() {
        return coach;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    @Override
    public int compareTo(Team other) {
        return this.teamName.compareTo(other.getTeamName());
    }

    // Utility Methods
    public int getNumberExperiencedPlayers() {
        int numExperienced = 0;
        for (Player player : players) {
            numExperienced = player.isPreviousExperience() ? ++numExperienced : numExperienced;
        }
        return numExperienced;
    }

    public double getTeamExperienceLevel() {
        return (double) getNumberExperiencedPlayers() / (double) players.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(teamName, team.teamName) &&
                Objects.equals(coach, team.coach) &&
                Objects.equals(players, team.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName, coach, players);
    }
}
