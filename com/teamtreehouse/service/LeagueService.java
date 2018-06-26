package com.teamtreehouse.service;

import com.teamtreehouse.model.League;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;

import java.util.Set;

public class LeagueService {
    private TeamService teamService;
    private ViewService viewService;

    public LeagueService(ViewService viewService) {
        this.viewService = viewService;
        teamService = new TeamService();
    }

    public TeamService getTeamService() {
        return teamService;
    }

    public void createTeam(League league) {
        String teamName = viewService.requestTeamName();
        String coach = viewService.requestCoach();
        league.getTeams().add(new Team(teamName, coach));
    }

    public void addPlayerToTeam(Player player, Team team, League league) {
        teamService.addPlayer(team, player);
        signPlayer(league, player);
        viewService.viewPlayerAdded(team, player);
    }

    public void removePlayerFromTeam(Player player, Team team, League league) {
        teamService.removePlayer(team, player);
        releasePlayer(league, player);
        viewService.viewPlayerReleased(team, player);
    }

    private void signPlayer(League league, Player player) {
        league.getUnsignedPlayers().remove(player);
        league.getSignedPlayers().add(player);
    }

    private void releasePlayer(League league, Player player) {
        league.getSignedPlayers().remove(player);
        league.getUnsignedPlayers().add(player);
    }

    private void removePlayerFromLeague(League league, Player player) {
        league.getUnsignedPlayers().remove(player);
        league.getSignedPlayers().remove(player);
    }

    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    public ViewService getViewService() {
        return viewService;
    }

    public void setViewService(ViewService viewService) {
        this.viewService = viewService;
    }

}