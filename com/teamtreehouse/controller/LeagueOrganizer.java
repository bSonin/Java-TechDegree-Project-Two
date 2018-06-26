package com.teamtreehouse.controller;

import com.teamtreehouse.model.League;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.service.LeagueService;
import com.teamtreehouse.service.ViewService;

public class LeagueOrganizer {
    private League league;
    private LeagueService leagueService;
    private ViewService viewService;

    public LeagueOrganizer(Player[] availablePlayers) {
        league = new League(availablePlayers);
        viewService = new ViewService();
        leagueService = new LeagueService(viewService);
    }

    public void init() {
        boolean isDone = false;
        while (!isDone) {
            String request = viewService.requestMainMenuAction();
            switch (request.toLowerCase()) {
                case "1":
                case "create":
                    createTeamFlow();
                    break;
                case "2":
                case "add":
                    addPlayerFlow();
                    break;
                case "3":
                case "remove":
                    removePlayerFlow();
                    break;
                case "4":
                case "teams":
                    viewTeamFlow();
                    break;
//                case "5":
//                case "availablePlayers":
//                    break;
//                case "6":
//                case "register":
//                    break;
//                case "7":
//                case "expel":
//                    break;
//                case "8":
//                case "build":
//                    break;
                case "5":
                 case "done":
                    isDone = true;
                    break;
                default:
                    viewService.viewNotValidOption(request);
                    break;
            }
        }
    }

    private void createTeamFlow() {
        leagueService.createTeam(league);
    }

    private void addPlayerFlow() {
        if (league.getTeams().size() > 0) {
            Player player = viewService.requestUnsignedPlayer(league);
            Team team = viewService.requestTeam(league);
            leagueService.addPlayerToTeam(player, team, league);
        }
        else {
            viewService.viewNoTeamsAlert();
        }
    }

    private void removePlayerFlow() {
        if (league.getTeams().size() > 0) {
            Team team = viewService.requestTeam(league);
            if (team.getPlayers().size() > 0) {
                Player player = viewService.requestPlayerFromTeam(team);
                leagueService.removePlayerFromTeam(player, team, league);
            }
            else {
                viewService.viewNoPlayersOnTeamAlert();
            }
        }
        else {
            viewService.viewNoTeamsAlert();
        }
    }

    private void viewTeamFlow() {
        viewService.viewTeams(league.getTeams());
    }

}
