package view;

import model.IAttackLog;
import model.ICharacter;
import model.IWeapon;

import java.util.List;

public interface IThunderdomeView {

    void displayBareHands();

    void displayBeginBattle();

    void displayFighterSelectionScreen(List<ICharacter> fighters);

    void displayHitMelee();

    void displayHitRanged();

    void displayMeleeCombat();

    void displayMissedMelee();

    void displayMissedRanged();

    void displayMovement();

    void displayWeaponSelectionScreen(List<IWeapon> weapons, ICharacter characterToArm);

    void displayWelcomeScreen() throws InterruptedException;

    void nextChallenger();

    void pronounceVictor(ICharacter victor);

    void renderAttackLog(IAttackLog attackLog);
}
