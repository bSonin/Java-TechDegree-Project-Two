package com.teamtreehouse.controller;

import com.teamtreehouse.model.League;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.service.LeagueService;
import com.teamtreehouse.service.TeamService;
import com.teamtreehouse.service.ViewService;


//FIXME: Next Steps
/*
    1) Confirm current functionality
           *) Questionable on add/remove player from chosen/yet to be chosen team
    2) Clean up methods based on Treehouse admin feedback
    3) Bolster null/empty collection checking
    4) Consider FIXMEs
    5) Clean up UI a bit.
    6) Submit
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
                case "roster":
                    rosterFlow();
                    break;
                case "6":
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
                    break;
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

    private void rosterFlow() {
        Team team = viewService.requestTeam(league);
        viewService.viewTeamRoster(team);
    }

    private void waitListFlow() {
        viewService.viewWaitList(league.getWaitListedPlayers());
    }

    private void expelPlayerFlow() {
        Player player = viewService.requestUnsignedPlayer(league);
        if (player == null) {
            viewService.viewErrorProcessingRequestAlert();
            return;
        }
        leagueService.removePlayerFromLeague(player, league);
        leagueService.moveWaitListedPlayerIntoLeague(league);
    }

    private void registerPlayerFlow() {
        leagueService.registerPlayer(league);
    }

    private void buildLeagueFlow() {
        if (league.getTeams() == null || league.getTeams().size() == 0) {
            int numTeams = viewService.requestNumberTeams();
            while (!isNumberTeamsValid(numTeams)) {
                viewService.viewInvalidNumberTeamsAlert(league.getUnsignedPlayers().size());
                numTeams = viewService.requestNumberTeams();
            }
            leagueService.createNumberOfTeams(league, numTeams);

            int numPlayersPerTeam = viewService.requestNumberPlayers(league);
            while (!isNumberPlayersPerTeamValid(numPlayersPerTeam)) {
                viewService.viewInvalidPlayersPerTeamAlert(league);
                numPlayersPerTeam = viewService.requestNumberPlayers(league);
            }
            leagueService.assignPlayersToTeamsByExperienceLevel(league, numPlayersPerTeam);
        }
        else {
            viewService.viewTeamsAlreadyExistAlert();
        }
    }

    private void leagueBalanceReportFlow() {
        viewService.viewLeagueBalanceReport(league);
    }

    private void createTeamFlow() {
        if (league.getUnsignedPlayers().size() < 1) {
            viewService.viewNoAvailablePlayersAlert();
        }
        else {
            leagueService.createTeam(league);
        }
    }

    private void addPlayerFlow() {
        if (league.getTeams().size() > 0) {
            Player player = viewService.requestUnsignedPlayer(league);
            Team team = viewService.requestTeam(league);
            if (team == null || player == null) {
                viewService.viewErrorProcessingRequestAlert();
                return;
            }
            leagueService.addPlayerToTeam(player, team, league);
        }
        else {
            viewService.viewNoTeamsAlert();
        }
    }

    private void removePlayerFlow() {
        if (league.getTeams().size() > 0) {
            Team team = viewService.requestTeam(league);
            if (team == null) {
                viewService.viewErrorProcessingRequestAlert();
                return;
            }
            if (team.getPlayers().size() > 0) {
                Player player = viewService.requestPlayerFromTeam(team);
                if (player == null) {
                    viewService.viewErrorProcessingRequestAlert();
                    return;
                }
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
            if (team == null) {
                viewService.viewErrorProcessingRequestAlert();
                return;
            }
            viewService.viewHeightReport(leagueService.getTeamGroupedByHeights(team));
        }
    }

    private boolean isNumberTeamsValid(int numTeams) {
        return numTeams <= league.getUnsignedPlayers().size();
    }

    private boolean isNumberPlayersPerTeamValid(int numPlayersPerTeam) {
        return league.getUnsignedPlayers().size() >= numPlayersPerTeam;
        //FIXME: am I missing a check here?
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
