package model;

import java.util.*;

/**
 * The Thunderdome is the gameInstance
 */
public class Thunderdome implements IThunderdome {

    // Keeps track of the distance between fighters
    private int fighterDistance;

    // The weapons remaining in the Thunderdome weapon rack
    private List<IWeapon> weaponsRack;

    // Keeps track of the characters who have not
    // been knocked out of the fight and are not currently fighting
    private List<ICharacter> remainingContestants;

    // Array keeps 2 characters and replaces the contestant that is
    // defeated with some other contestant
    private ICharacter[] currentFighters;

    // Linked-List that holds a chronological list of
    private List<IAttackLog> simulationRecord;

    /* #################################################################################### */
    /* ############################ Constructor ############################ */
    /* #################################################################################### */

    /**
     * Constructor instantiates a new Thunderdome instance
     */
    public Thunderdome() {
        currentFighters = new ICharacter[2];
        this.weaponsRack = new ArrayList<>();
        this.remainingContestants = new ArrayList<>();
        this.simulationRecord = new ArrayList<IAttackLog>();
    }

    /* #################################################################################### */
    /* ############################ Model Instantiation Methods ############################ */
    /* #################################################################################### */

    /**
     * Adds an ICharacter to the model as a prospective fighter through an array of
     * String arguments.
     *
     * @param args The ordered arguments to the Character constructor as a String array
     */
    public void addCharacter(String[] args) {

        // todo: guard input for each of these to ensure args is appropriately sized

        // Each argument from the argument array is parsed into the necessary data type
        String name = args[0];
        int strength = Integer.parseInt(args[1]);
        int walkingSpeed = Integer.parseInt(args[2]);
        double shootingAccuracyModifier = Double.parseDouble(args[3]);
        double combatProwess = Double.parseDouble(args[4]);
        String bio = args[5];

        // create the character with the passed arguments and add them to the game
        ICharacter addedCharacter = new Character(name, strength, walkingSpeed, shootingAccuracyModifier,
                                                    combatProwess, bio);
        this.remainingContestants.add(addedCharacter);
    }

    /**
     * Adds an IWeapon to the model as a prospective fighter through an array of
     * String arguments.
     *
     * @param args The ordered arguments to the Weapon constructor as a String array
     */
    public void addMeleeWeapon(String[] args) {

        // todo: guard input for each of these to ensure args is appropriately sized

        // Each argument from the argument array is parsed into the necessary data type
        String name = args[0];
        int meleeStrength = Integer.parseInt(args[1]);
        int durability = Integer.parseInt(args[2]);
        int encumbrance = Integer.parseInt(args[3]);
        String description = args[4];

        // create the weapon with the passed arguments and add it to the game
        IWeapon addedWeapon = new Weapon(name, meleeStrength, durability, encumbrance, description);
        weaponsRack.add(addedWeapon);

    }

    /**
     * Adds an IWeapon to the model as a prospective fighter through an array of
     * String arguments.
     *
     * @param args The ordered arguments to the RangedWeapon constructor as a String array
     */
    public void addRangedWeapon(String[] args) {

        // todo: guard input for each of these to ensure args is appropriately sized

        // Each argument from the argument array is parsed into the necessary data type
        String name = args[0];
        int meleeStrength = Integer.parseInt(args[1]);
        int durability = Integer.parseInt(args[2]);
        int encumbrance = Integer.parseInt(args[3]);
        int rangedStrength = Integer.parseInt(args[4]);
        int ammunition = Integer.parseInt(args[5]);
        double accuracy = Double.parseDouble(args[6]);
        String description = args[7];

        // create the weapon with the passed arguments and add it to the game
        IWeapon addedWeapon = new RangedWeapon(name, meleeStrength, durability, encumbrance, rangedStrength,
                                                ammunition, accuracy, description);
        this.weaponsRack.add(addedWeapon);

    }


    /* #################################################################################### */
    /* ############################ Combat Supporting Methods ############################ */
    /* #################################################################################### */

