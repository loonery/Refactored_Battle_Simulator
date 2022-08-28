package model;

public interface ICharacter {

    /**
     *
     * @param defender
     * @return
     * @throws IllegalArgumentException
     */
    public void attack(ICharacter defender, IAttackLog attackLog) throws IllegalArgumentException;

    /* ############################ Getter Methods ############################ */
    public double getShootingAccuracyModifier();

    public String getBio();

    public double getCombatProwess();

    public Boolean inTheFight();

    public boolean getMoveState();

    public String getName();

    int getStrength();

    public IWeapon getWeapon();

    int move(int fighterDistance, IAttackLog attackLog);

    void setMoveState(boolean moveState);

    public void setWeapon(IWeapon weapon);

    public void takeDamage(double damage) throws IllegalArgumentException;
}
