package model;

import java.util.Random;

/**
 * Defines the Character class's behavior. Characters are competitors in the battle simulator.
 */
public class Character implements ICharacter {

    private final int LOWER_BOUND_STRENGTH_DAMAGE = 0;

    // Static attributes
    private String name;

    // Physical attributes
    private int strength = 100;
    private int walkingSpeed;
    private double shootingAccuracyModifier;
    private double combatProwess;
    private String bio;

    // State attributes
    private Boolean moveState = true;
    private int hitPoints = 100;
    private IWeapon equippedWeapon;
    private boolean inTheFight = true;  // Characters always start as being able to be "in the fight"


    /* ############################ Constructor ############################ */

    /**
     * Creates a Character object instance.
     *
     * @param name                     the name of the character
     * @param strength                 the character's strength
     * @param walkingSpeed             the rate with which this character moves towards their opponent
     * @param shootingAccuracyModifier this character's ability to shoot
     * @param combatProwess            this character's ability to wield a melee weapon
     * @param bio                      this character's biography
     */
    public Character(String name, int strength, int walkingSpeed,
                     double shootingAccuracyModifier, double combatProwess, String bio) {

        // null check object input
        if (name == null || bio == null || name.isBlank() || bio.isBlank()) {
            throw new IllegalArgumentException("name or biography of character may not be null");
        }

        // bound numerical inputs
        if (strength <= 0 ||
                walkingSpeed <= 0 ||
                combatProwess < 0 || combatProwess > 1.00
                || shootingAccuracyModifier < 0 || shootingAccuracyModifier > 1.00) {
            throw new IllegalArgumentException("Illegal argument to Character constructor - invalid values argued " +
                    "on numeric parameters.");
        }

        this.name = name;
        this.strength = strength;
        this.walkingSpeed = walkingSpeed;
        this.bio = bio;

        // these attributes may be = 0, reflecting character who are inept with
        // certain weapon types
        this.combatProwess = combatProwess;
        this.shootingAccuracyModifier = shootingAccuracyModifier;
    }

    /* #################################################################################### */
    /* ############################ Combat-controlling methods ############################ */
    /* #################################################################################### */

