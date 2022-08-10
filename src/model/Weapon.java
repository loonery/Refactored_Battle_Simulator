package model;

import java.util.Random;

/**
 * The Weapon class implements the IWeapon interface and as an abstract class, houses common functionality for all
 * subclasses that represent different weapon variants.
 */
public class Weapon implements IWeapon {

    private String name;                // each weapon has a unique namey
    private String description;         // each weapon should have a description
    private int meleeStrength;          // each weapon has a meleeStrength, melee weapons and non-melee weapons
    private int rangedStrength;         // each weapon has a meleeStrength, ranged weapons and non-ranged weapons
    private int durability;             // durability acts as the "hit points" of a Weapon object
    private double encumbrance;         // the encumbrance of a weapon impacts the user's ability to land an attack
    private ICharacter user;            // the user of this weapon
    private Boolean broken = false;     // the status of the weapon as broken or not broken

    private final int LOWER_BOUND_WEAR_APPLIED = 1;     // the minimum wear applied to a weapon if its attack hits
    private final int UPPER_BOUND_WEAR_APPLIED = 5;     // the maximum wear applied to a weapon if its attack hits
    private final int DURABILITY_BREAKING_POINT = 0;    // if durability of any weapon hits this point it breaks
    private final int LOWER_BOUND_MELEE_DAMAGE = 1;     // the minimum damage that any melee attack can do if it hits


    /* ############################ Constructor ############################ */
    /**
     * Creates a Weapon object implementing the IWeapon object to be used by a Character implementing class.
     * A Weapon object's attributes will influence a character's attack
     *
     * @param name The name of this IWeapon object
     * @param meleeStrength the strength of this weapon when used in a melee context
     * @param durability the durability of this weapon. Drains linearly
     * @param encumbrance the level to which this weapon impacts hit-chance of an attack when used
     */
    public Weapon(String name, int meleeStrength, int durability, double encumbrance, String description) {

        // null check object input
        if (name == null || description == null || name.isBlank() || description.isBlank()) {
            throw new IllegalArgumentException("name of weapon may not be null");
        }

        // bound integer inputs
        if (meleeStrength <= 0 || durability <= 0 || encumbrance <= 0) {
            throw new IllegalArgumentException("Cannot initialize weapon attributes at 0 or less");
        }

        this.name = name;
        this.meleeStrength = meleeStrength;
        this.durability = durability;
        this.encumbrance = encumbrance;
        this.description = description;
    }


    /**
     * Returns a double that reflects the damage.
     *
     * @return the damage value rolled from an attack, or -1 if the attack misses.
     */
    @Override
    public double rollWeaponDamage() {

        // null guard when there is no user of this weapon
        if (this.getUser() == null) {
            throw new NullPointerException("Cannot roll damage on a weapon that has no user");
        }

        // the chance of landing an attack is the determined by the weapon's encumbrance
        // and the user's prowess with a melee weapon
        double hitChance;
        hitChance = this.getEncumbrance() * this.getUser().getWeaponProwess();

        // determine whether this shot will hit with RGN
        Random rand = new Random();
        double roll = rand.nextFloat();
        boolean attackHits = (roll <= hitChance);

        // todo: Factor this out when it makes sense to do so. We are no longer following a separation
        //  of concerns.
        // it might make sense to apply the wear during a character's attack method, not during
        // the calculate damage method

        // if the shot hits, apply weapon wear and calculate random damage
        if (attackHits) {
            this.applyWear();
            return (rand.nextFloat(LOWER_BOUND_MELEE_DAMAGE, this.getMeleeStrength()));
        }
        // return -1 otherwise, to indicate that the attack missed
        return -1.00F;
    }



    /* ############################ Public Getter Methods ############################ */

    public String getName() {
        return this.name;
    }

    public ICharacter getUser() {
        return this.user;
    }

    public int getMeleeStrength() {
        return this.meleeStrength;
    }

    public int getDurability() {
        return this.durability;
    }

    public double getEncumbrance() {
        return this.encumbrance;
    }

    public boolean isBroken() {
        return this.broken;
    }

    /* ############################ Public Setter Methods ############################ */

    /**
     * Links this weapon with some user and the user with some weapon.
     *
     * @param user the character to which this weapon will be bound.
     */
    public void setUser(ICharacter user) {

        // permits null values in instances when the weapon has no user
        this.user = user;
    }

    protected void setBroken(boolean broken) {
        this.broken = broken;
    }

    protected void handleBroken() {
        this.setUser(null);
    }

    /**
     * Applies randomly generated wear to the weapon and, if durability goes below 0, sets its status to broken.
     */
    public void applyWear() {
        Random rand = new Random();

        // decrement the durability
        this.durability -= (rand.nextInt(LOWER_BOUND_WEAR_APPLIED, UPPER_BOUND_WEAR_APPLIED));

        // if after the weapon is used it is broken, change its isBroken status and print a message
        if (this.getDurability() <= DURABILITY_BREAKING_POINT) {
            this.broken = true;
        }

        if (this.isBroken()) {
            handleBroken();
        }

    }







}
