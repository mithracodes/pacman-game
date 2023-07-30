import bagel.util.Point;

public class GhostRed extends GameEntities implements Movable, Mode_Updateable {

    private final static int SPEED_NORMAL = 1;
    private final static double SPEED_FRENZY = 0.5;
    private boolean movingRight;
    private Point startPosition;
    private Point ghostRedPos;
    private Point prevPosition;

    public GhostRed(int posX, int posY, String entType, String entImage) {
        super(posX, posY, entType, entImage);
        this.startPosition = new Point(posX, posY);
        this.ghostRedPos = startPosition;
        this.movingRight = true;
    }

    public Point getGhostRedPos() {
        return ghostRedPos;
    }

    /**
     * Method to move the ghost to it's previous position
     */
    public void movetoPrevPos() {
        ghostRedPos = prevPosition;
    }

    /**
     * Method to reset the ghost position to its starting position
     */
    public void resetPosition() {
        ghostRedPos = startPosition;
    }

    /**
     * Method that moves the Ghost Red in horizontal direction only
     * 
     * @param newX , @param newY, @param speed
     */
    @Override
    public void move(double newX, double newY, double speed) {
        prevPosition = ghostRedPos;
        if (movingRight)
            newX += speed;
        else
            newX -= speed;
        ghostRedPos = new Point(newX, newY);
    }

    /**
     * Method to reverse the direction of the ghost
     * 
     * @param mode
     */
    public void reverse(int mode) {
        if (movingRight)
            movingRight = false;
        else
            movingRight = true;
    }

    /**
     * Method that updates the ghost position during normal mode
     * 
     * @param levelOneNormal
     */
    public void updateNormal(LevelOne levelOneNormal) {
        this.setEntImage(getEntType());
        move(ghostRedPos.x, ghostRedPos.y, SPEED_NORMAL);
        getEntImage().drawFromTopLeft(ghostRedPos.x, ghostRedPos.y);
        levelOneNormal.ghostRedWallsCollisions(NORMAL_MODE);
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
            move(ghostRedPos.x, ghostRedPos.y, SPEED_FRENZY);
            getEntImage().drawFromTopLeft(ghostRedPos.x, ghostRedPos.y);
            levelOneFrenzy.ghostRedWallsCollisions(FRENZY_MODE);
        }
    }

}