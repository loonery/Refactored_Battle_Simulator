package model;

import java.util.Random;

/**
 * The RangedWeapon class
 */
public class RangedWeapon extends Weapon {

    private final int rangedStrength;         // the analog to meleeStrength in melee weapons
    private final double accuracy;            // the ease of ability to hit an attack with this weapon
    private final IWeapon meleeVersion;       // every ranged weapon may be used as a melee weapon
    private final int LOWER_BOUND_RANGED_DAMAGE = 1;

    //todo: implement melee damage functions for the ranged weapon class
    private int ammunition;             // the ammunition that this weapon holds

    /* ############################################################################### */
    /* ############################ Constructor ############################ */
    /* ############################################################################### */

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

    /* ############################################################################### */
    /* ############################ State Getter Methods ############################ */
    /* ############################################################################### */

    public Integer getAmmunition() {
        return this.ammunition;
    }

    /**
     * Returns the melee "version" of this ranged weapon, which embodies the idea of this ranged
     * weapon being used as a melee weapon.
     *
     * @return the melee version of this Ranged Weapon
     */
    private IWeapon getMeleeVersion() {
        return this.meleeVersion;
    }

    /**
     * Returns the ranged strength of this weapon.
     *
     * @return the rangedStrength of this weapon
     */
    private int getRangedStrength() {
        return this.rangedStrength;
    }

    /* ############################################################################### */
    /* ############################ Combat Methods ############################ */
    /* ############################################################################### */

    /**
     * Controls behavior for a RangedWeapon hitting an attack, and stores the information about the successful
     * attack in the IAttackLog object that is passed in.
     *
     * @param attackLog The attackLog object that visits this method
     */
    @Override
    public void handleHit(IAttackLog attackLog) {

        attackLog.setAttackHit(true);

        // Apply wear to the ranged weapon and use a unit of ammunition
        this.applyWear();
        this.useAmmunition();

        // if the weapon is broken or out of ammunition...
        if (this.isBroken() && this.getAmmunition() == 0) {

            attackLog.setWeaponBroke(true);


            // set the user's weapon to the melee version of this ranged weapon.
            // The rationale of this decision is that a weapon that breaks
            // from ranged-use is likely not entirely ineffective as a melee
            // weapon
            this.getUser().setWeapon(this.getMeleeVersion());
            this.setUser(null);
        }
        else if (this.getAmmunition() == 0)
        {
            attackLog.setAmmunitionGone(true);

            // set the user's weapon to the melee version of this ranged weapon.
            // when ammunition is exhausted
            this.getUser().setWeapon(this.getMeleeVersion());
            this.setUser(null);
        }
    }

    /**
     * Controls behavior for a RangedWeapon missing an attack, and stores the information about the missed
     * attack in the IAttackLog object that is passed in.
     *
     * @param attackLog The attackLog object that visits this method
     */
    @Override
    public void handleMiss(IAttackLog attackLog) {

        // for ranged weapons, a hit and a miss apply the same wear and
        // ammunition loss
        this.handleHit(attackLog);

        // set the attack log such that it stores the correct information
        // that the attack hit, since sending it to the handleHit method
        // will say otherwise
        attackLog.setAttackHit(false);
    }

    /**
     * Returns the chance that this RangedWeapon will hit an attack. Utilizes information about the
     * ICharacter user of this RangedWeapon to determine the chance of hitting.
     *
     * @return the hit probability as double between 0 and 1
     */
    @Override
    public double rollHitChance() {
        // the chance of hitting a shot is the determined by the weapon's inherent accuracy
        // and the user's accuracy with a ranged weapon
        return this.getUser().getShootingAccuracyModifier() * this.accuracy;
    }

    /**
     * Returns a double that reflects the damage.
     *
     * @return the damage value rolled from an attack, or -1 if the attack misses.
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

    /* ############################################################################### */
    /* ############################ State Modifier Methods ############################ */
    /* ############################################################################### */

    /**
     * Links this weapon with some user and the user with some weapon. Sets the moveState of the
     * user.
     *
     * @param user the character to which this weapon will be bound.
     */
    @Override
    public void setUser(ICharacter user) {
        super.setUser(user);

        // When a ranged weapon is equipped, the user will have their
        // moveState set to false, reflecting that they are trying
        // to keep distance from their opponent.
        this.getUser().setMoveState(false);
    }

    /**
     * Decrements the ammunition that is held by this weapon. If ammunition is considered
     */
    private void useAmmunition() {
        this.ammunition -= 1;
    }

}
