import java.io.*;
import bagel.*;
import bagel.util.*;
import bagel.util.Rectangle;

public class LevelZero {

    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private int gameState;
    private final static int LEVEL0_LOAD = 1;
    private final static int LEVEL0_START = 2;
    private final static int LEVEL0_END = 3;
    private final static int LEVEL1_LOAD = 4;
    private boolean isPlaying;
    private boolean isGameOver;
    private final static String csvFile0 = "res/level0.csv";
    private final static int WALL_ARRAY_SIZE = 145;
    private final static int DOT_ARRAY_SIZE = 121;
    private final static int GHOST_ARRAY_SIZE = 4;
    private final static Wall[] walls = new Wall[WALL_ARRAY_SIZE];
    private final static Dot[] dots = new Dot[DOT_ARRAY_SIZE];
    private final static GhostRed[] ghosts = new GhostRed[GHOST_ARRAY_SIZE];
    private final Font TFONT64 = new Font("res/FSO8BITR.TTF", 64);
    private final Font TFONT24 = new Font("res/FSO8BITR.TTF", 24);
    private final static Point TITLE_BL_POINT = new Point(260, 250);
    private final static int MSGADJx = 60;
    private final static int MSGADJy = 190;
    private final static Point LEVEL0_MSGL1_POINT = new Point(TITLE_BL_POINT.x + MSGADJx, TITLE_BL_POINT.y + MSGADJy);
    private final static int MSG_SPACER = 32;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final static String SPACESTART = "PRESS SPACE TO START";
    private final static String ARROWMOVE = "USE ARROW KEYS TO MOVE";
    private final static String LEVELPASS = "LEVEL COMPLETE!";
    private final static String LOSE = "GAME OVER!";
    private final static int DELAYFRAMES = 300;
    private int counter = DELAYFRAMES;

    private Player pacQueen;

    public LevelZero() {
        this.setGameState(LEVEL0_LOAD);
    }

    public void setGameState(int gState) {
        gameState = gState;
    }

    public int getGameState() {
        return gameState;
    }

    /**
     * gameState = 1 : Level0 - loadNewGame
     * Setup new game variables and display title screen
     */
    private void loadNewGame() {
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        TFONT64.drawString(GAME_TITLE, TITLE_BL_POINT.x, TITLE_BL_POINT.y);
        TFONT24.drawString(SPACESTART, LEVEL0_MSGL1_POINT.x, LEVEL0_MSGL1_POINT.y);
        TFONT24.drawString(ARROWMOVE, LEVEL0_MSGL1_POINT.x - MSG_SPACER, LEVEL0_MSGL1_POINT.y + MSG_SPACER);
    }

    /**
     * Method to load and read the csv file; creates objects of game entities
     * 
     * @param csvFile
     */
    // Adopted and modified from Project 1 Sample solution provided by the teaching team

    public void readCSV(String csvFile) {
        String entType, entImage;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int currentWallCount = 0;
            int currentDotCount = 0;
            int currentGhostCount = 0;
            String line;
            while ((line = br.readLine()) != null) {
                String[] sections = line.split(",");
                switch (sections[0]) {
                    case "Player":
                        entType = "Player";
                        entImage = "pac";
                        pacQueen = new Player(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]),
                                entType, entImage);
                        break;
                    case "Ghost":
                        entType = "Ghost";
                        entImage = "ghostRed";
                        ghosts[currentGhostCount] = new GhostRed(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]), entType, entImage);
                        currentGhostCount++;
                        break;
                    case "Dot":
                        entType = "Dot";
                        entImage = "dot";
                        dots[currentDotCount] = new Dot(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]),
                                entType, entImage);
                        currentDotCount++;
                        break;
                    case "Wall":
                        entType = "Wall";
                        entImage = "wall";
                        walls[currentWallCount] = new Wall(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]),
                                entType, entImage);
                        currentWallCount++;
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Method to display End of game messages, Game Over! or Level Complete!
     * 
     * @param message
     */
    private void dispEndMsg(String message) {
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        double textCenterX = (Window.getWidth() / 2) - (this.TFONT64.getWidth(message) / 2);
        double textCenterY = ((Window.getHeight() / 2) - MSG_SPACER);
        TFONT64.drawString(message, textCenterX, textCenterY);
    }

    /**
     * Update method to draw the various states of the game
     * 
     * @param input
     */
    // Adopted and modified from Project 1 Sample solution provided by the teaching team

    public void update(Input input) {

        if (!isPlaying) {

            if (this.getGameState() == LEVEL0_LOAD) {
                loadNewGame();
                readCSV(csvFile0);
                if (input.wasPressed(Keys.SPACE)) {
                    setGameState(LEVEL0_START);
                    isPlaying = true;
                }
            } else if (isGameOver) {
                dispEndMsg(LOSE); // Game Over!
            }

        } else {

            pacQueen.update(input, this);
            for (Dot element : dots) {
                element.update();
            }
            for (GhostRed element : ghosts) {
                element.update();
            }
            for (Wall element : walls) {
                element.update();
            }
            if (input.wasPressed(Keys.W)) { //remove this
                isPlaying = false;
                setGameState(LEVEL1_LOAD);
            }
            if (pacQueen.isLevel0_maxScore(DOT_ARRAY_SIZE)) {
                setGameState(LEVEL0_END);
                dispEndMsg(LEVELPASS); // Level(0) Complete!
                counter--;
                if (counter == 0) { // display level complete message for 300 frames
                    isPlaying = false;
                    setGameState(LEVEL1_LOAD);// Level1 Load state
                }
            }
            if (pacQueen.isZeroLives()) { // lose detection
                isGameOver = true;
                isPlaying = false;
            }
        }
    }

    /**
     * Method that checks for collisions of the player using intersect method
     * subsequently implements player actions depending on the collided entity
     * 
     * @param pacQueen
     */
    // Adopted and modified from Project 1 Sample solution provided by teaching team

    public void checkCollisions(Player pacQueen) {
        try {
            Rectangle pacBox = new Rectangle(pacQueen.getBox()); // Creates rectangle for player
            for (GhostRed element : ghosts) {
                Rectangle ghostBox = element.getBox();
                if (pacBox.intersects(ghostBox)) {
                    pacQueen.decreaseLives();
                    pacQueen.resetPosition();
                }
            }
            for (Dot element : dots) {
                Rectangle dotBox = element.getBox();
                if (element.isVisible() && pacBox.intersects(dotBox)) {
                    pacQueen.scoreAdd(Dot.DOT_POINTS);
                    element.setVisible(false);
                }
            }
            for (Wall element : walls) {
                Rectangle wallBox = element.getBox();
                if (pacBox.intersects(wallBox)) {
                    pacQueen.bounceBack();
                }
            }
        } catch (IndexOutOfBoundsException outboundErr) {
            System.out.println("Caught an IndexOutOfBoundsException: " + outboundErr.getMessage());
        }
    }
}