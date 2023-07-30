import bagel.util.Point;

public class GhostGreen extends GameEntities implements Movable, Mode_Updateable {

    private final static int SPEED_NORMAL = 4;
    private final static double SPEED_FRENZY = 3.5;
    private final static double PROBABILITY = 0.5;
    private Point startPosition;
    private Point ghostGreenPos;
    private Point prevPosition;
    private double random;
    private boolean isHorizontal;
    private boolean isMovingDown;
    private boolean isMovingRight;

    public GhostGreen(int posX, int posY, String entType, String entImage) {
        super(posX, posY, entType, entImage);
        this.startPosition = new Point(posX, posY);
        this.ghostGreenPos = startPosition;
        this.random = Math.random();
        this.isHorizontal = randomChosenDir(random);
    }

    public Point getGhostGreenPos() {
        return ghostGreenPos;
    }

    /**
     * Method to move the ghost to it's previous position
     */
    public void movetoPrevPos() {
        this.ghostGreenPos = prevPosition;
    }

    /**
     * Helper method to reset the ghost position to it's starting position
     */
    public void resetPosition() {
        ghostGreenPos = startPosition;
    }

    /**
     * Method to check if the randomly chosen direction is horizontal or vertical
     * 
     * @param random
     * @return
     */
    public boolean randomChosenDir(double random) {
        if (random < PROBABILITY) {
            isHorizontal = true;
            isMovingRight = true;
        } else {
            isHorizontal = false;
            isMovingDown = true;
        }
        return isHorizontal;
    }

    /**
     * Method that moves the Ghost Green to either of the chosen directions
     * 
     * @param newX , @param newY, @param speed
     */
    @Override
    public void move(double newX, double newY, double speed) {
        prevPosition = ghostGreenPos;
        if (!isHorizontal) {
            if (isMovingDown)
                newY += speed; // is moving down
            else
                newY -= speed; // is moving up
        } else { // moving horizontal
            if (isMovingRight)
                newX += speed; // is moving right
            else
                newX -= speed; // is moving left
        }
        ghostGreenPos = new Point(newX, newY);
    }

    /**
     * Method to reverse the direction of the ghost upon collision with walls
     * 
     * @param mode
     */
    public void reverse(int mode) {
        double speed;
        double newX = prevPosition.x;
        double newY = prevPosition.y;
        if (mode == NORMAL_MODE)
            speed = SPEED_NORMAL;
        else
            speed = SPEED_FRENZY;
        if (!isHorizontal) {
            if (!isMovingDown) { // is moving up
                newY += speed; // then move down
                isMovingDown = true;
            } else { // is moving down
                newY += speed; // then move up
                isMovingDown = false;
            }
        } else {
            if (!isMovingRight) { // is moving left
                newX += speed; // then move right
                isMovingRight = true;
            } else { // is moving right
                newX -= speed; // then move left
                isMovingRight = false;
            }
        }
        ghostGreenPos = new Point(newX, newY);
    }

    /**
     * Method that updates the ghost position during normal mode
     * 
     * @param levelOneNormal
     */
    public void updateNormal(LevelOne levelOneNormal) {
        this.setEntImage(getEntType());
        move(ghostGreenPos.x, ghostGreenPos.y, SPEED_NORMAL);
        getEntImage().drawFromTopLeft(ghostGreenPos.x, ghostGreenPos.y);
        levelOneNormal.ghostGreenWallsCollisions(NORMAL_MODE);
    }

    /**
     * Method that updates the ghost position, appearance and visibility during
     * frenzy mode
     * 
     * @param levelOneFrenzy
     */
    public void updateFrenzy(LevelOne levelOneFrenzy) {
        if (this.isVisible()) {
            this.setEntImage(Enemies.GHOST_FRENZY);
            move(ghostGreenPos.x, ghostGreenPos.y, SPEED_FRENZY);
            getEntImage().drawFromTopLeft(ghostGreenPos.x, ghostGreenPos.y);
            levelOneFrenzy.ghostGreenWallsCollisions(FRENZY_MODE);
        }
    }
}