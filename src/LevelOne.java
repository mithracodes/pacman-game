import java.io.*;
import bagel.*;
import bagel.util.*;

public class LevelOne implements Mode_Updateable {

    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private int gameState;
    private final static int LEVEL1_LOAD = 4;
    private final static int LEVEL1_START = 5;
    private final static int LEVEL1_END = 6;
    private boolean isPlaying;
    private boolean isGameOver;
    private boolean isWon;
    private final static String csvFile1 = "res/level1.csv";
    private final static int WALL_ARRAY_SIZE = 150;
    private final static int DOT_ARRAY_SIZE = 103;
    private final static int CHERRY_ARRAY_SIZE = 3;
    private final static int GHOST_ARRAY_SIZE = 4;
    private final static Wall[] walls = new Wall[WALL_ARRAY_SIZE];
    private final static Dot[] dots = new Dot[DOT_ARRAY_SIZE];
    private final static Cherry[] cherries = new Cherry[CHERRY_ARRAY_SIZE];
    private final static Enemies[] ghosts = new Enemies[GHOST_ARRAY_SIZE];
    private final Font TFONT64 = new Font("res/FSO8BITR.TTF", 64);
    private final static int MSG_SPACER = 32;
    private final static String SPACESTART = "PRESS SPACE TO START";
    private final static String ARROWMOVE = "USE ARROW KEYS TO MOVE";
    private final static String WON = "WELL DONE!";
    private final static String LOSE = "GAME OVER!";
    private final Font TFONT40 = new Font("res/FSO8BITR.TTF", 40);
    private final static Point LEVEL1_MSG_POINT = new Point(200, 350);
    private final static String EATPELLET = "EAT THE PELLET TO ATTACK";

    private Player pacQueen;
    private GhostRed ghostRed;
    private GhostBlue ghostBlue;
    private GhostGreen ghostGreen;
    private GhostPink ghostPink;
    private Pellet pellet;
    private final static int FRENZY_FRAMES = 1000;
    private boolean isFrenzyMode;
    private int frenzyFrames = FRENZY_FRAMES;

    public LevelOne() {
        this.setGameState(LEVEL1_LOAD);
        this.isPlaying = false;
        this.isGameOver = false;
        this.isWon = false;
    }
   
    public void setGameState(int gState) {
        gameState = gState;
    }

    public int getGameState() {
        return gameState;
    }

    /**
     * gameState = 4 :
     * Method to display Level1 instruction messages
     */
    private void loadLevelOne() {
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        TFONT40.drawString(SPACESTART, LEVEL1_MSG_POINT.x, LEVEL1_MSG_POINT.y);
        TFONT40.drawString(ARROWMOVE, LEVEL1_MSG_POINT.x - MSG_SPACER, LEVEL1_MSG_POINT.y + 1.3 * MSG_SPACER);
        TFONT40.drawString(EATPELLET, LEVEL1_MSG_POINT.x - 1.5 * MSG_SPACER, LEVEL1_MSG_POINT.y + 2.6 * MSG_SPACER);
    }

