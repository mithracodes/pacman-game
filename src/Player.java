import bagel.*;
import bagel.util.Point;
import bagel.Font;
import bagel.util.Rectangle;

public class Player extends GameEntities implements Movable, Mode_Updateable {

    private final static Image PAC = new Image("res/pac.png");
    private final static Image PACOPEN = new Image("res/pacOpen.png");
    private final static Image HEART = new Image("res/heart.png");
    private final static Point HEART_POINT = new Point(900, 10);
    private final static String SCORE = "SCORE";
    private final static int SCORE_SPACER = 10;
    private final static Point SCORE_BL_POINT = new Point(25, 25);
    private final Font TFONT20 = new Font("res/FSO8BITR.TTF", 20);
    private Image pacImage;
    private final static int MAX_LIVES = 3;
    private final static int SPEED_NORMAL = 3;
    private int pacLives;
    private int score;
    private Point startPosition;
    private Point pacPosition;
    private Point prevPosition;
    private int frameCount;
    private final static int IMGSWAP_FRAMES = 15;
    private DrawOptions rotate = new DrawOptions();
    private final static int LEVEL1_MAXSCORE = 800;
    private final static int SPEED_FRENZY = 4;

    public Player(int posX, int posY, String entType, String entImage) {
        super(posX, posY, entType, entImage);
        this.pacImage = PAC;
        this.startPosition = new Point(posX, posY);
        this.pacPosition = startPosition;
        this.pacLives = MAX_LIVES;
        this.score = 0;
    }

    public Point getPacPosition() {
        return pacPosition;
    }

    public void setPacPosition(int playerX, int playerY) {
        pacPosition = new Point(playerX, playerY);
    }
   
    public void setplayerImage(Image pacImage) {
        this.pacImage = pacImage;
    }

    public Image getPlayerImage() {
        return pacImage;
    }

    /**
     * Method to draw the player's lives (heart)
     */
    public void drawLives() {
        int spacer = 30;
        for (int life = 0; life < this.pacLives; life++) {
            HEART.drawFromTopLeft(HEART_POINT.x + (life * spacer), HEART_POINT.y);
        }
    }

    /**
     * Method to draw the player's score
     */
    public void drawScores() {
        TFONT20.drawString(SCORE, SCORE_BL_POINT.x, SCORE_BL_POINT.y);
        TFONT20.drawString(String.valueOf(this.score), SCORE_BL_POINT.x + (TFONT20.getWidth(SCORE) + SCORE_SPACER),
                SCORE_BL_POINT.y);
    }

    /**
     * Method to reduce the lives upon ghost collision
     */
    public void decreaseLives() {
        pacLives--;
    }

    /**
     * This method increments the player's score
     * 
     * @param points
     */
    public void scoreAdd(int points) {
        score = score + points;
    }

    /**
     * Methods for rendering swap of player images(every 15 frames)
     */
    public void changePlayerImage() {
        if (this.getPlayerImage().equals(PAC)) {
            this.setplayerImage(PACOPEN);
        } else {
            this.setplayerImage(PAC);
        }
    }

    /**
     * This method helps to move the player
     * 
     * @param pacNewX
     * @param pacNewY
     */
    @Override
    public void move(double newX, double newY, double speed) {
        prevPosition = pacPosition;
        pacPosition = new Point(pacPosition.x + newX, pacPosition.y + newY);
    }

    /**
     * Function that returns the bounding rectangle box around the player
     */
    public Rectangle getBox() {
        return new Rectangle(getPacPosition(), getEntImage().getWidth(), getEntImage().getHeight());
    }

    /**
     * Method to reset the player's position(,image and rotation) to it's starting
     * position
     */
    public void resetPosition() {
        pacPosition = startPosition;
        setplayerImage(PAC);
        rotate.setRotation(0);
    }

    /**
     * Method that prevents player from  wall collision
     */
    public void bounceBack() {
        pacPosition = prevPosition;
    }

    /**
     * Method that checks if the player has lost all lives
     */
    public boolean isZeroLives() {
        return pacLives == 0;
    }

    /**
     * Method that checks if the player has reached the level zero max score
     */
    public boolean isLevel0_maxScore(int dotcount) {
        return score == dotcount * Dot.DOT_POINTS;
    }

    /**
     * Method that checks if the player has reached the level One max score
     */
    public boolean isLevel1_maxScore() {
        return score == LEVEL1_MAXSCORE;
    }

    public void playerAnimateMove(Input input, int speed) {
        frameCount++;
        if (input.isDown(Keys.UP)) {
            move(0, -speed, speed);
            rotate.setRotation(-Math.PI / 2);
        } else if (input.isDown(Keys.DOWN)) {
            move(0, speed, speed);
            rotate.setRotation(Math.PI / 2);
        } else if (input.isDown(Keys.LEFT)) {
            move(-speed, 0, speed);
            rotate.setRotation(Math.PI);
        } else if (input.isDown(Keys.RIGHT)) {
            move(speed, 0, speed);
            rotate.setRotation(0);
        }
        if (frameCount == IMGSWAP_FRAMES) {
            changePlayerImage();
            frameCount = 0;
        }
    }
    
    /**
     * This method updates the player's behaviour during Level0
     * 
     * @param input
     * @param gamePlayObj
     */
    public void update(Input input, LevelZero gamePlayObj) {
        int speed = SPEED_NORMAL;
        playerAnimateMove(input, speed);
        pacImage.drawFromTopLeft(pacPosition.x, pacPosition.y, rotate);
        gamePlayObj.checkCollisions(this);
        drawLives();
        drawScores();
    }

    /**
     * Method updates the player's state during Level1 Normal mode
     * 
     * @param input
     * @param levelOneNormal
     */
    public void updateNormal(Input input, LevelOne levelOneNormal) {
        int speed = SPEED_NORMAL;
        playerAnimateMove(input, speed);
        pacImage.drawFromTopLeft(pacPosition.x, pacPosition.y, rotate);
        levelOneNormal.playerStationaryCollisions(this);
        levelOneNormal.playerGhostsCollisions(this, NORMAL_MODE);
        drawLives();
        drawScores();
    }

    /**
     * Method updates the player's state during Level1 Normal mode
     * 
     * @param input
     * @param levelOneFrenzy
     */
    public void updateFrenzy(Input input, LevelOne levelOneFrenzy) {
        int speed = SPEED_FRENZY;
        playerAnimateMove(input, speed);
        pacImage.drawFromTopLeft(pacPosition.x, pacPosition.y, rotate);
        levelOneFrenzy.playerStationaryCollisions(this);
        levelOneFrenzy.playerGhostsCollisions(this, FRENZY_MODE);
        drawLives();
        drawScores();
    }

    public void updateNormal(LevelOne levelOneNormal) {
    }

    public void updateFrenzy(LevelOne levelOneFrenzy) {
    }
}