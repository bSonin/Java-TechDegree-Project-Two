package com.teamtreehouse.controller;

import com.teamtreehouse.model.League;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.service.LeagueService;
import com.teamtreehouse.service.TeamService;
import com.teamtreehouse.service.ViewService;


//FIXME: Next Steps
/*
    List of items to complete moving forward
    1) Test the current code you've written more thoroughly. This isn't the wild west
    2) Finish the league balance report once admins reply to your requirements question
    3) Finish registration/expulsion once admins reply to your requirements question
    4) Clean up any code that has become unused during iteration
    5) Consider/bolster null checking/exception handling for actions that require
           players or teams exist
    5) Go through U/I to make sure things look nice/neat
    7) Submit

    IF you finish all steps except the code you can't complete without teacher clarification,
    go on to your coursework for unit 3.

 */

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
                case "wait":
                    waitListFlow();
                case "12":
                 case "done":
                    isDone = true;
                    break;
                default:
                    viewService.viewNotValidOption(request);
                    break;
            }
        }
    }

    private void waitListFlow() {
        viewService.viewWaitList(league.getWaitListedPlayers());
    }

    private void expelPlayerFlow() {
        Player player = viewService.requestUnsignedPlayer(league);
        leagueService.removePlayerFromLeague(player, league);
        leagueService.moveWaitListedPlayerIntoLeague(league);
    }

    private void registerPlayerFlow() {
        leagueService.registerPlayer(league);
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
