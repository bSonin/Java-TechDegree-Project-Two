package com.teamtreehouse.service;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;

public class TeamService {

    public TeamService() {

    }

    public void addPlayer(Team team, Player player) {
        team.getPlayers().add(player);
    }

    public void removePlayer(Team team, Player player) {
        team.getPlayers().remove(player);
    }
}
