package com.teamtreehouse.controller;

import com.teamtreehouse.model.League;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.service.LeagueService;
import com.teamtreehouse.service.TeamService;
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
                    teamsFlow();
                    break;
                case "5":
                case "height":
                    heightReportFlow();
                    break;
                case "7":
                case "balance":
                    leagueBalanceReportFlow();
                    break;
                case "8":
                case "build":
                    buildLeagueFlow();
                    break;
                case "9":
                case "register":
                    registerPlayerFlow();
                    break;
                case "10":
                case "expel":
                    expelPlayerFlow();
                    break;
                case "11":
                 case "done":
                    isDone = true;
                    break;
                default:
                    viewService.viewNotValidOption(request);
                    break;
            }
        }
    }

    private void expelPlayerFlow() {
        viewService.viewNotCurrentlyFunctional();
    }

    private void registerPlayerFlow() {
        viewService.viewNotCurrentlyFunctional();
    }

    private void buildLeagueFlow() {
        viewService.viewNotCurrentlyFunctional();
    }

    private void leagueBalanceReportFlow() {
        viewService.viewNotCurrentlyFunctional();
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

    private void teamsFlow() {
        viewService.viewTeams(league.getTeams());
    }

    private void heightReportFlow() {
        if (league.getTeams().size() == 0) {
            viewService.viewNoTeamsAlert();
        }
        else {
            Team team = viewService.requestTeam(league);
            viewService.viewHeightReport(leagueService.getTeamGroupedByHeights(team));
        }
    }

//FIXME: Either clean this up or delete it...
//    private void viewAvailablePlayersFlow() {
//        if (league.getUnsignedPlayers().size() == 0) {
//            viewService.viewNoAvailablePlayersAlert();
//        }
//        else {
//            viewService.viewPlayers(league.getUnsignedPlayers());
//        }
//    }
//
//    private void viewRosterFlow() {
//        if (league.getTeams().size() == 0) {
//            viewService.viewNoTeamsAlert();
//        }
//        else {
//            viewService.viewLeagueRoster(league);
//        }
//    }


}
