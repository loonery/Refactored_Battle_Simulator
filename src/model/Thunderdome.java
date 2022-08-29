package model;

import java.util.*;

/**
 * The Thunderdome is the gameInstance
 */
public class Thunderdome implements IThunderdome {

    // Keeps track of the distance between fighters
    private int fighterDistance = 50;

    // The weapons remaining in the Thunderdome weapon rack
    private List<IWeapon> weaponsRack;

    // Keeps track of the characters who have not
    // been knocked out of the fight and are not currently fighting
    private List<ICharacter> remainingContestants;

    // Array keeps 2 characters and replaces the contestant that is
    // defeated with some other contestant
    private ICharacter[] currentFighters;

    /* #################################################################################### */
    /* #################################### Constructor ################################### */
    /* #################################################################################### */

    /**
     * Constructor instantiates a new Thunderdome instance
     */
    public Thunderdome() {
        currentFighters = new ICharacter[2];
        this.weaponsRack = new ArrayList<>();
        this.remainingContestants = new ArrayList<>();
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

        // args may not be null
        if (args == null) {
            throw new IllegalArgumentException("args array may not be null");
        }

        // guard args length
        if (args.length != 6) {
            throw new IllegalArgumentException("the length of the Character constructor arguments array must have 6 " +
                    "arguments");
        }

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

        // args may not be null
        if (args == null) {
            throw new IllegalArgumentException("args array may not be null");
        }

        // guard args length
        if (args.length != 5) {
            throw new IllegalArgumentException("the length of the Character constructor arguments array must have 6 " +
                    "arguments");
        }

        // Each argument from the argument array is parsed into the necessary data type
        String name = args[0];
        int meleeStrength = Integer.parseInt(args[1]);
        int durability = Integer.parseInt(args[2]);
        double encumbrance = Double.parseDouble(args[3]);
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

        // args may not be null
        if (args == null) {
            throw new IllegalArgumentException("args array may not be null");
        }

        // guard args length
        if (args.length != 8) {
            throw new IllegalArgumentException("the length of the Character constructor arguments array must have 6 " +
                    "arguments");
        }

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
     * Arm a character with a specific weapon and remove that weapon from the Weapons rack.
     *
     * @param weaponIndex the index of the weapon in the weaponsRack
     * @param character the ICharacter instance to which the selected IWeapon object will be assigned
     */
    @Override
    public void armCharacter(int weaponIndex, ICharacter character) {

        // remove the weapon from the weaponsRack, give it to the character
        // and tell the weapon who its user is.
        IWeapon weapon = this.getWeaponsRack().remove(weaponIndex);
        character.setWeapon(weapon);
        weapon.setUser(character);

    }

    /**
     * Rolls a 50/50 die to determine which of any two contestants will have their
     * attack() method called for a given roll.
     *
     * @return boolean expressing which ICharacter will attack first
     */
    private boolean attackRoll() {
        Random rand = new Random();
        return rand.nextInt(0, 101) < 51;
    }

    /**
     * Have 2 fighters battle against one another
     */
    public List<IAttackLog> battle() {

        // retain a log of all attacks that happen during a battle between two characters
        ArrayList<IAttackLog> battleLog = new ArrayList<>();
        ICharacter contestant1 = this.currentFighters[0];
        ICharacter contestant2 = this.currentFighters[1];

        // While there is no winner in the battle...
        while (noWinner()) {

            // roll a "die" to see who will attack for a given roll
            boolean attackRoll = attackRoll();

            // if the fighters have closed ranged...
            if (distanceClosed()) {

                // when the distance is closed, have weapons behave as they should
                // during hand-to-hand content
                contestant1.getWeapon().handleHandToHand();
                contestant2.getWeapon().handleHandToHand();

                // Randomly determine who will attack in the case that the two
                // contestants are in melee combat
                if (attackRoll) {
                    IAttackLog newAttackLog = new AttackLog(contestant1, contestant2, this.getFighterDistance());
                    battleLog.add(newAttackLog);
                    contestant1.attack(contestant2, newAttackLog);
                } else {
                    IAttackLog newAttackLog = new AttackLog(contestant2, contestant1, this.getFighterDistance());
                    battleLog.add(newAttackLog);
                    contestant2.attack(contestant1, newAttackLog);
                }
            }
            // case where the fighters have NOT closed ranged
            else
            {
                // cases where contestant 1 lands the attack roll, and either moves,
                // or attacks if their moveState dictates.
                if (attackRoll && !contestant1.getMoveState()) {
                    IAttackLog newAttackLog = new AttackLog(contestant1, contestant2, this.getFighterDistance());
                    battleLog.add(newAttackLog);
                    contestant1.attack(contestant2, newAttackLog);

                } else if (attackRoll && contestant1.getMoveState()) {
                    IAttackLog newAttackLog = new AttackLog(contestant1, contestant2, this.getFighterDistance());
                    battleLog.add(newAttackLog);
                    this.setFighterDistance(contestant1.move(this.getFighterDistance(), newAttackLog));

                // cases where contestant 2 lands the attack roll, and either moves,
                // or attacks if their moveState dictates.
                } else if (!attackRoll && !contestant2.getMoveState()) {

                    IAttackLog newAttackLog = new AttackLog(contestant2, contestant1, this.getFighterDistance());
                    battleLog.add(newAttackLog);
                    contestant2.attack(contestant1, newAttackLog);

                } else if (!attackRoll && contestant2.getMoveState()) {
                    IAttackLog newAttackLog = new AttackLog(contestant2, contestant1, this.getFighterDistance());
                    battleLog.add(newAttackLog);
                    this.setFighterDistance(contestant2.move(this.getFighterDistance(), newAttackLog));
                }
            }
        }

        return battleLog;
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
        // returns true when both fighters are still valid combatants
        return currentFighters[0].inTheFight() && currentFighters[1].inTheFight();
    }

    /* #################################################################################### */
    /* ############################ Model State Change Methods ############################ */
    /* #################################################################################### */

    /**
     * Places a new fighter into combat by removing them from the list of remaining fighters
     * and adding them to the currentFighters array.
     *
     * @return the ICharacter the is placed into the arena
     */
    @Override
    public ICharacter placeFighterIntoArena(int fighterIndex) {

        // extract the selected fighter from the list of remaining fighters
        ICharacter newChallenger = this.getFightersRemaining().remove(fighterIndex);

        // determine empty slot for fighter and add them
        for (int i = 0; i < getCurrentFighters().length; i++) {
            if (currentFighters[i] == null) {
                currentFighters[i] = newChallenger;
                return newChallenger;
            }
        }
        return newChallenger;
    }

    /**
     * Removes the defeated fighter from the Thunderdome.
     */
    @Override
    public void removeDefeatedFighter() {
        if (!currentFighters[0].inTheFight()) {
            this.currentFighters[0] = null;
        }
        if (!currentFighters[1].inTheFight()) {
            this.currentFighters[1] = null;
        }
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
    public boolean fightersRemaining() {
        return (this.remainingContestants.size() > 0);
    }

    /**
     * Get the current fighters in this Thunderdome.
     *
     * @return the array (size 2) of current ICharacters that are in combat
     */
    public ICharacter[] getCurrentFighters(){
        return this.currentFighters;
    }

    /**
     * Gets the List of ICharacters that are still candidates to be placed into
     * the Thunderdome (not currently fighting and not defeated).
     *
     * @return the List of ICharacters that are remaining in the Thunderdome
     */
    @Override
    public List<ICharacter> getFightersRemaining() {
        return this.remainingContestants;
    }

    /**
     * Returns the victorious fighter from the Thunderdome after a battle. If
     * both fighters are still in-combat with no victor, returns null.
     *
     * @return the victorious fighter from the Thunderdome if one exists
     */
    public ICharacter getVictoriousFighter() {

        if (currentFighters[0] != null && currentFighters[0].inTheFight()) {
            return this.currentFighters[0];
        } else if (currentFighters[1] != null && currentFighters[1].inTheFight()) {
            return this.currentFighters[1];
        }

        return null;
    }

    /**
     * Returns the Weapon objects that are remaining in the Thunderdome.
     *
     * @return the weapons remaining in the thunderdome as a List object
     */
    public List<IWeapon> getWeaponsRack() {
        return this.weaponsRack;
    }


}
