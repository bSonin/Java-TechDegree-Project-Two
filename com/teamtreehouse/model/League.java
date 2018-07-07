package com.teamtreehouse.model;

import java.util.*;

public class League {
    public static final int MAX_PLAYERS_PER_TEAM = 11;
    private Set<Player> unsignedPlayers;
    private Set<Player> signedPlayers;
    private Queue<Player> waitListedPlayers;
    private Set<Team> teams;

    public League(Player[] players) {
        signedPlayers = new TreeSet<>();
        waitListedPlayers = new LinkedList<>();
        teams = new TreeSet<>();
        unsignedPlayers = new TreeSet<>(Arrays.asList(players));
    }

    public Set<Player> getSignedPlayers() {
        return signedPlayers;
    }

    public Set<Player> getUnsignedPlayers() {
        return unsignedPlayers;
    }

    public Queue<Player> getWaitListedPlayers() {
        return waitListedPlayers;
    }

    public Set<Team> getTeams() {
        return teams;
    }

}
