package com.teamtreehouse.model;

import java.util.*;

public class League {
    private Set<Player> unsignedPlayers;
    private Set<Player> signedPlayers;
    private Queue<Player> waitListedPlayers;
    private Set<Team> teams;
    // TODO: Consider adding a static playersPerTeam max, which would need to be enforced during adding
    //       Will make check for number teams vs players easier


    public League(Player[] players) {
        signedPlayers = new TreeSet<>();
        waitListedPlayers = new LinkedList<>();
        teams = new TreeSet<>();
        unsignedPlayers = new TreeSet<>(Arrays.asList(players));
    }

    public Set<Player> getSignedPlayers() {
        return signedPlayers;
    }

    public void setSignedPlayers(Set<Player> signedPlayers) {
        this.signedPlayers = signedPlayers;
    }

    public Set<Player> getUnsignedPlayers() {
        return unsignedPlayers;
    }

    public void setUnsignedPlayers(Set<Player> unsignedPlayers) {
        this.unsignedPlayers = unsignedPlayers;
    }

    public Queue<Player> getWaitListedPlayers() {
        return waitListedPlayers;
    }

    public void setWaitListedPlayers(Queue<Player> waitListedPlayers) {
        this.waitListedPlayers = waitListedPlayers;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }


}
