package view.text_view;

import model.IAttackLog;
import model.ICharacter;
import model.IWeapon;
import model.RangedWeapon;
import view.IThunderdomeView;

import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Provides a text-based view to render combat in the model.IThunderdome implementing class.
 */
public class ThunderdomeTextView implements IThunderdomeView {

    private final int WELCOME_SCREEN_DISPLAY_MILLISECONDS = 2000;


    /* ############################################################################### */
    /* ############################ Static Graphics ############################ */
    /* ############################################################################### */

    /**
     * Display the Images.BARE_HANDS label.
     */
    @Override
    public void displayBareHands() {
        System.out.println(Images.BARE_HANDS);
    }

    /**
     * Display the Images.HIT label.
     */
    @Override
    public void displayHitMelee() {
        System.out.println(Images.HIT);
    }

    /**
     * Display the Images.RANGED_HIT label.
     */
    @Override
    public void displayHitRanged() {
        System.out.println(Images.RANGED_HIT);
    }

    /**
     * Display the BEGIN_BATTLE label.
     */
    @Override
    public void displayMeleeCombat() {
        System.out.println(Images.BEGIN_BATTLE);
    }

    /**
     * Display the Images.MISS label.
     */
    @Override
    public void displayMissedMelee() {
        System.out.println(Images.MISS);
    }

    /**
     * Display the Images.RANGED_MISS label.
     */
    @Override
    public void displayMissedRanged() {
        System.out.println(Images.RANGED_MISS);
    }


    /**
     * Display the Images.MOVE label.
     */
    @Override
    public void displayMovement() {
        System.out.println(Images.MOVE);
    }


    /**
     * Display text to ask for the next challenger.
     */
    @Override
    public void nextChallenger() {
        System.out.println(" Who will come dethrone them?\n");
    }

    /* ############################################################################### */
    /* ############################ Dynamic Graphics ############################ */
    /* ############################################################################### */

    /**
     * Visually indicates the beginning of combat between two challengers.
     */
    @Override
    public void displayBeginBattle() {
        System.out.println("=============================== Begin Battle ===============================");
    }

    /**
     * Visually guides the user of this program through selecting a Character to battle in the Thunderdome.
     *
     * @param fighters the ICharacteers the user may select for battle in the arena
     */
    @Override
    public void displayFighterSelectionScreen(List<ICharacter> fighters) {

        System.out.println("Here are the fighters...");

        int i = 0;
        for (ICharacter character : fighters) {
            System.out.println(++i + " " + character.getName() + ": " + character.getBio());
        }

        System.out.println("\nSelect a character by inputting their respective integer as it appears on the screen: ");
    }

    /**
     * Broken weapons must be rendered in two distinct circumstances. We rely on this helper
     * method to avoid excessive redundancy in the renderAttackLog method.
     *
     * @param attackLog         the attackLog that will render the information about the broken weapon
     *                          and the new IWeapon that the character with the broken weapon will use.
     * @param renderedAttackLog the StringBuilder object that is used to render the text output.
     */
    private void displayWeaponBreak(IAttackLog attackLog, StringBuilder renderedAttackLog) {

        this.displayBareHands();

        renderedAttackLog.append("\n").append(attackLog.getAttacker().getName()).append("'s ")
                .append(attackLog.getAttackerWeapon().getName()).append(" broke during their " +
                        "attack on ").append(attackLog.getDefender().getName()).append(".").append(" They" +
                        " have resorted to using ").append(attackLog.getNewWeapon().getName()).append(".");
    }

    /**
     * Visually guides the user through selecting a weapon for the character they have just selected.
     *
     * @param weapons the weapons available to the character
     * @param characterToArm the ICharacter implementing object being armed
     */
    @Override
    public void displayWeaponSelectionScreen(List<IWeapon> weapons, ICharacter characterToArm) {

        System.out.println("Here are the armaments available to " + characterToArm.getName() + "...");

        int i = 1;
        for (IWeapon weapon : weapons) {
            System.out.println(i + " " + weapon.getName() + " - " + weapon.getDescription());
            i++;
        }

        System.out.println("\nArm " + characterToArm.getName() + " with an available weapon by inputting its " +
                "respective" +
                " integer as it appears on the screen: ");

    }

    /**
     * Displays the screen that welcomes the program user into the simulation.
     *
     * @throws InterruptedException due to use of Thread.sleep() method.
     */
    @Override
    public void displayWelcomeScreen() throws InterruptedException {

        System.out.println("\nWelcome to the Thunderdome! The rules are simple:");
        sleep(WELCOME_SCREEN_DISPLAY_MILLISECONDS);
        System.out.println("""

                1) You will choose two fighters and pit them against one another in battle.
                2) They will start a distance away from one another.\s
                3) Fighters equipped with melee weapons will close range, while those equipped with range weapons will stay put.\s
                4) The fight will go on until one fighter remains standing. \s
                5) The remaining fighter will be pitted against another fighter of your choice until there are none left.""".indent(
                1));
    }


