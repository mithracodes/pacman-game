import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Parent Class of all the ShadwPac game elements
 */
public class GameEntities {

    private String entityType;
    private Point startPosition;
    private Point position;
    private Image entImage;
    private boolean isVisible;

    public GameEntities(int posX, int posY, String entType, String entImage) {

        this.entityType = entType;
        this.startPosition = new Point(posX, posY);
        this.position = startPosition;
        this.entImage = new Image("res/" + entImage + ".png");
        this.isVisible = true;
    }

    public Point getPosition() {
        return position;
    }

    public String getEntType() {
        return entityType;
    }

    public Image getEntImage() {
        return entImage;
    }

    public void setEntImage(String newImage) {
        entImage = new Image("res/" + newImage + ".png");
    }

    /**
     * Method to place the entity back at its starting position
     */
    public void resetPosition() {
        position = startPosition;
    }

    /**
     * Method that checks the entity's visibility and draws at the position
     */
    public void update() {
        if (isVisible) {
            getEntImage().drawFromTopLeft(getPosition().x, getPosition().y);
        }
    }

    /**
     * Function that returns the bounding box rectange of the entity
     */
    public Rectangle getBox() {
        return new Rectangle(getPosition(), getEntImage().getWidth(), getEntImage().getHeight());
    }

    /**
     * set Method for visibility of the entity
     * 
     * @param appear
     */
    public void setVisible(boolean appear) {
        isVisible = appear;
    }

    /**
     * Function to check if the entity is visible
     * 
     * @return
     */
    public boolean isVisible() {
        return isVisible;
    }
}