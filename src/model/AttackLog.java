package model;

/**
 * This class is used to hold data about an attack on one character from another. This data is returned from
 * the ICharacter attack method so that side effect information from an attack may be fed to the IView implementing
 * class.
 */
public class AttackLog implements IAttackLog {

    private final boolean attackerMoveState;
    private ICharacter attacker;
    private IWeapon attackerWeapon;
    private ICharacter defender;
    private boolean attackerWeaponBreak = false;
    private boolean attackHit = false;
    private double damageDone = 0.00;
    private int distanceBetween;
    private boolean rangeClosed = false;
    private IWeapon newWeapon = null;
    private boolean defenderFelled = false;
    private boolean ammunitionGone = false;

    // ######################################################################################### //
    // ###################################### Constructor ###################################### //
    // ######################################################################################### //

    /**
     * Constructor for an attackLog. AttackLogs store information about attacks from 1 character to another.
     *
     * @param attacker        the attacker for the attack being recorded in this log
     * @param defender        the defender against the attack being recorded in this log
     * @param distanceBetween the distance between the two fighters when the attack took place
     */
    public AttackLog(ICharacter attacker, ICharacter defender, int distanceBetween) {

        if (attacker == null || defender == null) {
            throw new IllegalArgumentException("attackLogs may not have null arguments.");
        }

        this.attacker = attacker;
        this.attackerMoveState = attacker.getMoveState();
        this.attackerWeapon = attacker.getWeapon();
        this.defender = defender;
        this.distanceBetween = distanceBetween;
    }

    // ######################################################################################### //
    // ######################################## Getters ######################################## //
    // ######################################################################################### //

    /**
     * Tells whether the ammunition in the attacker's weapon is gone as a result of the attack.
     * False by default.
     *
     * @return whether the ammunition is empty in the attacker's IWeapon after the attack recorded.
     */
    @Override
    public boolean getAmmunitionGone() {
        return ammunitionGone;
    }

    /**
     * Get the attacking ICharacter of the attack this AttackLog is recording.
     *
     * @return the attacker
     */
    @Override
    public ICharacter getAttacker() {
        return attacker;
    }

    /**
     * Get the moveState of the attacker at the time of the attack this AttackLog is recording.
     *
     * @return the attacker's moveState at the time of the attack.
     */
    public boolean getAttackerMoveState() {
        return attackerMoveState;
    }

    /**
     * The IWeapon object that the attacker uses during the attack that this AttackLog is recording.
     *
     * @return the attacker's IWeapon object at the time of this attack
     */
    @Override
    public IWeapon getAttackerWeapon() {
        return attackerWeapon;
    }

    /**
     * Get the damage done in the attack that this AttackLog is recording.
     *
     * @return the damage done in the attack
     */
    @Override
    public double getDamageDone() {
        return damageDone;
    }

    /**
     * Returns the defender in the attack that this AttackLog is recording.
     *
     * @return the ICharacter that is defending against an attack (or move)
     */
    @Override
    public ICharacter getDefender() {
        return defender;
    }

    /**
     * Returns the distance that is recorded between the two ICharacters in combat.
     *
     * @return the integer distance between the two fighters in
     * the battle this AttackLog is recording
     */
    @Override
    public int getDistanceBetween() {
        return this.distanceBetween;
    }

    /**
     * If the ICharacter has replaced their original weapon with a new weapon
     * (due to breaking, ammunition, or when ranged is closed
     */
    @Override
    public IWeapon getNewWeapon() {
        return newWeapon;
    }

    /**
     * Tells whether the attack this AttackLog is recording hit the opponent.
     *
     * @return whether the attack hit
     */
    @Override
    public boolean isAttackHit() {
        return attackHit;
    }

    /**
     * Tells whether the attacker's weapon broke during an attack.
     *
     * @return whether the attacker's weapon broke
     */
    @Override
    public boolean isAttackerWeaponBreak() {
        return attackerWeaponBreak;
    }

    /**
     * Tells whether the defender was felled during the attack that this AttackLog is recording.
     *
     * @return whether the defender was felled.
     */
    @Override
    public boolean isDefenderFelled() {
        return defenderFelled;
    }

    /**
     * Tells whether the range is closed between the two characters during a "move" attack. Note that this will only
     * return true on the AttackLog recording the specific move that closed the range.
     *
     * @return whether the range is closed between the two ICharacters.
     */
    @Override
    public boolean rangedClosed() {
        return this.rangeClosed;
    }

    // ######################################################################################### //
    // ######################################## Setters ######################################## //
    // ######################################################################################### //

    /**
     * Sets the ammunition in the attacker's weapon to be gone as a result of the attack.
     */
    public void setAmmunitionGone(boolean ammunitionGone) {
        this.ammunitionGone = ammunitionGone;
    }

    /**
     * Whether the attack that this AttackLog is recording hit its intended opponent.
     *
     * @param attackHit whether the attack hit
     */
    public void setAttackHit(boolean attackHit) {
        this.attackHit = attackHit;
    }

    /**
     * Set whether the attacker's weapon broke during the attack this AttackLog is recording.
     *
     * @param attackerWeaponBreak whether the attacker's weapon broke during the attack
     */
    public void setAttackerWeaponBreak(boolean attackerWeaponBreak) {
        this.attackerWeaponBreak = attackerWeaponBreak;
    }

    /**
     * Set the damage done during the attack this AttackLog is recording.
     *
     * @param damageDone the damage done during the attack
     */
    public void setDamageDone(double damageDone) {
        this.damageDone = damageDone;
    }

    /**
     * Set whether the defender was felled during the attack this AttackLog is recording.
     *
     * @param defenderFelled whether the defender was felled
     */
    public void setDefenderFelled(boolean defenderFelled) {
        this.defenderFelled = defenderFelled;
    }

    /**
     * Set the distance between the two Characters during the attack this AttackLog is recording.
     *
     * @param distanceBetween the distance between the two Characters
     */
    public void setDistanceBetween(int distanceBetween) {this.distanceBetween = distanceBetween;}

    /**
     * If it exists, set the weapon that the attacker switches to when a case arises that causes them to switch
     * weapons during the attack this AttackLog is recording.
     *
     * @param newWeapon the IWeapon implementing object the Character switches to during this attack
     */
    public void setNewWeapon(IWeapon newWeapon) {
        this.newWeapon = newWeapon;
    }

    /**
     * Set that the range is closed between the characters during the attack this AttackLog is recording.
     *
     * @param rangeClosed whether the range is closed between the two ICharacters
     */
    public void setRangeClosed(boolean rangeClosed) {
        this.rangeClosed = rangeClosed;
    }
}
