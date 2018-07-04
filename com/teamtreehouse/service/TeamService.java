package com.teamtreehouse.service;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;

import java.util.*;

public class TeamService {

    public TeamService() {

    }

    public void addPlayer(Team team, Player player) {
        team.getPlayers().add(player);
    }

    public void removePlayer(Team team, Player player) {
        team.getPlayers().remove(player);
    }

    public Map<String, Set<Player>> groupPlayersByHeight(Team team) {
        Map<String, Set<Player>> playersGroupedByHeight = new HashMap<>();
        playersGroupedByHeight.put("small", new HashSet<>());
        playersGroupedByHeight.put("medium", new HashSet<>());
        playersGroupedByHeight.put("tall", new HashSet<>());

        for (Player player : team.getPlayers()) {
            int height = player.getHeightInInches();
            if (height > 34 && height < 41) {
                playersGroupedByHeight.get("small").add(player);
            }
            else if (height > 40 && height < 47) {
                playersGroupedByHeight.get("medium").add(player);
            }
            else if (height > 46 && height < 51) {
                playersGroupedByHeight.get("tall").add(player);
            }
        }
        return playersGroupedByHeight;
    }
}
