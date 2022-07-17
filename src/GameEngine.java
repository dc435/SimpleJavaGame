/**
 * Main game engine
 * @author: Damian Curran
 *
 */

import java.util.Scanner;

public class GameEngine {
	
	private Player player;
	private Monster monster;
	private World world;
	
	private static Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) {
				
		// Creates an instance of the game engine.
		GameEngine gameEngine = new GameEngine();
		
		// Runs the main game loop.
		gameEngine.runGameLoop();
		
	}
	
	/*
	 *  Logic for running the main game loop.
	 */
	private void runGameLoop() {
		
		displayMainMenu();
		boolean inGame = true;	
		
		while (inGame) {
			
			System.out.print("> ");
			String userInput = keyboard.nextLine();
			
			switch (userInput) {
			
				case "help":
					
					displayHelpMsg();
					break;
					
				case "commands":
					
					displayCommandsMsg();
					break;
					
				case "player":
					
					optionPlayer();
					break;
					
				case "monster":
					
					createMonster();
					break;
					
				case "start":
					
					optionStart();
					break;
					
				case "exit":

					inGame = false;
					break;
					
				default:
					
					displayDefaultMsg();
					break;
			
			}
			
			if (!inGame) {
				displayExitMsg();
			}
			
		}
		
	}
	
	/*
	 *  Logic for running the map loop called from GameLoop after optionStart
	 */
	private void runMapLoop() {
		
		world.renderWorld();
		
		boolean inMap = true;	
		
		while (inMap) {
			
			System.out.print("> ");
			String userMove = keyboard.nextLine();
			
			switch (userMove) {
			
				case "w":
					
					world.movePlayerNorth();
					break;
					
				case "s":
					
					world.movePlayerSouth();
					break;
					
				case "a":
					
					world.movePlayerWest();
					break;
					
				case "d":
					
					world.movePlayerEast();
					break;
					
				case "home":

					inMap = false;
					break;
					
				default:
					
					displayDefaultMapMsg();
					break;
					
			}
			
			//After each player move, assess whether player and monster are on the same node
			//If yes, exit the MapLoop, initiate BattleLoop
			//If no, check if player has returned 'home'
			//If no, render world and continue MapLoop
			
			if (world.clash()) {
				
				displayEncounterMsg();
				inMap = false;
				runBattleLoop();
				
			} else if (!inMap){
				
				displayHomeMapMsg();
				
			} else {
				
				world.renderWorld();
			}
			
		}
		
	}
	
	/*
	 *  Logic for running the battle loop once player & monster clash.
	 */
	private void runBattleLoop() {
		
		boolean inBattle = true;
		
		while(inBattle) {
			
			displayCurrentHealth();
			
			playerAttacks();
			
			if (monster.getCurrentHealth() <= 0) {
				System.out.println(player.getName() + " wins!");
				inBattle = false;
				break;
			}
			
			monsterAttacks();
			
			if (player.getCurrentHealth() <= 0) {
				System.out.println(monster.getName() + " wins!");
				inBattle = false;
				break;
			}
			
			System.out.println();
			
		}

		System.out.println();
		pressEnterToReturn();
				
	}
	
	/*
	 *  Method to create player - called from optionPlayer() (following GameLoop)
	 */
	private void createPlayer() {
		
		System.out.println("What is your character's name?");
		String playerName = keyboard.nextLine();	
		
		player = new Player(playerName);
		
		System.out.println("Player '" + player.getName() + "' created.");
		System.out.println();
		
		pressEnterToReturn();
		
	}

	/*
	 *  Method to create monster - called from GameLoop
	 */
	private void createMonster() {
		
		System.out.print("Monster name: ");
		String monsterName = keyboard.nextLine();	
		
		System.out.print("Monster health: ");
		int maxHealth;
		//Check if next input is an integer. If not, display error message and return:
		try {
			maxHealth = Integer.parseInt(keyboard.nextLine()); 
		} catch (Exception e) {
			System.out.println("Error. Monster Health needs to be an integer. Please type 'monster' to start again.\n");
			return;
		}
		
		System.out.print("Monster damage: ");
		int attackDamage;
		//Check if next input is an integer. If not, display error message and return:
		try {
			attackDamage = Integer.parseInt(keyboard.nextLine()); 
		} catch (Exception e) {
			System.out.println("Error. Attack Damage needs to be an integer. Please type 'monster' to start again.\n");
			return;
		}
		
		monster = new Monster(monsterName, maxHealth, attackDamage);
		
		System.out.println("Monster '" + monster.getName() + "' created.");
		System.out.println();
		
		pressEnterToReturn();
		
	}

	/*
	 *  Method to create world - called from GameLoop - 'start'
	 */
	private void createWorld() {
		
		char playerChar = Character.toUpperCase(player.getName().charAt(0));
		char monsterChar = Character.toLowerCase(monster.getName().charAt(0));
		
		world = new World(playerChar, monsterChar);
		
	}

	/*
	 *  Method to check if player exists and, if not, create player - called from GameLoop 'player'
	 */
	private void optionPlayer() {
		
		if (player == null) {
			createPlayer();
		} else {
			displayPlayerDetails();
		}					
		
	}

	/*
	 *  Method to check conditions prior to starting new game - called from GameLoop 'start'
	 */
	private void optionStart() {
		
		if (player == null) {
			displayNotFound("player");
		} else if (monster == null) {
			displayNotFound("monster");
		} else {
			player.returnToFullHealth();
			monster.returnToFullHealth();
			createWorld();
			runMapLoop();
			
		}		
	}

	/*
	 *  Method to assess if Enter key is pressed - called variously throughout GameLoop
	 */
	private void pressEnterToReturn() {
		
		System.out.println("(Press enter key to return to main menu)");
		
		while(keyboard.nextLine() == null) {
				//Wait here for ENTER press.
			}
		
		displayMainMenu();
	
	}
	
	/*
	 *  Method to decrement player health - called from BattleLoop
	 */
	private void playerAttacks() {
		
		int newHealth = monster.getCurrentHealth() - player.getAttackDamage();
		
		monster.setCurrentHealth(newHealth);
		
		System.out.println(player.getName() + " attacks " + monster.getName() 
			+ " for " + player.getAttackDamage() + " damage.");
	
	}

	
	/*
	 *  Method to decrement monster health - called from BattleLoop
	 */
	private void monsterAttacks() {
		
		int newHealth = player.getCurrentHealth() - monster.getAttackDamage();
		
		player.setCurrentHealth(newHealth);
		
		System.out.println(monster.getName() + " attacks " + player.getName() 
			+ " for " + monster.getAttackDamage() + " damage.");
		
	}

	/*
	 *  Calls each listed method which, combined, display the main menu.
	 */
	private void displayMainMenu() {
		
		displayTitleText();
		displayCurrentConfig();
		displayInitialMsg();
		
	}
	
	/*
	 *  Displays the title text.
	 */
	private void displayTitleText() {
		
		String titleText = " ____                        \n" + 
				"|  _ \\ ___   __ _ _   _  ___ \n" + 
				"| |_) / _ \\ / _` | | | |/ _ \\\n" + 
				"|  _ < (_) | (_| | |_| |  __/\n" + 
				"|_| \\_\\___/ \\__, |\\__,_|\\___|\n" + 
				"COMP90041   |___/ Assignment ";
		
		System.out.println(titleText);
		System.out.println();

	}
	
	/*
	 *  Displays current player-monster configuration and health.
	 */
	private void displayCurrentConfig() {
		
		String playerInfo = "[None]";
		String monsterInfo = "[None]";
		
		if (player != null) {
			playerInfo = player.getName() + " " + player.getCurrentHealth() + "/" + player.getMaxHealth();
		}
		
		if (monster != null) {
			 monsterInfo = monster.getName() + " " + monster.getCurrentHealth() + "/" + monster.getMaxHealth();
		}
		
		System.out.println("Player: " + playerInfo + "  | Monster: " + monsterInfo);
		System.out.println();
		
	}
	
	/*
	 *  Displays the initial message to user below the Current Configuration display.
	 */
	private void displayInitialMsg() {
		
		String initialMsg = "Please enter a command to continue.\n"
				+ "Type 'help' to learn how to get started.";
		
		System.out.println(initialMsg);
		System.out.println();
		
	}
	
	/*
	 *  Displays the help text.
	 */
	private void displayHelpMsg() {
		
		String helpMsg = "Type 'commands' to list all available commands\n"
				+ "Type 'start' to start a new game\n"
				+ "Create a character, battle monsters, and find treasure!";
		
		System.out.println(helpMsg);
		System.out.println();

	}
	
	/*
	 *  Displays the text response to the 'command' input.
	 */
	private void displayCommandsMsg() {
		
		String commandsMsg = "help\n"
				+ "player\n"
				+ "monster\n"
				+ "start\n"
				+ "exit";
		
		System.out.println(commandsMsg);
		System.out.println();

	}
	
	/*
	 *  Displays the current player details.
	 */
	private void displayPlayerDetails() {
		
		String playerDetails = player.getName() + " (Lv. " + player.getLevel() + ")\n"
				+ "Damage: " + player.getAttackDamage() + "\n"
				+ "Health: " + player.getCurrentHealth() + "/" + player.getMaxHealth();
		
		System.out.println(playerDetails);
		System.out.println();
		
		pressEnterToReturn();
		
	}
	
	/*
	 *  Displays are error message when player / monster is not yet defined.
	 */
	private void displayNotFound(String thing) {
		
		String notFound = "No " + thing + " found, please create a " + thing + " with '" + thing + "' first.";

		System.out.println(notFound);
		System.out.println();
		
		pressEnterToReturn();	
		
	}
	
	/*
	 *  Displays exit message upon quitting the program.
	 */
	private void displayExitMsg() {
		
		System.out.println("Thank you for playing Rogue!");
		
	}
	
	/*
	 *  Displays default error text.
	 */
	private void displayDefaultMsg() {
		
		System.out.println("Sorry, that command is not recognised.");
		displayHelpMsg();
		
	}
	
	/*
	 *  Displays default error text whilst in MapLoop.
	 */
	private void displayDefaultMapMsg() {
		
		System.out.println("Sorry, that command is not recognised. "
			+ "Please use w, a, s and d to move player. Type 'home' to return to main.");
		
	}
	
	/*
	 *  Displays return home message from MapLoop to main GameLoop.
	 */
	private void displayHomeMapMsg() {
		
		System.out.println("Returning home...");
		System.out.println();
		
		pressEnterToReturn();	
		
	}
	
	/*
	 *  Displays encounter message used at commencement of BattleLoop.
	 */
	private void displayEncounterMsg() {
		
		System.out.println(player.getName() + " encountered a " + monster.getName());
		System.out.println();
		
	}
	
	/*
	 *  Displays current health of player and monster used during BattleLoop.
	 */
	private void displayCurrentHealth() {
		
		System.out.println(player.getName() + " " + player.getCurrentHealth() + "/" + player.getMaxHealth()
				+ " | "
				+ monster.getName() + " " + monster.getCurrentHealth() + "/" + monster.getMaxHealth());
		
	}
	
}
