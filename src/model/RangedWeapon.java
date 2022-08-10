package model;

import java.util.Random;

/**
 * The RangedWeapon class
 */
public class RangedWeapon extends Weapon {

    private int rangedStrength;         // the analog to meleeStrength in melee weapons
    private double accuracy;            // the ease of ability to hit an attack with this weapon
    private int ammunition;             // the ammunition that this weapon holds
    private IWeapon meleeVersion;       // every ranged weapon may be used as a melee weapon

    //todo: implement melee damage functions for the ranged weapon class

    private final int LOWER_BOUND_RANGED_DAMAGE = 1;

    /**
     * Creates a Weapon object implementing the IWeapon object to be used by a Character implementing class.
     * A Weapon object's attributes will influence a character's attack
     *
     * @param name The name of this IWeapon object
     * @param meleeStrength the strength of this weapon when used in a melee context
     * @param durability the durability of this weapon. Drains linearly
     * @param encumbrance the level to which this weapon impacts hit-chance of an attack when used
     * @param rangedStrength the ability of the weapon to inflict ranged damage
     * @param ammunition the ammunition that the weapon has available
     * @param accuracy the ease of ability to hit an attack with this weapon
     */
    public RangedWeapon(String name, int meleeStrength, int durability, int encumbrance,
                        int rangedStrength, int ammunition, double accuracy, String description) {

        // pass information to super constructor
        super(name, meleeStrength, durability, encumbrance, description);

        // guard values
        if (ammunition <= 0 || rangedStrength <= 0 || accuracy <= 0) {
            throw new IllegalArgumentException("Ranged weapon values may not be initialized to 0 or less.");
        }

        this.rangedStrength = rangedStrength;
        this.accuracy = accuracy;
        this.ammunition = ammunition;

        // The melee version of a Ranged Weapon is used when the ranged version breaks or runs out of ammo
        this.meleeVersion = new Weapon(this.getName(), this.getMeleeStrength(), this.getDurability(),
                                       this.getEncumbrance(), this.getName() + "'s melee manifestation");
    }

    /**
     * @return the damage value rolled from an attack.
     */
    @Override
    public double rollWeaponDamage() {

        // null guard the user of this weapon
        if (this.getUser() == null) {
            throw new NullPointerException("Cannot roll damage on a weapon that has no user");
        }

        Random rand = new Random();

        // the chance of hitting a shot is the determined by the weapon's inherent accuracy
        // and the user's accuracy with a ranged weapon
        double hitChance;
        hitChance = this.getUser().getShootingAccuracyModifer() * this.accuracy;

        // determine whether this shot will hit with RGN
        double roll = rand.nextFloat();
        boolean attackHits = (roll <= hitChance);

        // todo: Factor this out when it makes sense to do so. We are no longer following a separation
        //  of concerns.
            // it might make sense to apply the wear during a character's attack method, not during
            // the calculate damage method

        // Apply wear and decrement ammunition regardless of shot outcome.
        this.applyWear();
        this.useAmmunition();

        // If this weapon breaks, the weapon tells the user to use this weapon's melee manifestation
        // todo: think about how to extract the side effect of a ranged weapon breaking or running out of ammo
        //  in the attack log
        if (this.isBroken()) {
            this.handleBroken();
        }

        // if the shot hits, apply weapon wear and calculate random damage
        if (attackHits) {
            return (rand.nextFloat(LOWER_BOUND_RANGED_DAMAGE, this.getRangedStrength()));
        }
        // return -1 otherwise, to indicate that the attack missed
        return -1.00F;
    }

    protected void handleBroken() {
        this.getUser().setWeapon(this.getMeleeVersion());
        this.setUser(null);
    }

    /**
     * Decrements the ammunition that is held by this weapon. If ammunition is considered
     */
    private void useAmmunition() {
        this.ammunition -= 1;
        if (ammunition <= 0) {
            this.setBroken(true);
        }
    }

    private IWeapon getMeleeVersion() {
        return this.meleeVersion;
    }

    public int getAmmunition() {
        return this.ammunition;
    }


    /**
     * Returns the ranged strength of this weapon.
     *
     * @return
     */
    public int getRangedStrength() {
        return this.rangedStrength;
    }
}
