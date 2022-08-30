package controller;

import model.IAttackLog;
import model.ICharacter;
import model.IThunderdome;
import view.IThunderdomeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.Scanner;

public class ThunderdomeController implements IThunderdomeController {

    // The gameContent folder is held as a constant
    private final String GAME_CONTENT_PATH = "src/gameContent";
    private IThunderdome thunderdomeInstance;           // This controller's instance of the game
    private IThunderdomeView thunderdomeView;       // This controller's view of the game

    private final static int MILLISECONDS_BETWEEN_ATTACK_RENDERS = 3000;

    /* ##################################################################### */
    /* ############################ Constructor ############################ */
    /* ##################################################################### */

    /**
     * Creates a controller that will be used as an intermediary between the IThunderDomeView view and the
     * Thunderdome model. Uses the statically stored GAME_CONTENT_PATH to load in game content from
     * text files stores in the GAME_CONTENT_PATH.
     *
     * @param thunderdomeInstance the thunderdome instance
     * @param thunderdomeView     the thunderdome view
     * @throws IllegalArgumentException when view or controller are null
     * @throws IOException              when game content file is valid
     */
    public ThunderdomeController(IThunderdome thunderdomeInstance, IThunderdomeView thunderdomeView)
            throws IOException, IllegalArgumentException {

        if (thunderdomeInstance == null || thunderdomeView == null) {
            throw new IllegalArgumentException("Neither the game instance nor the view may be null");
        }

        this.thunderdomeInstance = thunderdomeInstance;
        this.thunderdomeView = thunderdomeView;
        this.loadModel(Path.of((GAME_CONTENT_PATH)));
    }


    /* ################################################################################# */
    /* ############################ Simulation-Loop Methods ############################ */
    /* ################################################################################# */

    /**
     * Kicks off the Thunderdome game instance and renders its information through use of the View.
     */
    public void run() throws InterruptedException {

        this.thunderdomeView.displayWelcomeScreen();

        // select the first fighter that others will be pitted against
        this.readyFighter();

        // while there are fighters remaining
        while (this.getModel().fightersRemaining()) {

            // Select a fighter for every battle
            readyFighter();

            // active fighters battle via the model
            ArrayList<IAttackLog> battleLog = (ArrayList<IAttackLog>) this.getModel().battle();

            // render each attack that takes place during the battle
            this.getView().displayBeginBattle();
            for (IAttackLog attackLog : battleLog) {
                Thread.sleep(MILLISECONDS_BETWEEN_ATTACK_RENDERS);
                this.getView().renderAttackLog(attackLog);
            }

            // remove the fighter who was defeated from the currentFighters
            // array
            this.getView().nextChallenger();
            this.getModel().removeDefeatedFighter();
        }

        // reaching this point in execution means that there is only one character standing.
        this.getView().pronounceVictor(this.getModel().getVictoriousFighter());
    }

    /**
     *
     */
    private void readyFighter() {

        // todo: make some distinction between selecting the first challenger, and
        //  asking the user to replace a defeated challenger

        // display the characters available for selection
        this.getView().displayFighterSelectionScreen(this.getModel().getFightersRemaining());
        ICharacter selectedCharacter = this.characterSelection();

        // Display the weapons available to apply to the character
        this.getView().displayWeaponSelectionScreen(this.getModel().getWeaponsRack(), selectedCharacter);
        this.weaponSelection(selectedCharacter);

    }

    /**
     * Regulates the selection of weapons for ICharacters within the Thunderdome program.
     *
     * @param user the ICharacter for which this program's user is selecting a weapon
     */
    private void weaponSelection(ICharacter user) {
        // Select the weapon and give it to the selected character
        Scanner scanner = new Scanner(System.in);
        int selectedWeapon = scanner.nextInt() - 1;
        this.getModel().armCharacter(selectedWeapon, user);
    }

    /**
     * Regulates the selection of an ICharacter in the Thunderdome Game Loop.
     */
    private ICharacter characterSelection() {

        // todo: guard against out of bounds input, or otherwise incorrect input

        // place the selected fighter into the arena
        Scanner scanner = new Scanner(System.in);
        int selectedCharacter = scanner.nextInt() - 1;
        return this.getModel().placeFighterIntoArena(selectedCharacter);

    }


    /* ##################################################################### */
    /* ############################ Getter Methods ############################ */
    /* ##################################################################### */

    /**
     * Gets the model.
     *
     * @return the model attached to this Controller
     */
    public IThunderdome getModel() {
        return this.thunderdomeInstance;
    }

    /**
     * Gets the view that is regulated by this Controller.
     *
     * @return the view attached to this Controller
     */
    public IThunderdomeView getView() {
        return this.thunderdomeView;
    }

    /* ##################################################################### */
    /* ############################ Model Instantiating Methods ############################ */
    /* ##################################################################### */

    /**
     * Loads in game content files from the GAME_CONTENT_PATH file path and passes data from each file
     * to its associated Constructor (via the model) in order to populate the model.
     *
     * @param gameContentFolder the game content folder
     * @throws MissingResourceException when a necessary game content file is missing
     */
    public void loadModel(Path gameContentFolder) throws RuntimeException, IOException {

        // null guard the file object
        if (gameContentFolder == null) {
            throw new IllegalArgumentException("No game content file may be null");
        }

        // get the directory's contents as a directory stream
        try (DirectoryStream<Path> gameContent = Files.newDirectoryStream(gameContentFolder)) {

            // todo: could factor out the different load methods into one method

            // load the contents of each file which map to loading information into to different portions of the model.
            for (Path gameFile : gameContent) {

                Scanner scanner = new Scanner(gameFile.toFile());

                // read the file and tokenize each line of arguments
                while (scanner.hasNextLine()) {

                    // iterate over all lines and split the arguments to the Character's constructor
                    // on all commas except for those in strings
                    String line = scanner.nextLine();
                    String[] tokenizedLine = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                    // Iterate over and clean the tokenized input...
                    String[] argumentsArray = new String[tokenizedLine.length];
                    for (int i = 0; i < argumentsArray.length; i++) {
                        String argument = tokenizedLine[i].strip();
                        argument = argument.replaceAll("\"", "");
                        argumentsArray[i] = argument;
                    }

                    // call the appropriate model-method dependent upon which gameFile we are trying
                    // to load.
                    switch (String.valueOf(gameFile.getFileName())) {
                        case "Characters.txt" -> this.getModel().addCharacter(argumentsArray);
                        case "MeleeWeapons.txt" -> this.getModel().addMeleeWeapon(argumentsArray);
                        case "RangedWeapons.txt" -> this.getModel().addRangedWeapon(argumentsArray);
                        default -> throw new FileNotFoundException("Some critical game content file is missing");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
