public class Dot extends GameEntities {

    // Each dot adds a score of 10 when eaten by the player
    public final static int DOT_POINTS = 10;

    public Dot(int posX, int posY, String entType, String entImage) {
        super(posX, posY, entType, entImage);

    }
}