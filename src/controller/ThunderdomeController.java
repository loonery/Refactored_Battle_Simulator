package controller;

import model.IThunderdome;
import model.Thunderdome;
import view.ThunderdomeTextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Scanner;

public class ThunderdomeController implements IThunderdomeController {

    private Thunderdome thunderdomeInstance;           // This controller's instance of the game
    private ThunderdomeTextView thunderdomeView;       // This controller's view of the game

    // The gameContent folder is held as a constant
    private final String GAME_CONTENT_PATH = "src/gameContent";


    /**
     *
     * @param thunderdomeInstance
     * @param thunderdomeView
     * @throws FileNotFoundException
     * @throws IllegalArgumentException
     */
    public ThunderdomeController(Thunderdome thunderdomeInstance, ThunderdomeTextView thunderdomeView)
            throws FileNotFoundException, IllegalArgumentException {

        if (thunderdomeInstance == null || thunderdomeView == null) {
            throw new IllegalArgumentException("Neither the game instance nor the view may be null");
        }

        this.thunderdomeInstance = thunderdomeInstance;
        this.thunderdomeView = thunderdomeView;
        this.loadModel(new File(GAME_CONTENT_PATH));

    }


    /**
     *
     * @param gameContentFolder
     * @throws MissingResourceException
     * @throws FileNotFoundException
     */
    private void loadModel(File gameContentFolder) throws MissingResourceException, FileNotFoundException {

        // load the contents of each file
        // load different portions of the model dependent upon which file is being iterated
        // over in the loop
        for (File gameFile : Objects.<File>requireNonNull(gameContentFolder)) {
            switch (gameFile.getName()) {
                case "Characters.txt" -> loadCharacters(gameFile);
                case "MeleeWeapons.txt" -> loadMeleeWeapons(gameFile);
                case "RangedWeapons.txt" -> loadRangedWeapons(gameFile);
                default -> {
                    throw new MissingResourceException("Some critical game content file is missing");
                }
            }
        }
    }


    private void loadCharacters(File charactersFile) throws FileNotFoundException {

        if (charactersFile == null) {
            throw new IllegalArgumentException("No game content file may be null");
        }

        Scanner scanner = new Scanner(charactersFile);

        // read the file and tokenize each line of arguments
        while (scanner.hasNextLine()) {

            // iterate over all lines and split the arguments to the Character's constructor
            // on all commas except for those in strings
            String line = scanner.nextLine();
            String[] tokenizedLine = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

            // add the character argued from a line in the charactersFile
            this.getModel().addCharacter(tokenizedLine);

        }
    }

    private void loadMeleeWeapons(File meleeWeaponsFile) throws FileNotFoundException {

        if (meleeWeaponsFile == null) {
            throw new IllegalArgumentException("No game content file may be null");
        }

        Scanner scanner = new Scanner(meleeWeaponsFile);

        // read the file and tokenize each line of arguments
        while (scanner.hasNextLine()) {

            // iterate over all lines and split the arguments to the Weapon's constructor
            // on all commas except for those in strings
            String line = scanner.nextLine();
            String[] tokenizedLine = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

            // add the character argued from a line in the charactersFile
            this.getModel().addMeleeWeapon(tokenizedLine);

        }

    }

    private void loadRangedWeapons(File rangedWeaponsFile) throws FileNotFoundException {

        if (rangedWeaponsFile == null) {
            throw new IllegalArgumentException("No game content file may be null");
        }

        Scanner scanner = new Scanner(rangedWeaponsFile);

        // read the file and tokenize each line of arguments
        while (scanner.hasNextLine()) {

            // iterate over all lines and split the arguments to the Weapon's constructor
            // on all commas except for those in strings
            String line = scanner.nextLine();
            String[] tokenizedLine = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

            // add the character argued from a line in the charactersFile
            this.getModel().addRangedWeapon(tokenizedLine);

        }

    }


    public void run() {

        // Should spin up the welcome text from the view, and the



    }


    public IThunderdome getModel() {
        return this.thunderdomeInstance;
    }


}