    /**
     * This character applies damage against another argued character dependent upon a determined value of damage.
     *
     * @param defender the model.ICharacter defending from an attack against this character.
     */
    @Override
    public void attack(ICharacter defender, IAttackLog attackLog) throws IllegalArgumentException {

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

            // The character's weapon will handle the case where the attack hits
            this.getWeapon().handleHit(attackLog);

            // use the character's attributes and the weapon they have equipped to calculate damage
            double weaponDamageRoll = this.getWeapon().rollWeaponDamage();

            // the total damage is the total weapon damage + the character's randomly drawn strength
            double totalAttackDamage = weaponDamageRoll + rand.nextFloat(LOWER_BOUND_STRENGTH_DAMAGE,
                    this.getStrength());
            defender.takeDamage(totalAttackDamage);
            attackLog.setDamageDone(totalAttackDamage);

            // if the defender is felled in the attack,
            if (!defender.inTheFight()) {
                attackLog.setDefenderFelled(true);
            }

            // if the attack does not hit...
        } else {

            // When an attack misses, the character's weapon will
            // handle the case
            this.getWeapon().handleMiss(attackLog);
        }
    }

    /**
     * Moves the character the distance of their walking speed towards another character. Called by
     * the IThunderdome implementing model instance, which keeps track of the distance between
     * two fighters.
     *
     * @param defender        the ICharacter that this character is moving towards in battle
     * @param fighterDistance the distance between two fighters in battle
     * @param attackLog       the attackLog that is recording information about the battle this move took place in
     *
     * @return the new distance between the two fighters after this Character moves
     */
    public int move(ICharacter defender, int fighterDistance, IAttackLog attackLog) {

        // The character's position is decremented by their walking speed
        fighterDistance -= this.getWalkingSpeed();

        // return the mutated fighter distance
        attackLog.setDistanceBetween(fighterDistance);

        // if the fighters have closed range...
        if (fighterDistance <= 0) {

            // clamp fighterDistance
            fighterDistance = 0;
            attackLog.setDistanceBetween(fighterDistance);
            attackLog.setRangeClosed(true);

            // when distance is closed between two combatants, no
            // character will try to move anymore
            this.setMoveState(false);
            defender.setMoveState(false);
        }

        return fighterDistance;
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
        }
    }

    /* ######################################################################## */
    /* ############################ Getter Methods ############################ */
    /* ######################################################################## */

    /**
     * Gets the character's biography text.
     *
     * @return The character's biography
     */
    public String getBio() {
        return bio;
    }

    /**
     * Returns the combat prowess modifier of this character, which dictates factors in to their hit-chance
     * with melee-attacks.
     *
     * @return the combat prowess modifier , a double with a value between 0 and 1, that serves as a modifier
     * to other variables in determining hit chance with a melee attack.
     */
    @Override
    public double getCombatProwess() {
        return this.combatProwess;
    }

    /**
     * Returns whether this character will move during their attack method, which is dependent
     * upon the IWeapon implementing weapon object they are carrying.
     *
     * @return Whether the character will move during their attack method execution
     */
    public boolean getMoveState() {
        return this.moveState;
    }

    /**
     * Return the name of this Character.
     *
     * @return the name of this Character as a String
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the shooting accuracy modifier of this character, which dictates factors in to their hit-chance
     * Returns the shooting accuracy modifier of this character, which dictates factors in to their hit-chance
     * with ranged attacks.
     *
     * @return the shooting accuracy modifier, a double with a value between 0 and 1, that serves as a modifier
     * to other variables in determining hit chance with a ranged attack.
     */
    @Override
    public double getShootingAccuracyModifier() {
        return this.shootingAccuracyModifier;
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
     * Get the walking speed of this Character - the integer value that will be deducted from the distance
     * between this character and their opponent when this character moves in battle.
     *
     * @return get the walking speed of this character
     */
    public int getWalkingSpeed() {
        return walkingSpeed;
    }

    /**
     * Gets the model.IWeapon implementing object that this character is wielding.
     *
     * @return the model.IWeapon implementing object that this character is wielding.
     */
    public IWeapon getWeapon() {
        return this.equippedWeapon;
    }

    /**
     * Returns this character's status in the arena. Returns true if this character has hit points left. Otherwise,
     * returns false.
     *
     * @return the character's status in the arena.
     */
    public Boolean inTheFight() {
        return this.inTheFight;
    }

    /* ######################################################################## */
    /* ############################ Setter Methods ############################ */
    /* ######################################################################## */

    /**
     * Sets the moveState of this character, which determines whether they will close distance when
     * their attack method is called.
     *
     * @param moveState the Boolean value to set the moveState to
     */
    public void setMoveState(boolean moveState) {
        this.moveState = moveState;
    }

    /**
     * Resets ICharacter implementing object instances between rounds in the Thunderdome.
     */
    public void resetCharacter() {

        // reset case for ranged weapons - if a Character was using a ranged weapon as a melee weapon at the end of
        // the last round of battle, but their ranged weapon is still valid as a ranged weapon, they will switch back
        // to that weapon between rounds
        IWeapon rangedVersion = this.getWeapon().getRangedVersion();
        if (rangedVersion != null && !rangedVersion.isBroken() && !rangedVersion.getAmmunitionGone()) {
            this.setWeapon(rangedVersion);
            rangedVersion.setUser(this);
        }

        // this weapon sets the user
        this.getWeapon().setUser(this);
    }

    /**
     * Sets the model.IWeapon implementing object that this character will use for attacks.
     *
     * @param weapon the model.IWeapon implementing object that this character will equip.
     */
    public void setWeapon(IWeapon weapon) throws IllegalArgumentException {

        // null guard the setting a character's weapon
        if (weapon == null) {
            throw new IllegalArgumentException("May not assign a null weapon");
        }

        // have this character equip the weapon
        this.equippedWeapon = weapon;
    }

}