    /**
     * Pronounces the victor of the Thunderdome battle arena.
     *
     * @param victor the victor of the Thunderdome
     */
    @Override
    public void pronounceVictor(ICharacter victor) {
        System.out.println("It seems like no one will dethrone " + victor.getName() + "...they are the last " +
                "fighter standing.");
    }

    /**
     * Dynamically renders a passed in attackLog as text output.
     *
     * @param attackLog the attackLog to be rendered.
     */
    @Override
    public void renderAttackLog(IAttackLog attackLog) {

        System.out.println("_____________________________________________________________________");

        // Determine the action that was performed in the attack:
        //  A) the character moved, and did not attack
        StringBuilder renderedAttackLog = new StringBuilder("\n");
        if (attackLog.getAttackerMoveState()) {

            // render the character moving in text form
            this.displayMovement();

            // render the information from the attack log
            renderedAttackLog.append(attackLog.getAttacker().getName()).append(" moved towards ")
                    .append(attackLog.getDefender().getName())
                    .append(" closing the distance to ")
                    .append(attackLog.getDistanceBetween())
                    .append(" feet.");
            System.out.println(renderedAttackLog);
            renderedAttackLog = new StringBuilder();


            if (attackLog.rangedClosed()) {
                this.displayMeleeCombat();
                renderedAttackLog.append(attackLog.getAttacker().getName()).append(" closed the distance with ")
                        .append(attackLog.getDefender().getName()).append(" marking the beginning of " +
                                "hand-to-hand battle!");
                System.out.println(renderedAttackLog);
                renderedAttackLog = new StringBuilder();
            }

        // B) the character attacked...
        } else {

            // if the attack was with a ranged weapon...
            if (attackLog.getAttackerWeapon().getClass() == RangedWeapon.class) {

                renderedAttackLog.append(attackLog.getAttacker().getName()).append(" fired at ").append(
                                attackLog.getDefender().getName())
                        .append(" with their ").append(attackLog.getAttackerWeapon().getName()).append(" and");

                // and if their attack hit...

                if (attackLog.isAttackHit()) {
                    renderedAttackLog.append(" hit for ").append(
                            String.format("%.02f", attackLog.getDamageDone())).append(
                            " points of damage.");
                    this.displayHitRanged();

                    // otherwise if their attack missed...
                } else {
                    renderedAttackLog.append(" missed completely!");
                    this.displayMissedRanged();
                }

                System.out.println(renderedAttackLog);
                renderedAttackLog = new StringBuilder();

                // if the attacker's weapon broke...
                if (attackLog.isAttackerWeaponBreak()) {
                    this.displayWeaponBreak(attackLog, renderedAttackLog);

                    // otherwise if the ammunition is gone
                } else if (attackLog.getAmmunitionGone()) {

                    renderedAttackLog.append("\n").append(attackLog.getAttacker().getName()).append("'s ")
                            .append(attackLog.getAttackerWeapon().getName()).append(" ran out of ammunition during " +
                                    "their attack on ").append(attackLog.getDefender().getName()).append(".").append(" They" +
                                    " have resorted to using ").append(attackLog.getNewWeapon().getName());

                }

                System.out.println(renderedAttackLog);
                renderedAttackLog = new StringBuilder();    // Clear the stringBuilder

                // if the attack was a melee weapon...
            } else {

                renderedAttackLog.append(attackLog.getAttacker().getName()).append(" swung at ").append(
                                attackLog.getDefender().getName())
                        .append(" with their ").append(attackLog.getAttackerWeapon().getName()).append(" and ");

                // if their attack hit...
                if (attackLog.isAttackHit()) {

                    renderedAttackLog.append("hit for ").append(
                            String.format("%.02f", attackLog.getDamageDone())).append(
                            " points of damage.");
                    this.displayHitMelee();

                    // if the attacker's weapon broke during the attack...
                    if (attackLog.isAttackerWeaponBreak()) {
                        this.displayWeaponBreak(attackLog, renderedAttackLog);
                    }

                    // if their attack missed...
                } else {
                    renderedAttackLog.append("missed completely!");
                    this.displayMissedMelee();
                }

                System.out.println(renderedAttackLog);
                renderedAttackLog = new StringBuilder();    // Clear the stringBuilder
            }

            // if the defender is defeated in this process
            if (attackLog.isDefenderFelled()) {

                renderedAttackLog.append("\n").append(attackLog.getDefender().getName()).append(" was felled during " +
                                "the attack! ").append(attackLog.getAttacker().getName())
                        .append(" reigns in the Thunderdome.");

            }
        }

        System.out.println(renderedAttackLog);
    }

}

