package com.teamtreehouse.service;

import com.teamtreehouse.model.League;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;

import java.util.*;

public class LeagueService {
    private TeamService teamService;
    private ViewService viewService;

    public LeagueService(ViewService viewService) {
        this.viewService = viewService;
        teamService = new TeamService();
    }

    public void createTeam(League league) {
        boolean teamNameUnique = false;
        String teamName = viewService.requestTeamName();
        while (!teamNameUnique) {
            teamNameUnique = true;
            for (Team team : league.getTeams()) {
                if (teamName.equalsIgnoreCase(team.getTeamName())) {
                    viewService.viewTeamNameExistsAlert();
                    teamName = viewService.requestTeamName();
                    teamNameUnique = false;
                    break;
                }
            }
        }

        String coach = viewService.requestCoach();
        league.getTeams().add(new Team(teamName, coach));
    }

    public void addPlayerToTeam(Player player, Team team, League league, boolean showCreationMessage) {
        if (team.getPlayers().size() < League.MAX_PLAYERS_PER_TEAM) {
            teamService.addPlayer(team, player);
            signPlayer(league, player);
            if (showCreationMessage) { viewService.viewPlayerAdded(team, player); }
        }
        else {
            viewService.viewTeamIsFullAlert();
        }
    }

    public void removePlayerFromTeam(Player player, Team team, League league) {
        teamService.removePlayer(team, player);
        releasePlayer(league, player);
        viewService.viewPlayerReleased(team, player);
    }

    public Map<String, Set<Player>> getTeamGroupedByHeights(Team team) {
        return teamService.groupPlayersByHeight(team);
    }

    private void signPlayer(League league, Player player) {
        league.getUnsignedPlayers().remove(player);
        league.getSignedPlayers().add(player);
    }

    private void releasePlayer(League league, Player player) {
        league.getSignedPlayers().remove(player);
        league.getUnsignedPlayers().add(player);
    }

    public void registerPlayer(League league) {
        String firstName = viewService.requestFirstName();
        String lastName = viewService.requestLastName();
        int heightInInches = viewService.requestHeight();
        boolean isExperienced = viewService.requestExperience();
        league.getWaitListedPlayers().add(new Player(firstName, lastName, heightInInches, isExperienced));
    }

    public void removePlayerFromLeague(Player player, League league) {
        league.getUnsignedPlayers().remove(player);
    }

    public void moveWaitListedPlayerIntoLeague(League league) {
        Player firstInLine = league.getWaitListedPlayers().poll();
        if (firstInLine != null) {
            league.getUnsignedPlayers().add(firstInLine);
        }
    }

    public void createNumberOfTeams(League league, int numberOfTeams) {
        for (int i = 0; i < numberOfTeams; ++i) {
            createTeam(league);
        }
    }

    public boolean assignPlayersToTeamsByExperienceLevel(League league, int desiredNumPlayersPerTeam) {

        // Sort players by experience level so we can do round robin assignment
        List<Player> players = new ArrayList<>(league.getUnsignedPlayers());
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Boolean.compare(p2.isPreviousExperience(), p1.isPreviousExperience());
            }
        });

        // Determine number of players per team - force equal number players per team
        int maxPlayersPerTeam = (int) ((double) players.size() / (double) league.getTeams().size());
        int playersPerTeam = (desiredNumPlayersPerTeam > maxPlayersPerTeam) ? maxPlayersPerTeam : desiredNumPlayersPerTeam;

        // Assign players to teams
        int playerCount = players.size();
        int currentPlayer = 0;
        while (playerCount >= league.getTeams().size() && (currentPlayer / league.getTeams().size() < playersPerTeam)) {
            for (Team team : league.getTeams()) {
                addPlayerToTeam(players.get(currentPlayer), team, league, false);
                ++currentPlayer;
                --playerCount;
            }
        }
        return !(playersPerTeam == desiredNumPlayersPerTeam);
    }
}
