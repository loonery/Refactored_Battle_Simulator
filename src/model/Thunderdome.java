package model;

import java.util.HashMap;

/**
 * The Thunderdome is the gameInstance
 */
public class Thunderdome implements IThunderdome {

    // Keeps track of the distance between fighters
    private int fighterDistance;

    // The weapons remaining in the Thunderdome weapon rack
    private HashMap<String, IWeapon> weaponsRack;

    // Keeps track of the characters who have not
    // been knocked out of the fight and are not currently fighting
    private HashMap<String, ICharacter> remainingContestants;

    // Array keeps 2 characters and replaces the contestant that is
    // defeated with some other contestant
    private ICharacter[] currentFighters;

    /**
     * Constructor instantiates a new Thunderdome instance
     */
    public Thunderdome() {
        currentFighters = new ICharacter[2];
        this.weaponsRack = new HashMap<String, IWeapon>();
        this.remainingContestants = new HashMap<String, ICharacter>();
    }

    /**
     * Method that instantiates Weapon objects and adds them to the weaponRack.
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
        weaponsRack.put(name, addedWeapon);

    }


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
        this.weaponsRack.put(name, addedWeapon);

    }


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
        this.remainingContestants.put(name, addedCharacter);
    }

    private void removeFighter(ICharacter contestant){

    }


    public void run() {

        // todo: work on implementing the text based running, and finding a way to abstract the details of the
        // text printing.
                // - thinking that I could have the controller send the text to the view?
                // - the view should just have hardcoded text for certain portions of the game loop that the
                // controller will access







    }

}
