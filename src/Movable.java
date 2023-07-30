/**
 * Interface implented by entities that can move
 */
public interface Movable {

    /**
     * Method to move the entity
     * @param newX
     * @param newY
     * @param speed
     */
    public void move(double newX, double newY, double speed);
}