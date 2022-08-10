package model;

public interface IWeapon {

    public double rollWeaponDamage();

    public int getDurability();

    public boolean isBroken();

    public Integer getAmmunition();

    public String getName();

    public void setUser(ICharacter user);

    public ICharacter getUser();

}
