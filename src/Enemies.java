public class Enemies extends GameEntities implements Movable {
    /**
     * In frenzy mode, every player-ghost collision awards 30 points to the player
     */
    public final static int GHOST_POINTS = 30;
    /*
     * During frenzy mode, all of the ghosts images will be changed to
     * ghostFrenzy.png
     */
    public final static String GHOST_FRENZY = "ghostFrenzy";

    public Enemies(int posX, int posY, String entType, String entImage) {
        super(posX, posY, entType, entImage);
    }

    @Override
    public void move(double newX, double newY, double speed) {
    }
}