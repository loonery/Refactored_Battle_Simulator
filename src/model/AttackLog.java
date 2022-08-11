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


    private boolean attackerWeaponBreak = false;
    private
    private boolean attackHit = false;
    private double damageDone = -1.00;


    public AttackLog(ICharacter attacker, ICharacter defender) {

        if (attacker == null || defender == null) {
            throw new IllegalArgumentException("attackLogs may not have null arguments.");
        }

        this.attacker = attacker;
        this.attackerWeapon = attacker.getEquippedWeapon();
        this.defender = defender;
        this.defenderWeapon = defender.getEquippedWeapon();

    }

    public void setAttackHit(boolean attackHit) {
        this.attackHit = attackHit;
    }

    public void setDamageDone(double damageDone) {
        this.damageDone = damageDone;
    }

    public void setAttackerWeaponBreak() {
        this.attackerWeaponBreak = true;
    }
}
