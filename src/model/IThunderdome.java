package model;

import java.util.List;

public interface IThunderdome {

    void addCharacter(String[] args);

    void addMeleeWeapon(String[] args);

    void addRangedWeapon(String[] args);

    void removeFighter(int indexOf);

    void removeDefeatedFighter();

    void removeWeapon(int indexOf);

    Boolean fightersRemaining();

    public List<IAttackLog> battle();

    void setFighterDistance(int fighterDistance);

    List<IWeapon> getWeaponsRack();

    List<ICharacter> getFightersRemaining();

    void removeWeapon(String weaponName);

    void removeFighter(String weaponName);

}
