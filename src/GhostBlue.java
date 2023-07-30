import bagel.util.Point;

public class GhostBlue extends GameEntities implements Movable, Mode_Updateable {

    private final static int SPEED_NORMAL = 2;
    private final static double SPEED_FRENZY = 1.5;
    private boolean movingDown;
    private Point startPosition;
    private Point ghostBluePos;
    private Point prevPosition;

    public GhostBlue(int posX, int posY, String entType, String entImage) {
        super(posX, posY, entType, entImage);
        this.startPosition = new Point(posX, posY);
        this.ghostBluePos = startPosition;
        this.movingDown = true;
    }

    public Point getGhostBluePos() {
        return ghostBluePos;
    }

    /**
     * Method to move the ghost to it's previous position
     */
    public void movetoPrevPos() {
        this.ghostBluePos = prevPosition;
    }

    /**
     * Method to reset the ghost position to its starting position
     */
    public void resetPosition() {
        ghostBluePos = startPosition;
    }

    /**
     * Method that moves the Ghost Blue in vertical direction only
     * 
     * @param newX , @param newY, @param speed
     */
    public void move(double newX, double newY, double speed) {
        prevPosition = ghostBluePos;
        if (movingDown)
            newY += speed;
        else
            newY -= speed;
        ghostBluePos = new Point(newX, newY);
    }

    /*
     * Method to reverse the direction of the ghost
     */
    public void reverse(int mode) {
        if (movingDown)
            movingDown = false;
        else
            movingDown = true;
    }

    /**
     * Method that updates the ghost position during normal mode
     * 
     * @param levelOneNormal
     */
    public void updateNormal(LevelOne levelOneNormal) {
        this.setEntImage(getEntType());
        move(ghostBluePos.x, ghostBluePos.y, SPEED_NORMAL);
        getEntImage().drawFromTopLeft(ghostBluePos.x, ghostBluePos.y);
        levelOneNormal.ghostBlueWallsCollisions(NORMAL_MODE);
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
            move(ghostBluePos.x, ghostBluePos.y, SPEED_FRENZY);
            getEntImage().drawFromTopLeft(ghostBluePos.x, ghostBluePos.y);
            levelOneFrenzy.ghostBlueWallsCollisions(FRENZY_MODE);
        }
    }
}