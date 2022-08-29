package model;

import java.util.List;

/**
 * The interface that all IThunderdome implementing classes must fulfill.
 */
public interface IThunderdome {

    /* #################################################################################### */
    /* ############################ Model Instantiation Methods ############################ */
    /* #################################################################################### */

    void addCharacter(String[] args);

    void addMeleeWeapon(String[] args);

    void addRangedWeapon(String[] args);

    /* #################################################################################### */
    /* ############################ Combat Supporting Methods ############################ */
    /* #################################################################################### */

    void armCharacter(int weaponIndex, ICharacter character);

    List<IAttackLog> battle();

    int getFighterDistance();

    /* #################################################################################### */
    /* ############################ Model State Change Methods ############################ */
    /* #################################################################################### */

    ICharacter placeFighterIntoArena(int fighterIndex);

    void removeDefeatedFighter();

    void setFighterDistance(int fighterDistance);

    /* #################################################################################### */
    /* ############################ Model State Getter Methods ############################ */
    /* #################################################################################### */

    boolean fightersRemaining();

    ICharacter[] getCurrentFighters();

    List<ICharacter> getFightersRemaining();

    ICharacter getVictoriousFighter();

    List<IWeapon> getWeaponsRack();

}
