package model;

import java.util.Random;

/**
 * The RangedWeapon class
 */
public class RangedWeapon extends Weapon {

    private int ammunition;             // the ammunition that this weapon holds
    private final int rangedStrength;         // the analog to meleeStrength in melee weapons
    private final double accuracy;            // the ease of ability to hit an attack with this weapon
    private final IWeapon meleeVersion;       // every ranged weapon may be used as a melee weapon

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

        // guard against inappropriate values
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
     * @return the damage value rolled when an attack with this weapon hits.
     */
    @Override
    public double rollWeaponDamage() {

        // null guard the user of this weapon
        if (this.getUser() == null) {
            throw new NullPointerException("Cannot roll damage on a weapon that has no user");
        }

        Random rand = new Random();
        return (rand.nextFloat(LOWER_BOUND_RANGED_DAMAGE, this.getRangedStrength()));

    }



    /**
     * Decrements the ammunition that is held by this weapon. If ammunition is considered
     */
    private void useAmmunition() {
        this.ammunition -= 1;
    }

    private IWeapon getMeleeVersion() {
        return this.meleeVersion;
    }

    public Integer getAmmunition() {
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

    @Override
    public void handleHit() {

        // Apply wear to the ranged weapon and use a unit of ammunition
        this.applyWear();
        this.useAmmunition();

        // if the weapon is broken or out of ammunition...
        if (this.isBroken() || this.getAmmunition() == 0) {

            // set the user's weapon to the melee version of this ranged weapon.
            // The rationale of this decision is that a weapon that breaks
            // from ranged-use is likely not entirely ineffective as a melee
            // weapon
            this.getUser().setWeapon(this.getMeleeVersion());
            this.setUser(null);
        }
    }

    @Override
    public void handleMiss() {

        // for ranged weapons, a hit and a miss apply the same wear and
        // ammunition loss
        this.handleHit();
    }

    @Override
    public double rollHitChance() {
        // the chance of hitting a shot is the determined by the weapon's inherent accuracy
        // and the user's accuracy with a ranged weapon
        return this.getUser().getShootingAccuracyModifer() * this.accuracy;
    }

}
