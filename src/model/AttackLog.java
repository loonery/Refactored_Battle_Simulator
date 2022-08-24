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
    private IWeapon defenderWeapon;


    // Initial values of the attack prior to more information
    private boolean attackerWeaponBreak = false;
    private boolean attackHit = false;
    private double damageDone = -1.00;


    private boolean weaponBroke = false;
    private IWeapon newWeapon = null;
    private boolean defenderFelled = false;
    private Boolean ammunitionGone = false;

    public AttackLog(ICharacter attacker, ICharacter defender) {

        if (attacker == null || defender == null) {
            throw new IllegalArgumentException("attackLogs may not have null arguments.");
        }

        this.attacker = attacker;
        this.attackerWeapon = attacker.getWeapon();
        this.defender = defender;
        this.defenderWeapon = defender.getWeapon();

    }

    public void setAmmunitionGone(Boolean ammunitionGone) {
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

    public void setNewWeapon(IWeapon newWeapon) {
        this.newWeapon = newWeapon;
    }

    public void setWeaponBroke(boolean weaponBroke) {
        this.weaponBroke = weaponBroke;
    }

}
