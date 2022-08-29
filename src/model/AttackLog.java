package model;

/**
 * This class is used to hold data about an attack on one character from another. This data is returned from
 * the ICharacter attack method so that side effect information from an attack may be fed to the IView implementing
 * class.
 */
public class AttackLog implements IAttackLog {

    private ICharacter attacker;
    private IWeapon attackerWeapon;
    private ICharacter defender;

    // Initial values of the attack prior to more information
    private boolean attackerWeaponBreak = false;
    private boolean attackHit = false;
    private double damageDone = -1.00;

    private int distanceBetween;

    private boolean attackerMoveState;
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
     * @param attacker the attacker for the attack being recorded in this log
     * @param defender the defender against the attack being recorded in this log
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

    @Override
    public boolean getAmmunitionGone() {
        return ammunitionGone;
    }

    @Override
    public ICharacter getAttacker() {
        return attacker;
    }

    @Override
    public IWeapon getAttackerWeapon() {
        return attackerWeapon;
    }

    @Override
    public double getDamageDone() {
        return damageDone;
    }

    @Override
    public ICharacter getDefender() {
        return defender;
    }

    @Override
    public int getDistanceBetween() {
        return this.distanceBetween;
    }

    @Override
    public IWeapon getNewWeapon() {
        return newWeapon;
    }

    @Override
    public boolean rangedClosed() {
        return this.rangeClosed;
    }

    @Override
    public boolean isAttackHit() {
        return attackHit;
    }

    @Override
    public boolean isAttackerWeaponBreak() {
        return attackerWeaponBreak;
    }

    @Override
    public boolean isDefenderFelled() {
        return defenderFelled;
    }

    public boolean getAttackerMoveState() {
        return attackerMoveState;
    }


    // ######################################################################################### //
    // ######################################## Setters ######################################## //
    // ######################################################################################### //

    public void setAmmunitionGone(boolean ammunitionGone) {
        this.ammunitionGone = ammunitionGone;
    }

    public void setAttackHit(boolean attackHit) {
        this.attackHit = attackHit;
    }

    public void setAttackerWeaponBreak(boolean attackerWeaponBreak) {
        this.attackerWeaponBreak = attackerWeaponBreak;
    }

    public void setDamageDone(double damageDone) {
        this.damageDone = damageDone;
    }

    public void setDefenderFelled(boolean defenderFelled) {
        this.defenderFelled = defenderFelled;
    }

    public void setDistanceBetween(int distanceBetween) {this.distanceBetween = distanceBetween;}

    public void setNewWeapon(IWeapon newWeapon) {
        this.newWeapon = newWeapon;
    }

    public void setRangeClosed(boolean rangeClosed) {
        this.rangeClosed = rangeClosed;
    }

}