    /**
     * Method to display all entities loaded from the csv file
     * 
     * @param csvFile
     */
    public void readCSV(String csvFile) {
        String entType, entImage;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int currentWallCount = 0;
            int currentDotCount = 0;
            int currentCherryCount = 0;
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
                    case "Pellet":
                        entType = "Pellet";
                        entImage = "pellet";
                        pellet = new Pellet(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]),
                                entType, entImage);
                        break;
                    case "Cherry":
                        entType = "Cherry";
                        entImage = "cherry";
                        cherries[currentCherryCount] = new Cherry(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]), entType, entImage);
                        currentCherryCount++;
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
                    case "GhostRed":
                        entType = "GhostRed";
                        entImage = "ghostRed";
                        ghostRed = new GhostRed(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]),
                                entType, entImage);
                        ghosts[currentGhostCount] = new Enemies(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]), entType, entImage);
                        currentGhostCount++;
                        break;
                    case "GhostBlue":
                        entType = "GhostBlue";
                        entImage = "ghostBlue";
                        ghostBlue = new GhostBlue(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]),
                                entType, entImage);
                        ghosts[currentGhostCount] = new Enemies(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]), entType, entImage);
                        currentGhostCount++;
                        break;
                    case "GhostGreen":
                        entType = "GhostGreen";
                        entImage = "ghostGreen";
                        ghostGreen = new GhostGreen(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]),
                                entType, entImage);
                        ghosts[currentGhostCount] = new Enemies(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]), entType, entImage);
                        currentGhostCount++;
                        break;
                    case "GhostPink":
                        entType = "GhostPink";
                        entImage = "ghostPink";
                        ghostPink = new GhostPink(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]),
                                entType, entImage);
                        ghosts[currentGhostCount] = new Enemies(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]), entType, entImage);
                        currentGhostCount++;
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Method to display End of game messages- Game Over! or Well done!
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
    public void update(Input input) {
        if (!isPlaying) {

            if (this.getGameState() == LEVEL1_LOAD) {
                loadLevelOne();
                readCSV(csvFile1);
                if (input.wasPressed(Keys.SPACE)) {
                    setGameState(LEVEL1_START);
                    isPlaying = true;
                    isFrenzyMode = false;
                }
            } else if (isWon) {
                dispEndMsg(WON); // Well Done!
            } else if (isGameOver) {
                dispEndMsg(LOSE); // Game Over!
            }
        } else {
            for (Dot element : dots) {
                element.update();
            }
            for (Cherry element : cherries) {
                element.update();
            }
            for (Wall element : walls) {
                element.update();
            }
            pellet.update();

            if (!isFrenzyMode) { // normal mode
                this.updateNormal(input);
            } else {     // frenzy mode
                this.updateFrenzy(input);
                frenzyFrames--;
                if (frenzyFrames <= 0)
                    turnFrenzyOff();
            }

            if (pacQueen.isLevel1_maxScore()) { // Win detection
                setGameState(LEVEL1_END); // End of Level1
                isWon = true;
                isPlaying = false;
            }
            if (pacQueen.isZeroLives()) { // Lose detection
                setGameState(LEVEL1_END); // End of Level1
                isGameOver = true;
                isPlaying = false;
            }
        }
    }

    /**
     * Method that check for collisions of the player with stationary entities
     * subsequently implements player actions depending on the collided entity
     */
    public void playerStationaryCollisions(Player pacQueen) {
        try {
            Rectangle pacBox = new Rectangle(pacQueen.getPacPosition().x, pacQueen.getPacPosition().y,
                    pacQueen.getEntImage().getWidth(), pacQueen.getEntImage().getHeight());

            Rectangle pelletBox = new Rectangle(pellet.getBox());
            if (pellet.isVisible() && pacBox.intersects(pelletBox)) {
                pellet.setVisible(false);
                turnFrenzyOn();
                return;
            }
            for (Dot element : dots) {
                Rectangle dotBox = element.getBox();
                if (element.isVisible() && pacBox.intersects(dotBox)) {
                    pacQueen.scoreAdd(Dot.DOT_POINTS);
                    element.setVisible(false);
                }
            }
            for (Cherry element : cherries) {
                Rectangle cherryBox = element.getBox();
                if (element.isVisible() && pacBox.intersects(cherryBox)) {
                    pacQueen.scoreAdd(Cherry.CHERRY_POINTS);
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

    /**
     * Method to check Player's collision with ghosts
     * implements the after collision effect during normal or frenzy mode
     */
    public void playerGhostsCollisions(Player pacQueen, int mode) {
        Rectangle pacBox = new Rectangle(pacQueen.getPacPosition().x, pacQueen.getPacPosition().y,
                pacQueen.getEntImage().getWidth(), pacQueen.getEntImage().getHeight());
        Rectangle ghostRedBox = new Rectangle(ghostRed.getGhostRedPos().x, ghostRed.getGhostRedPos().y,
                ghostRed.getEntImage().getWidth(), ghostRed.getEntImage().getHeight());
        Rectangle ghostBlueBox = new Rectangle(ghostBlue.getGhostBluePos().x, ghostBlue.getGhostBluePos().y,
                ghostBlue.getEntImage().getWidth(), ghostBlue.getEntImage().getHeight());
        Rectangle ghostGreenBox = new Rectangle(ghostGreen.getGhostGreenPos().x, ghostGreen.getGhostGreenPos().y,
                ghostGreen.getEntImage().getWidth(), ghostGreen.getEntImage().getHeight());
        Rectangle ghostPinkBox = new Rectangle(ghostPink.getGhostPinkPos().x, ghostPink.getGhostPinkPos().y,
                ghostPink.getEntImage().getWidth(), ghostPink.getEntImage().getHeight());

        // Normal mode - Check player's Collisions with ghosts
        if (!isFrenzyMode) {
            if (pacBox.intersects(ghostRedBox)) {
                pacQueen.decreaseLives();
                pacQueen.resetPosition();
                ghostRed.resetPosition();
            } else if (pacBox.intersects(ghostBlueBox)) {
                pacQueen.decreaseLives();
                pacQueen.resetPosition();
                ghostBlue.resetPosition();
            } else if (pacBox.intersects(ghostGreenBox)) {
                pacQueen.decreaseLives();
                pacQueen.resetPosition();
                ghostGreen.resetPosition();
            } else if (pacBox.intersects(ghostPinkBox)) {
                pacQueen.decreaseLives();
                pacQueen.resetPosition();
                ghostPink.resetPosition();
            }
        } else if (isFrenzyMode) {
            // Frenzy mode - Check player's Collisions with ghosts
            if (ghostRed.isVisible() && pacBox.intersects(ghostRedBox)) {
                pacQueen.scoreAdd(Enemies.GHOST_POINTS);
                ghostRed.setVisible(false);
            } else
            if (ghostBlue.isVisible() && pacBox.intersects(ghostBlueBox)) {
                pacQueen.scoreAdd(Enemies.GHOST_POINTS);
                ghostBlue.setVisible(false);
            } else
            if (ghostGreen.isVisible() && pacBox.intersects(ghostGreenBox)) {
                pacQueen.scoreAdd(Enemies.GHOST_POINTS);
                ghostGreen.setVisible(false);
            }else
            if (ghostPink.isVisible() && pacBox.intersects(ghostPinkBox)) {
                pacQueen.scoreAdd(Enemies.GHOST_POINTS);
                ghostPink.setVisible(false);
            }
        }
    }

    /**
     * Method to prevent red ghost-wall collision
     * 
     * @param mode
     */
    public void ghostRedWallsCollisions(int mode) {
        Rectangle ghostRedBox = new Rectangle(ghostRed.getGhostRedPos().x, ghostRed.getGhostRedPos().y,
                ghostRed.getEntImage().getWidth(), ghostRed.getEntImage().getHeight());
        for (Wall elemWall : walls) {
            Rectangle wallBox = elemWall.getBox();
            if (ghostRedBox.intersects(wallBox)) {
                ghostRed.reverse(mode);
            }
        }
    }

    /**
     * Method to prevent blue ghost-wall collision
     * 
     * @param mode
     */
    public void ghostBlueWallsCollisions(int mode) {
        Rectangle ghostBlueBox = new Rectangle(ghostBlue.getGhostBluePos().x, ghostBlue.getGhostBluePos().y,
                ghostBlue.getEntImage().getWidth(), ghostBlue.getEntImage().getHeight());
        for (Wall elemWall : walls) {
            Rectangle wallBox = elemWall.getBox();
            if (ghostBlueBox.intersects(wallBox)) {
                ghostBlue.reverse(mode);
            }
        }
    }

    /**
     * Method to prevent green ghost-wall collision
     * 
     * @param mode
     */
    public void ghostGreenWallsCollisions(int mode) {
        Rectangle ghostGreenBox = new Rectangle(ghostGreen.getGhostGreenPos().x, ghostGreen.getGhostGreenPos().y,
                ghostGreen.getEntImage().getWidth(), ghostGreen.getEntImage().getHeight());
        for (Wall elemWall : walls) {
            Rectangle wallBox = elemWall.getBox();
            if (ghostGreenBox.intersects(wallBox)) {
                ghostGreen.reverse(mode);
            }
        }
    }

    /**
     * Method to prevent pink ghost-wall collision
     * 
     * @param mode
     */
    public void ghostPinkWallsCollisions(int mode) {
        Rectangle ghostPinkBox = new Rectangle(ghostPink.getGhostPinkPos().x, ghostPink.getGhostPinkPos().y,
                ghostPink.getEntImage().getWidth(), ghostPink.getEntImage().getHeight());
        for (Wall elemWall : walls) {
            Rectangle wallBox = elemWall.getBox();
            if (ghostPinkBox.intersects(wallBox)) {
                ghostPink.reverseRandom(mode);
            }
        }
    }

    /**
     * Method that updates the player and ghosts behaviour during normal mode
     * 
     * @param input
     */
    public void updateNormal(Input input) {
        pacQueen.updateNormal(input, this);
        ghostRed.updateNormal(this);
        ghostBlue.updateNormal(this);
        ghostGreen.updateNormal(this);
        ghostPink.updateNormal(this);
    }

    /**
     * Method that updates the player and ghosts behaviour during frenzy mode
     * 
     * @param input
     */
    public void updateFrenzy(Input input) {
        pacQueen.updateFrenzy(input, this);
        ghostRed.updateFrenzy(this);
        ghostBlue.updateFrenzy(this);
        ghostGreen.updateFrenzy(this);
        ghostPink.updateFrenzy(this);
    }

    /**
     * Method that switches the level mode from normal to frenzy
     */
    public void turnFrenzyOn() {
        isFrenzyMode = true;
    }

    /**
     * Method that reverts the level mode from frenzy to normal
     * updates the transition state
     */
    public void turnFrenzyOff() {
        isFrenzyMode = false;
        // collided invisible ghosts re-appear at start position
        for (Enemies element : ghosts) {
            if (!element.isVisible()) {
                element.setVisible(true);
                element.resetPosition();
            } else { // non-collided ghosts continue from their previous position
                switch (element.getEntType()) {
                    case "ghostRed":
                        ghostRed.movetoPrevPos();
                        break;
                    case "ghostBlue":
                        ghostBlue.movetoPrevPos();
                        break;
                    case "ghostGreen":
                        ghostGreen.movetoPrevPos();
                        break;
                    case "ghostPink":
                        ghostPink.movetoPrevPos();
                        break;
                }
            }
        }
    }

    public void updateNormal(LevelOne levelOneNormal) {
    }

    public void updateFrenzy(LevelOne levelOneFrenzy) {
    }
}