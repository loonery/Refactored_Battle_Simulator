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

        // todo: make output dynamic so that the user can see the fighter's name
        while (this.getModel().fightersRemaining()) {

            // Select a fighter for every battle
            readyFighter();

            // todo: have characters in the model battle
            ArrayList<IAttackLog> battleLog = (ArrayList<IAttackLog>) this.getModel().battle();

            // render each attack that takes place during the battle
            for (IAttackLog attackLog : battleLog) {
                Thread.sleep(2000);
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

    private void readyFighter() {

        // todo: make some distinction between selecting the first challenger, and
        //  asking the user to

        // display the characters available for selection
        this.getView().displayFighterSelectionScreen(this.getModel().getFightersRemaining());
        ICharacter selectedCharacter = this.characterSelection();

        // Display the weapons available to apply to the character
        this.getView().displayWeaponSelectionScreen(this.getModel().getWeaponsRack(), selectedCharacter);
        this.weaponSelection(selectedCharacter);

    }

    /**
     *
     * @param user
     */
    private void weaponSelection(ICharacter user) {

        // Select the weapon and give it to the selected character
        Scanner scanner = new Scanner(System.in);
        int selectedWeapon = scanner.nextInt() - 1;
        this.getModel().armCharacter(selectedWeapon, user);


        // todo: need to apply the weapon to the correct person.

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
     * to its associated Constructor in order to populate the model.
     *
     * @param gameContentFolder the game content folder
     * @throws MissingResourceException when a necessary game content file is missing
     */
    public void loadModel(Path gameContentFolder) throws MissingResourceException {

        // get the directory's contents as a directory stream
        try (DirectoryStream<Path> gameContent = Files.newDirectoryStream(gameContentFolder)) {

            // todo: could factor out the different load methods into one method

            // load the contents of each file which map to loading information into to different portions of the model.
            for (Path gameFile : gameContent) {
                switch (String.valueOf(gameFile.getFileName())) {
                    case "Characters.txt" -> loadCharacters(gameFile.toFile());
                    case "MeleeWeapons.txt" -> loadMeleeWeapons(gameFile.toFile());
                    case "RangedWeapons.txt" -> loadRangedWeapons(gameFile.toFile());
                    default -> {
                        throw new FileNotFoundException("Some critical game content file is missing");
                    }
                }
            }
        } catch (MissingResourceException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Passes arguments from the argued filePath to the model.Character constructor to load
     * Character objects into the Thunderdome.
     *
     * @param charactersFile the File object that describes the arguments to be passed to the model.Character
     *                       constructor.
     * @throws FileNotFoundException when the charactersFile argument cannot be found
     */
    private void loadCharacters(File charactersFile) throws FileNotFoundException {

        // null guard the file object
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

            // Iterate over and clean the tokenized input...
            String[] argumentsArray = new String[tokenizedLine.length];
            for (int i = 0; i < argumentsArray.length; i++)  {

                String argument = tokenizedLine[i].strip();
                argument = argument.replaceAll("\"", "");
                argumentsArray[i] = argument;

            }

            // pass the Character constructor arguments in each line of
            // the CharactersFile.
            this.getModel().addCharacter(argumentsArray);

        }
    }

    /**
     * Passes arguments from the argued filePath to the model.Weapon constructor to load
     * Weapon objects into the Thunderdome.
     *
     * @param meleeWeaponsFile the File object that describes the arguments to be passed to the model.Weapon
     *                         constructor.
     * @throws FileNotFoundException when the meleeWeaponsFile argument cannot be found
     */
    private void loadMeleeWeapons(File meleeWeaponsFile) throws FileNotFoundException {

        // null guard the meleeWeaponsFile
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

            // Iterate over and clean the tokenized input...
            String[] argumentsArray = new String[tokenizedLine.length];
            for (int i = 0; i < argumentsArray.length; i++)  {

                String argument = tokenizedLine[i].strip();
                argument = argument.replaceAll("\"", "");
                argumentsArray[i] = argument;


            }

            // add the character argued from a line in the charactersFile
            this.getModel().addMeleeWeapon(argumentsArray);

        }

    }

    /**
     * Passes arguments from the argued filePath to the model.RangedWeapon constructor to load
     * Weapon objects into the Thunderdome.
     *
     * @param rangedWeaponsFile the File object that describes the arguments to be passed to the model.RangedWeapon
     *                          constructor.
     * @throws FileNotFoundException when the meleeWeaponsFile argument cannot be found
     */
    private void loadRangedWeapons(File rangedWeaponsFile) throws FileNotFoundException {

        // null guard the rangedWeaponsFile
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

            // Iterate over and clean the tokenized input...
            String[] argumentsArray = new String[tokenizedLine.length];
            for (int i = 0; i < argumentsArray.length; i++)  {
                String argument = tokenizedLine[i].strip();
                argument = argument.replaceAll("\"", "");
                argumentsArray[i] = argument;
            }


            // add the character argued from a line in the charactersFile
            this.getModel().addRangedWeapon(argumentsArray);

        }
    }



}
