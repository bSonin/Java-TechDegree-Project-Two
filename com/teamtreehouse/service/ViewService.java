package com.teamtreehouse.service;

import com.teamtreehouse.model.League;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        mainMenu.put("Teams", "View a list of current teams");
        mainMenu.put("Roster", "View a team's roster");
        mainMenu.put("Height", "View a height report for a chosen team");
        mainMenu.put("Balance", "View a League Balance Report");
        mainMenu.put("Build", "Allow the Organizer to build fair teams automatically");
        mainMenu.put("Register", "Register a new player with the league and add them to a wait list");
        mainMenu.put("Expel", "Expel a player from the league, bringing the player at the top of the wait list into the pool");
        mainMenu.put("Wait", "View players on the wait list");
        mainMenu.put("Done", "Quit using the LeagueOrganizer application");
    }

    // ---------------------
    // *** Views ***
    // ---------------------
    public void viewMainMenu() {
        int i = 0;
        for (Map.Entry<String, String> menuItem : mainMenu.entrySet()) {
            System.out.printf("%d) %-9s-  %s%n", ++i, menuItem.getKey(), menuItem.getValue());
        }
        System.out.println();
        System.out.println();
    }

    public void viewTeamRoster (Team team) {
        System.out.printf("Team: %s - Coach: %s%n", team.getTeamName(), team.getCoach());
        viewPlayers(team.getPlayers());
    }

    public void viewTeams(Set<Team> teams) {
        if (teams == null || teams.size() == 0) {
            System.out.println("No teams to view!");
            System.out.println();
            return;
        }
        int i = 0;
        for (Team team : teams) {
            System.out.printf("%d) %s%n", ++i, team.getTeamName());
        }
        System.out.println();
    }

    public void viewPlayers(Set<Player> players) {
        if (players == null || players.size() == 0) {
            System.out.println("No players to view!");
            System.out.println();
            return;
        }
        int i = 0;
        for (Player player : players) {
            System.out.printf("%d) %s - Height: %d (in)  Experience: %s%n",
                    ++i,
                    player.getFirstName() + " " + player.getLastName(),
                    player.getHeightInInches(),
                    player.isPreviousExperience() ? "yes" : "no");

        }
        System.out.println();
    }

    public void viewWaitList(Queue<Player> waitListedPlayers) {
        if (waitListedPlayers == null || waitListedPlayers.size() == 0) {
            System.out.println("There are no wait listed players.");
            System.out.println();
        } else {
            System.out.println("This is the list of wait listed players:");
            viewPlayers(new TreeSet<>(waitListedPlayers));
        }
    }

    public void viewPlayerAdded(Team team, Player player) {
        System.out.printf("%s has been added to the %s team!%n",
                player.getFirstName() + " " + player.getLastName(),
                team.getTeamName());
        System.out.println();
    }

    public void viewPlayerReleased(Team team, Player player) {
        System.out.printf("%s has been released from the %s team!",
                player.getFirstName() + " " + player.getLastName(),
                team.getTeamName());
        System.out.println();
    }

    public void viewHeightReport(Map<String, Set<Player>> playersGroupByHeight) {
        Set<Player> smallPlayers = playersGroupByHeight.get("small");
        if (smallPlayers == null || smallPlayers.size() == 0) {
            System.out.println("=== No Players of Size 35 - 40 inches ===");

        } else {
            System.out.println("=== Players 35 - 40 inches ===");
            System.out.printf("There are %d players in this range:%n", smallPlayers.size());
            viewPlayers(playersGroupByHeight.get("small"));
        }

        Set<Player> mediumPlayers = playersGroupByHeight.get("medium");
        if (mediumPlayers == null || mediumPlayers.size() == 0) {
            System.out.println("=== No Players of Size 41 - 46 inches ===");
        } else {
            System.out.println("=== Players 41 - 46 inches ===");
            System.out.printf("There are %d players in this range:%n", mediumPlayers.size());
            viewPlayers(playersGroupByHeight.get("medium"));
        }

        Set<Player> tallPlayers = playersGroupByHeight.get("tall");
        if (tallPlayers == null || tallPlayers.size() == 0) {
            System.out.println("=== No Players of Size 47 - 50 inches ===");
        } else {
            System.out.println("=== Players 47 - 50 inches ===");
            System.out.printf("There are %d players in this range:%n%", tallPlayers.size());
            viewPlayers(playersGroupByHeight.get("tall"));
            System.out.println();
        }
    }
    public void viewLeagueBalanceReport(League league) {
        for (Team team : league.getTeams()) {
            System.out.printf("--- Team: %s ---%n", team.getTeamName());
            System.out.printf("Number Experienced Players: %d%n", team.getNumberExperiencedPlayers());
            System.out.printf("Number Inexperienced Players: %d%n", team.getPlayers().size() - team.getNumberExperiencedPlayers());
            System.out.printf("Team Experience Level: %.2f%%%n%n", team.getTeamExperienceLevel());
        }
        System.out.println();
    }

    // ---------------------
    // *** Requests ***
    // ---------------------
    public String requestFirstName() {
        System.out.printf("Please enter the players's first name:  ");
        return readStringFromInput();
    }

    public String requestLastName() {
        System.out.printf("Please enter the players's last name:  ");
        return readStringFromInput();
    }

    public int requestHeight() {
        System.out.printf("Please enter the players's height (inches):  ");
        return readIntFromInput();
    }
    public boolean requestExperience() {
        System.out.printf("Does the player have previous experience? (yes/no):  ");
        return readBooleanFromInput();
    }

    public String requestTeamName() {
        System.out.printf("Please enter the team's name:  ");
        return readStringFromInput();
    }

    public String requestCoach() {
        System.out.printf("Please enter the coach's name:  ");
        return readStringFromInput();
    }

    public String requestMainMenuAction() {
        System.out.println();
        System.out.println("Please select an action from the list below:");
        viewMainMenu();
        System.out.printf("Your choice:  ");
        return readStringFromInput();
    }
    public Team requestTeam(League league) {
        System.out.println();
        System.out.println("Please select a team by entering its corresponding number:");
        viewTeams(league.getTeams());
        System.out.printf("Your choice:  ");
        List<Team> teams = new ArrayList<>(league.getTeams());
        int index = readIntFromInput() - 1;
        return isOutOfBounds(teams.size(), index) ? null :teams.get(index);
    }

    public Player requestUnsignedPlayer(League league) {
        System.out.println();
        System.out.println("Please select a player by entering his/her corresponding number:");
        viewPlayers(league.getUnsignedPlayers());
        System.out.printf("Your choice:  ");
        List<Player> players = new ArrayList<>(league.getUnsignedPlayers());
        int index = readIntFromInput() - 1;
        return isOutOfBounds(players.size(), index) ? null : players.get(index);
    }

    public int requestNumberTeams() {
        System.out.println();
        System.out.println("Please enter the number of teams to be created:");
        return readIntFromInput();
    }

    public int requestNumberPlayers(League league) {
        System.out.println();
        System.out.println("Please enter the number of players you'd like per team:");
        return readIntFromInput();
    }

    public Player requestPlayerFromTeam(Team team) {
        System.out.printf("Please select a player from the %s's roster:%n", team.getTeamName());
        viewTeamRoster(team);
        System.out.println("Your choice:  ");
        List<Player> players = new ArrayList<>(team.getPlayers());
        int index = readIntFromInput() - 1;
        return isOutOfBounds(players.size(), index) ? null : players.get(index);
    }

    private boolean isOutOfBounds(int size, int index) {
        return index < 0 || index >= size;
    }

    // ---------------------
    // *** Process Input ***
    // ---------------------
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

    private boolean readBooleanFromInput() {
        boolean response = false;
        try {
            String textResponse = reader.readLine().trim();
            response = textResponse.equalsIgnoreCase("yes") ? true : false;
        } catch (Exception e) {
            System.out.println("ERROR: Could not read input - Closing Program!");
            System.exit(0);
        }
        return response;
    }

    // ---------------------
    // *** Alerts ***
    // ---------------------
    public void viewNoPlayersOnTeamAlert() {
        System.out.println("There are no players on this team, so no players can be removed!");
        System.out.println();
    }

    public void viewNoTeamsAlert() {
        System.out.println("No teams exist!");
        System.out.println();
    }

    public void viewNoAvailablePlayersAlert() {
        System.out.println("There are no available players to be viewed or added!");
        System.out.println();
    }

    public void viewNotValidOption(String request) {
        System.out.printf("\"%s\" is not a valid option.%n", request);
    }

    public void viewErrorProcessingRequestAlert() {
        System.out.println("ERROR: Could not process request.");
        System.out.println();
    }

    public void viewInvalidPlayersPerTeamAlert(League league) {
        System.out.printf("There are only %d players available, and there must be at least one player on each of the %d teams.%n",
                league.getUnsignedPlayers().size(),
                league.getTeams().size());
        System.out.printf("Additionally, the maximum number of players allowed on a team is %d.%n", League.MAX_PLAYERS_PER_TEAM);
    }

    public void viewTeamsAlreadyExistAlert() {
        System.out.println("ERROR: You cannot use the team builder feature if the league already has teams.");
        System.out.println();
    }

    public void viewInvalidNumberTeamsAlert(int numAvailablePlayers) {
        System.out.println("ERROR: You cannot have more teams than there are players.");
        System.out.printf("There are %d players available.%n", numAvailablePlayers);
    }

    public void viewTeamIsFullAlert() {
        System.out.println("ERROR: The team already has the maximum number of players! Please add the player to a different team.");
        System.out.println();
    }

    public void viewModifiedRequestedTeamSizeAlert() {
        System.out.println("ERROR: The request number of players per team has been altered to create teams of equal size.");
        System.out.println();
    }

    public void viewTeamNameExistsAlert() {
        System.out.println("ERROR: This team name already exists! Try again!");
        System.out.println();
    }
}
