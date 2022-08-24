package model;

public interface IAttackLog {

    public void setAmmunitionGone(Boolean ammunitionGone);

    public void setAttackHit(boolean attackHit);

    public void setAttackerWeaponBreak(boolean attackerWeaponBreak);

    public void setDamageDone(double damageDone);

    public void setDefenderFelled(boolean defenderFelled);

    public void setNewWeapon(IWeapon newWeapon);

    public void setWeaponBroke(boolean weaponBroke);

}
