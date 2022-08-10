package model;

public interface ICharacter {

    /**
     * model.Character types must be able to battle against one another.
     *
     * @param combatantA one of the two combatants in the battle simulator
     * @param combatantB one of the two combatants in the battle simulator
     */
    public IAttackLog attack(ICharacter defender) throws IllegalArgumentException;



    public void takeDamage(double damage) throws IllegalArgumentException;


    /* ############################ Getter Methods ############################ */
    public void getShootingAccuracyModifer();

    public void getWeaponProwess();

    public IWeapon getEquippedWeapon();

    public String getName();

    public void setWeapon(IWeapon weapon);
}
