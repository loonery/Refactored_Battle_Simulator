package view;

import model.ICharacter;
import model.IWeapon;

import java.util.HashMap;
import java.util.List;

public interface IThunderdomeView {

    void displayWelcomeScreen() throws InterruptedException;

    void displayMissedMelee();

    void displayHitMelee();

    void displayMissedRanged();

    void displayHitRanged();

    void displayMovement();

    void displayMeleeCombat();

    void displayWeaponBreak();

    void displayFighterSelectionScreen(List<ICharacter> fighters);

    void displayWeaponSelectionScreen(List<IWeapon> weapons);

}
