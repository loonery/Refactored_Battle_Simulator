package model;

/**
 * The interface Weapon.
 */
public interface IWeapon {



    ICharacter getUser();

    String getName();

    String getDescription();

    int getDurability();

    double rollWeaponDamage();

    double rollHitChance();

    boolean isBroken();

    void handleMiss(IAttackLog attackLog);

    void handleHit(IAttackLog attackLog);

    void applyWear();

    void setUser(ICharacter user);

}
