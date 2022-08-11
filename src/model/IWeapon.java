package model;

public interface IWeapon {



    ICharacter getUser();

    String getName();

    String getDescription();

    int getDurability();

    double rollWeaponDamage();

    double rollHitChance();

    boolean isBroken();

    void handleMiss();

    void handleHit();

    void applyWear();

    void setUser(ICharacter user);

}
