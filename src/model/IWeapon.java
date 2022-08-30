package model;

/**
 * The interface Weapon.
 */
public interface IWeapon {

    /* ############################################################################### */
    /* ############################ Getter Methods ############################ */
    /* ############################################################################### */

    boolean getAmmunitionGone();

    String getDescription();

    int getDurability();

    double getEncumbrance();

    int getMeleeStrength();

    String getName();

    ICharacter getUser();

    IWeapon getRangedVersion();

    boolean isBroken();


    /* ############################################################################### */
    /* ############################ Combat Methods ############################ */
    /* ############################################################################### */

    void applyWear();

    void handleHandToHand();

    void handleHit(IAttackLog attackLog);

    void handleMiss(IAttackLog attackLog);

    double rollHitChance();

    double rollWeaponDamage();

    /* ############################################################################### */
    /* ############################ Setter Methods ############################ */
    /* ############################################################################### */

    void setUser(ICharacter user);

    void setRangedVersion(IWeapon rangedVersion);

}
