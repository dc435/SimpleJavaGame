/**
 * Class for Player objects
 * @author: Damian Curran
 *
 */
public class Player {

	private String name;
	private int maxHealth;
	private int currentHealth;
	private int level;
	private int attackDamage;
	
	/*
	 *  Constructor for Player class
	 *  Name object variable per input parameter
	 *  Remaining variables defined per formulas in the project specifications
	 */
	public Player(String name) {
		
		this.name = name;		
		this.level = 1;
		this.maxHealth = 17 + (level * 3);
		this.currentHealth = maxHealth;
		this.attackDamage = 1 + level;
	
	}
	
	/*
	 *  Method to return player to full health. Used at commencement of new MapLoop
	 */
	public void returnToFullHealth() {
		this.setCurrentHealth(this.getMaxHealth());
	}

	/*
	 *  Getter methods
	 */
	public String getName() {
		return name;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public int getLevel() {
		return level;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	/*
	 *  Setter method
	 */
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}
		
}