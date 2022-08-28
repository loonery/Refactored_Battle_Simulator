package model;

public interface IAttackLog {

    boolean getAmmunitionGone();

    ICharacter getAttacker();

    boolean getAttackerMoveState();

    IWeapon getAttackerWeapon();

    double getDamageDone();

    ICharacter getDefender();

    int getDistanceBetween();

    IWeapon getNewWeapon();

    boolean isAttackHit();

    boolean isAttackerWeaponBreak();

    boolean isDefenderFelled();

    boolean rangedClosed();

    void setAmmunitionGone(Boolean ammunitionGone);

    void setAttackHit(boolean attackHit);

    void setAttackerWeaponBreak(boolean attackerWeaponBreak);

    void setDamageDone(double damageDone);

    void setDefenderFelled(boolean defenderFelled);

    void setDistanceBetween(int distanceBetween);

    void setNewWeapon(IWeapon newWeapon);

    void setRangeClosed(boolean rangeClosed);

}
