package model;

/**
 * The ICharacter interface expresses the contract for all ICharacter implementing classes.
 */
public interface ICharacter {

    /* #################################################################################### */
    /* ############################ Combat-controlling methods ############################ */
    /* #################################################################################### */

    void attack(ICharacter defender, IAttackLog attackLog) throws IllegalArgumentException;

    int move(ICharacter character, int fighterDistance, IAttackLog attackLog);

    void takeDamage(double damage) throws IllegalArgumentException;

    /* ######################################################################## */
    /* ############################ Getter Methods ############################ */
    /* ######################################################################## */

    String getBio();

    double getCombatProwess();

    boolean getMoveState();

    String getName();

    double getShootingAccuracyModifier();

    int getStrength();

    int getWalkingSpeed();

    IWeapon getWeapon();

    Boolean inTheFight();

    /* ######################################################################## */
    /* ############################ Setter Methods ############################ */
    /* ######################################################################## */

    void setMoveState(boolean moveState);

    void resetCharacter();

    void setWeapon(IWeapon weapon);
}
