import bagel.util.Point;

public class GhostPink extends GameEntities implements Movable, Mode_Updateable {

    private final static int SPEED_NORMAL = 3;
    private final static double SPEED_FRENZY = 2.5;
    private int chosenDir;
    private Point startPosition;
    private Point ghostPinkPos;
    private Point prevPosition;

    public GhostPink(int posX, int posY, String entType, String entImage) {
        super(posX, posY, entType, entImage);
        this.startPosition = new Point(posX, posY);
        this.ghostPinkPos = startPosition;
        this.chosenDir = randomChosenDir();
    }

    public Point getGhostPinkPos() {
        return ghostPinkPos;
    }

    /**
     * Method to move the ghost to it's previous position
     */
    public void movetoPrevPos() {
        this.ghostPinkPos = prevPosition;
    }

    /**
     * Method to reset the ghost position to its starting position
     */
    public void resetPosition() {
        ghostPinkPos = startPosition;
    }

    /**
     * Function that returns the chosen Direction value(0,1,2 or 3)
     */
    public int randomChosenDir() {
        // Generate a random number between 0 and 3
        int randomDirection = (int) (Math.random() * 4);
        return randomDirection;
    }

    /**
     * Method that moves the Ghost Pink in the chosen(up, down, left or right)
     * direction
     * 
     * @param newX , @param newY, @param speed
     */
    @Override
    public void move(double newX, double newY, double speed) {
        prevPosition = ghostPinkPos;
        switch (chosenDir) {
            case 0: // is moving up
                newY -= speed;
                ghostPinkPos = new Point(newX, newY);
                break;
            case 1: // is moving down
                newY += speed;
                ghostPinkPos = new Point(newX, newY);
                break;
            case 2: // left
                newX -= speed;
                ghostPinkPos = new Point(newX, newY);
                break;
            case 3: // right
                newX += speed;
                ghostPinkPos = new Point(newX, newY);
                break;
        }
    }

    /**
     * Method to randomly select direction for the ghost upon collision with wall
     * 
     * @param mode
     */
    public void reverseRandom(int mode) {
        ghostPinkPos = prevPosition;
        this.chosenDir = randomChosenDir();
    }

    /**
     * @Override Method that updates the ghost position during normal mode
     * 
     * @param levelOneNormal
     */
    public void updateNormal(LevelOne levelOneNormal) {
        this.setEntImage(getEntType());
        move(ghostPinkPos.x, ghostPinkPos.y, SPEED_NORMAL);
        getEntImage().drawFromTopLeft(ghostPinkPos.x, ghostPinkPos.y);
        levelOneNormal.ghostPinkWallsCollisions(NORMAL_MODE);
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
            move(ghostPinkPos.x, ghostPinkPos.y, SPEED_FRENZY);
            getEntImage().drawFromTopLeft(getGhostPinkPos().x, getGhostPinkPos().y);
            levelOneFrenzy.ghostPinkWallsCollisions(FRENZY_MODE);
        }
    }
}