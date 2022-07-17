/**
 * Class for World objects
 * @author: Damian Curran
 *
 */
public class World {
	
	//Constants for map size
	private static final int WORLD_WIDTH = 6;
	private static final int WORLD_HEIGHT = 4;
	
	//Constants for initial positions on map for constructor
	private static final int START_PLAYER_POS_HORZ = 1;
	private static final int START_PLAYER_POS_VERT = 1;
	private static final int START_MONSTER_POS_HORZ = 4;
	private static final int START_MONSTER_POS_VERT = 2;
	
	//Constant character to display for empty cell on map
	private static final char EMPTY_CHAR = '.';
	
	private char playerChar;
	private char monsterChar;
	private int playerPositionHorz; 
	private int playerPositionVert;
	private int monsterPositionHorz;
	private int monsterPositionVert;
	
	/*
	 *  Constructor for World class
	 *  Parameters: First initial for player (playerChar); First initial for monster (monsterChar)
	 *  Default positions values for player and monster: Initialised by constants
	 */
	public World(char playerChar, char monsterChar) {
		
		this.playerChar = playerChar;
		this.monsterChar = monsterChar;
		
		playerPositionHorz = START_PLAYER_POS_HORZ;
		playerPositionVert = START_PLAYER_POS_VERT;
		monsterPositionHorz = START_MONSTER_POS_HORZ;
		monsterPositionVert = START_MONSTER_POS_VERT;
			
	}

	/*
	 *  Method to print (ie. to 'render') map grid
	 */
	public void renderWorld() {
		
		for (int i = 0; i < WORLD_HEIGHT; i++) {
			
			for (int j = 0; j < WORLD_WIDTH; j ++) {
				
				if (i == playerPositionVert && j == playerPositionHorz) {
					System.out.print(playerChar);
				} else if (i == monsterPositionVert && j == monsterPositionHorz) {
					System.out.print(monsterChar);
				} else {
					System.out.print(EMPTY_CHAR);
				}	
			}
			
			System.out.print("\n");
			
		}
		
		System.out.println();
		
	}
	
	/*
	 *  Method to test if player & monster move into same node on grid
	 *  Used in MapLoop in GameEngine to initialise the BattleLoop
	 */
	public boolean clash() {
		if (playerPositionHorz == monsterPositionHorz 
				&& playerPositionVert == monsterPositionVert) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/*
	 *  Method to adjust player position on map (North)
	 */
	public void movePlayerNorth() {
		if (this.getPlayerPositionVert() > 0) {
			this.setPlayerPositionVert(this.getPlayerPositionVert() - 1);
		}
	}
	
	/*
	 *  Method to adjust player position on map (South)
	 */
	public void movePlayerSouth() {
		if (this.getPlayerPositionVert() < (WORLD_HEIGHT - 1)) {
			this.setPlayerPositionVert(this.getPlayerPositionVert() + 1);
		}
	}
	
	/*
	 *  Method to adjust player position on map (West)
	 */
	public void movePlayerWest() {
		if (this.getPlayerPositionHorz() > 0) {
			this.setPlayerPositionHorz(this.getPlayerPositionHorz() - 1);
		}
	}
	
	/*
	 *  Method to adjust player position on map (East)
	 */
	public void movePlayerEast() {
		if (this.getPlayerPositionHorz() < (WORLD_WIDTH - 1)) {
			this.setPlayerPositionHorz(this.getPlayerPositionHorz() + 1);
		}
	}
	
	/*
	 *  Getter methods:
	 */
	public int getPlayerPositionHorz() {
		return playerPositionHorz;
	}

	public int getPlayerPositionVert() {
		return playerPositionVert;
	}

	/*
	 *  Setter methods:
	 */
	private void setPlayerPositionHorz(int playerPositionHorz) {
		this.playerPositionHorz = playerPositionHorz;
	}

	private void setPlayerPositionVert(int playerPositionVert) {
		this.playerPositionVert = playerPositionVert;
	}

}
