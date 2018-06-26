package com.teamtreehouse.model;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class League {
    private Set<Player> unsignedPlayers;
    private Set<Player> signedPlayers;
    private Set<Player> waitListedPlayers;
    private Set<Team> teams;


    public League(Player[] players) {
        signedPlayers = new TreeSet<>();
        waitListedPlayers = new TreeSet<>();
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

    public Set<Player> getWaitListedPlayers() {
        return waitListedPlayers;
    }

    public void setWaitListedPlayers(Set<Player> waitListedPlayers) {
        this.waitListedPlayers = waitListedPlayers;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }


}
