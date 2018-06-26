package com.teamtreehouse.service;

import com.sun.org.apache.bcel.internal.classfile.SourceFile;
import com.teamtreehouse.model.League;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.*;

public class ViewService {
    private Map<String, String> mainMenu;
    private BufferedReader reader;


    public ViewService() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        mainMenu = new LinkedHashMap<>();
        populateMenu();
    }

    private void populateMenu() {
        mainMenu.put("Create", "Create a new team");
        mainMenu.put("Add", "Add players to a team");
        mainMenu.put("Remove", "Remove players from a new team");
//        mainMenu.put("Teams", "View a list of current teams");
//        mainMenu.put("Players", "View a list of current players");
//        mainMenu.put("Register", "Register a new player with the league and add them to a wait list");
//        mainMenu.put("Expel", "Expel a player from the league, bringing the player at the top of the wait list into the pool");
//        mainMenu.put("Report", "");
//        mainMenu.put("Balance", "");
//        mainMenu.put("Build", "Allow the Organizer to build teams automatically");
        mainMenu.put("Done", "Quit using the LeagueOrganizer application");
    }

    public void viewMainMenu() {
        int i = 0;
        for (Map.Entry<String, String> menuItem : mainMenu.entrySet()) {
            System.out.printf("%d) %-9s-  %s%n", ++i, menuItem.getKey(), menuItem.getValue());
        }
        System.out.println("");
        System.out.println("");
    }

    public boolean viewHeightReport(Team team) {
        return false;
    }

    public boolean viewLeagueBalanceReport() {
        return false;
    }

    public void viewNotValidOption(String request) {
        System.out.printf("\"%s\" is not a valid option.%n", request);
    }

    public String requestTeamName() {
        System.out.printf("Please enter the team's name:  ");
        return readStringFromInput();
    }

    public String requestCoach() {
        System.out.printf("Please enter the coach's name:  ");
        return readStringFromInput();
    }

    public void viewTeamRoster (Team team) {
        // Players for specific team
    }

    public void viewLeagueRoster () {
        // All players in league
        // Should note if player already on team
    }

    public void viewTeams(Set<Team> teams) {
        int i = 0;
        for (Team team : teams) {
            System.out.printf("%d) %s%n", ++i, team.getTeamName());
        }
    }

    public void viewPlayers(Set<Player> players) {
        int i = 0;
        for (Player player : players) {
            System.out.printf("%d) %s - Height: %d (in)  Experience: %s%n",
                    ++i,
                    player.getFirstName() + " " + player.getLastName(),
                    player.getHeightInInches(),
                    player.isPreviousExperience() ? "yes" : "no");
        }
    }
    public void viewWaitList() {
        // View players on wait list
    }

    public Team requestTeam(League league) {
        System.out.println("Please select a team by entering its corresponding number:");
        viewTeams(league.getTeams());
        System.out.printf("Your choice:  ");
        List<Team> teams = new ArrayList<>(league.getTeams());
        return teams.get(readIntFromInput() - 1);
    }

    public Player requestUnsignedPlayer(League league) {
        System.out.println("Please select a player by entering his/her corresponding number:");
        viewPlayers(league.getUnsignedPlayers());
        System.out.printf("Your choice:  ");
        List<Player> players = new ArrayList<>(league.getUnsignedPlayers());
        return players.get(readIntFromInput() - 1);
    }

    private int readIntFromInput() {
        int response = -1;
        try {
            response = Integer.parseInt(reader.readLine().trim());
        } catch (Exception e) {
            System.out.println("ERROR: Could not read input - Closing program!");
            System.exit(0);
        }
        return response;
    }

    private String readStringFromInput() {
        String response = "";
        try {
            response = reader.readLine().trim();
        } catch (Exception e) {
            System.out.println("ERROR: Could not read input - Closing Program!");
            System.exit(0);
        }
        return response;
    }

    public void viewPlayerAdded(Team team, Player player) {
        System.out.printf("%s has been added to the %s team!",
                player.getFirstName() + " " + player.getLastName(),
                team.getTeamName());
    }

    public void viewPlayerReleased(Team team, Player player) {
        System.out.printf("%s has been released from the %s team!",
                player.getFirstName() + " " + player.getLastName(),
                team.getTeamName());
    }

    public String requestMainMenuAction() {
        System.out.println("Please select an action from the list below:");
        viewMainMenu();
        System.out.printf("Your choice:  ");
        return readStringFromInput();
    }

    public Player requestSignedPlayer(League league) {
        System.out.println("Please select a player by entering his/her corresponding number:");
        viewPlayers(league.getSignedPlayers());
        System.out.printf("Your choice:  ");
        List<Player> players = new ArrayList<>(league.getSignedPlayers());
        return players.get(readIntFromInput() - 1);
    }

    public void viewNoTeamsAlert() {
        System.out.println("No teams exist! Please create a team before adding/removing players");
    }
}
