import bagel.*;

/**
 * Code for SWEN20003 Project 2, Semester 1, 2023
 * Project2B
 * @Mahamithra_Sivagnanam
 */

 
public class ShadowPac extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

     /**
     * GameStates
     * 1. Level0 - loadNewGame (LEVEL0_LOAD)
     * 2. Level0 - start (LEVEL0_START)
     * 3. level0 - end (LEVEL0_END)
     * 4. Level1 - loadNewLevel (LEVEL1_LOAD)
     * 5. Level1 - Start (LEVEL1_START)
     * 6. level1 - end (LEVEL1_END)
     */
    private LevelZero gameObj = new LevelZero();
    private LevelOne LevelOneObj = new LevelOne();
    private final static int LEVEL1_LOAD = 4;
    
    public ShadowPac() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * Main - entry point for the program
     * 
     * @param args
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Method to perform game state updates
     * Allows the game to exit when the escape key is pressed
     * Creates new GamePlay Object and calls its update method
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        gameObj.update(input); // Level0
        if (gameObj.getGameState() == LEVEL1_LOAD) {
            LevelOneObj.update(input); //Level1
        }
    }
}