    /**
     * Have 2 fighters battle against one another
     */
    public List<IAttackLog> battle() {


        // todo: look into having this method return a list of the battle logs, which the controller
        //  will send to the view for rendering


        ArrayList<IAttackLog> battleLog = new ArrayList<>();

        ICharacter contestant1 = this.currentFighters[0];
        ICharacter contestant2 = this.currentFighters[1];

        // todo: think a bit more about the mechanics of characters using ranged and melee
        //  weapons
        while (noWinner()) {

            // if the fighters have closed ranged...
            if (distanceClosed()) {

                // neither contestant tries to move position once they
                // have closed range
                contestant1.setMoveState(false);
                contestant2.setMoveState(false);

                // Randomly determine who will attack in the case that the two
                // contestants are in melee combat
                if (attackRoll()) {
                    battleLog.add(contestant1.attack(contestant2));
                } else {
                    battleLog.add(contestant2.attack(contestant1));
                }
            }
            else    // case where the fighters have not closed ranged
            {

                // cases where contestant 1 lands the attack roll, and either moves,
                // or attacks if their moveState dictates.
                if (attackRoll() && !contestant1.getMoveState()) {
                    battleLog.add(contestant1.attack(contestant2));
                } else if (attackRoll() && contestant1.getMoveState()) {
                    this.setFighterDistance(contestant1.move(this.getFighterDistance()));

                    // cases where contestant 2 lands the attack roll, and either moves,
                    // or attacks if their moveState dictates.
                } else if (!attackRoll() && !contestant2.getMoveState()) {
                    battleLog.add(contestant2.attack(contestant1));
                } else if (!attackRoll() && contestant2.getMoveState()) {
                    this.setFighterDistance(contestant1.move(this.getFighterDistance()));
                }
            }
        }

        return battleLog;
    }

    /**
     * Rolls a 50/50 die to determine which of any two contestants will have their
     * attack() method called for a given roll.
     *
     * @return boolean expressing which ICharacter will attack first
     */
    private boolean attackRoll() {
        Random rand = new Random();
        return rand.nextInt(0, 1) == 1;
    }

    /**
     * Tells whether the distance between two combatants has been closed.
     *
     * @return whether the combatants have closed range.
     */
    private boolean distanceClosed() {
        return fighterDistance <= 0;
    }

    /**
     * Returns the distance between two combatant fighters.
     *
     * @return the distance as an integer
     */
    public int getFighterDistance() {
        return fighterDistance;
    }

    /**
     * Tells whether there is a winner between a pair of combatants. Returns true
     * if there is no winner when called.
     *
     * @return whether there is a winner in the Thunderdome at the time of calling
     */
    private boolean noWinner() {
        // returns true
        return currentFighters[0].getFightStatus() && currentFighters[1].getFightStatus();
    }

    /* #################################################################################### */
    /* ############################ Model State Change Methods ############################ */
    /* #################################################################################### */

    /**
     * Places a new fighter into combat
     */
    public void fighterEnters() {

    }

    /**
     * Removes the defeated fighter from the Thunderdome.
     */
    @Override
    public void removeDefeatedFighter() {
        // todo:  guard input
        if (!currentFighters[0].getFightStatus()) {
            this.remainingContestants.remove(0);
        }
        if (!currentFighters[1].getFightStatus()) {
            this.remainingContestants.remove(1);
        }
    }

    /**
     * Removes a weapon from the Thunderdome when it becomes unavailable.
     */
    @Override
    public void removeWeapon(int indexOf) {

        //todo: null guard input
        this.getWeaponsRack().remove(indexOf);

    }

    /**
     * Sets the distance between fighters that are in combat.
     *
     * @param fighterDistance the distance between the two fighters expressed as an integer
     *                        of arbitrary units
     */
    public void setFighterDistance(int fighterDistance) {
        this.fighterDistance = fighterDistance;
    }

    /* #################################################################################### */
    /* ############################ Model State Getter Methods ############################ */
    /* #################################################################################### */

    /**
     * Tells whether there are any fighters (who have not fought) are remaining in
     * the Thunderdome.
     *
     * @return whether there are any fighters in the Thunderdome
     */
    @Override
    public Boolean fightersRemaining() {
        return (this.remainingContestants.size() == 0);
    }

    /**
     * Returns the Weapon objects that are remaining in the Thunderdome.
     *
     * @return the weapons remaining in the thunderdome as a List object
     */
    public List<IWeapon> getWeaponsRack() {
        return Collections.unmodifiableList(this.weaponsRack);
    }





}
