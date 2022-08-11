package model;

import java.util.Random;

/**
 * Defines the Character class's behavior. Characters are competitors in the battle simulator.
 */
public class Character implements ICharacter {

    // Static attributes
    private String name;

    // Physical attributes
    private int strength = 100;
    private int walkingSpeed;
    private double shootingAccuracyModifier;
    private double combatProwess;
    private String bio;

    // State attributes
    private int hitPoints = 100;
    private IWeapon equippedWeapon;
    private Boolean inTheFight;

    private final int LOWER_BOUND_STRENGTH_DAMAGE = 0;


    /* ############################ Constructor ############################ */

    /**
     *
     * @param name the name of the character
     * @param strength the character's strength
     * @param walkingSpeed the rate with which this character moves towards their opponent
     * @param shootingAccuracyModifier this character's ability to shoot
     * @param combatProwess this character's ability to wield a melee weapon
     * @param bio this character's biography
     */
    public Character(String name, int strength, int walkingSpeed,
                     double shootingAccuracyModifier, double combatProwess, String bio) {

        // null check object input
        if (name == null || bio == null || name.isBlank() || bio.isBlank()) {
            throw new IllegalArgumentException("name or biography of character may not be null");
        }

        // bound integer inputs
        if (strength <= 0 || walkingSpeed <= 0 || hitPoints <= 0 || combatProwess <= 0) {
            throw new IllegalArgumentException("Cannot initialize combat attributes at 0");
        }

        this.name = name;
        this.strength = strength;
        this.walkingSpeed = walkingSpeed;
        this.shootingAccuracyModifier = shootingAccuracyModifier;
        this.bio = bio;

    }

    /* ############################ Public Methods (non-getter, setter, toString) ############################ */

    /**
     * This character applies damage against another argued character dependent upon a determined value of damage.
     *
     * @param defender the model.ICharacter defending from an attack against this character.
     */
    public AttackLog attack(ICharacter defender) throws IllegalArgumentException {

        // begin a new attack log containing the characters taking part in the fight
        AttackLog attackLog = new AttackLog(this, defender);

        // An attack cannot be conducted on a null character
        if (defender == null) {
            throw new IllegalArgumentException("This character cannot attack a null character");
        }

        // determine whether this attack will hit with RGN.
        // attack will hit when the roll value is within the bounds of the
        // hit chance.
        Random rand = new Random();
        double roll = rand.nextFloat();
        boolean attackHits = (roll <= this.getWeapon().rollHitChance());

        // If the Character's attack hits their opponent...
        if (attackHits) {

            // Handles the case where the attack hits
            this.getWeapon().handleHit();

            // use the character's attributes and the weapon they have equipped to calculate damage
            double weaponDamageRoll = this.getWeapon().rollWeaponDamage();

            // the total damage is the total weapon damage + the randomly drawn strength
            double totalAttackDamage = weaponDamageRoll + rand.nextFloat(LOWER_BOUND_STRENGTH_DAMAGE, this.getStrength());
            defender.takeDamage(totalAttackDamage);

            }
        } else {

            this.getWeapon().handleMiss();

        }

        // Every attack on a character returns information about what happened in the attack
        return attackLog;
    }

    /**
     * Applies damage to this character based.
     *
     * @param damage the amount of damage to be applied to this character
     */
    public void takeDamage(double damage) throws IllegalArgumentException {

        // value check the damage
        if (damage <= 0) {
            throw new IllegalArgumentException("The argued damage value may not be zero or negative.");
        }

        // apply the damage to the character
        this.hitPoints -= damage;

        // determine character's life status after damage
        if (this.hitPoints < 0) {
            this.inTheFight = false;
        };
    }



    /* ############################ Setter Methods ############################ */

    /**
     * Sets the model.IWeapon implementing object that this character will use for attacks.
     *
     * @param weapon the model.IWeapon implementing object that this character will equip.
     */
    public void setWeapon(IWeapon weapon) throws IllegalArgumentException {

        if (weapon == null) {
            throw new IllegalArgumentException("May not assign a null weapon");
        }

        this.equippedWeapon = weapon;
    }

    /* ############################ Getter Methods ############################ */

    /**
     * Gets the model.IWeapon implementing object that this character is wielding.
     *
     * @return the model.IWeapon implementing object that this character is wielding.
     */
    public IWeapon getWeapon() {
        return this.equippedWeapon;
    }

    /**
     * Gets the strength attribute of this character.
     *
     * @return the strength value that this character has.
     */
    public int getStrength() {
        return this.strength;
    }

    /**
     * Returns this character's status in the arena. Returns true if this character has hit points left. Otherwise,
     * returns false.
     *
     * @return the character's status in the arena.
     */
    public Boolean getFightStatus() {
        return this.inTheFight;
    }

    /**
     * Gets this character's hitPoint value.
     *
     * @return the hitPoints that this character has remaining
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /* ############################ ToString Methods ############################ */

    /**
     *
     * @return
     */
    @Override
    public String toString(){

    }




}
