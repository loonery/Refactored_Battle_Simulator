package model;

public interface ICharacter {

    /**
     *
     * @param defender
     * @return
     * @throws IllegalArgumentException
     */
    public IAttackLog attack(ICharacter defender) throws IllegalArgumentException;

    /* ############################ Getter Methods ############################ */
    public double getShootingAccuracyModifier();

    public String getBio();

    public double getCombatProwess();

    public Boolean getFightStatus();

    boolean getMoveState();

    public String getName();

    int getStrength();

    public IWeapon getWeapon();

    int move(int fighterDistance);

    void setMoveState(boolean moveState);

    public void setWeapon(IWeapon weapon);

    public void takeDamage(double damage) throws IllegalArgumentException;
}
