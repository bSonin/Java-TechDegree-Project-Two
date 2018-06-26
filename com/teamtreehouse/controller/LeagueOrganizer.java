package com.teamtreehouse.controller;

import com.teamtreehouse.model.League;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.service.LeagueService;
import com.teamtreehouse.service.ViewService;


// NOTES FROM 6/23/18
// Hurry to get MVP of create teams, add players and remove players.
// This will make it easier to expand, and begin committing your work.
// You should aim to have at least 3-4 commits for this project, and should get used
// to committing each sitting. This will help you to avoid losing progress and get you
// used to thinking about work in small, completeable tasks.

// Get mvp create/add/remove, commit, then worry about expanding. Service methods are
// your friends. Funnel everything down the LeagueService and ViewService!


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
//                case "4":
//                case "teams":
//                    break;
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
                case "4": //FIXME: Will need to edit number as you add options!
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
            Player player = viewService.requestSignedPlayer(league);
            Team team = viewService.requestTeam(league);
            leagueService.removePlayerFromTeam(player, team, league);
        }
        else {
            viewService.viewNoTeamsAlert();
        }
    }

}
