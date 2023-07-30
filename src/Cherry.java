public class Cherry extends GameEntities {

    // Each cherry adds a score of 20 when eaten by the player
    public final static int CHERRY_POINTS = 20;
    private boolean isVisible;

    public Cherry(int posX, int posY, String entType, String entImage) {
        super(posX, posY, entType, entImage);
        this.isVisible = true;
    }

    /**
     * Update method of Cherry-location and visibility
     */
    public void update() {
        if (isVisible) {
            getEntImage().drawFromTopLeft(getPosition().x, getPosition().y);
        }
    }

    /**
     * set Method for visibility of cherries
     * 
     * @param appear
     */
    public void setVisible(boolean appear) {
        isVisible = appear;
    }

    /**
     * Function to check if the Cherry is visible
     * 
     * @return
     */
    public boolean isVisible() {
        return isVisible;
    }
}