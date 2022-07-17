/**
 * Class for Monster objects
 * @author: Damian Curran
 *
 */
public class Monster {

	private String name;
	private int maxHealth;
	private int currentHealth;
	private int attackDamage;
	
	/*
	 *  Constructor for Monster class
	 *  Current health initialised at Max health. Other object variables per input parameters
	 */
	public Monster(String name, int maxHealth, int attackDamage) {
		
		this.name = name;		
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		this.attackDamage = attackDamage;
	
	}
	
	/*
	 *  Method to return monster to full health. Used at commencement of new MapLoop
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
