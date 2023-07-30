/**
 * Interface implemented by entities that behaves differently
 * in two modes - normal and frenzy
 */
public interface Mode_Updateable {
    /*
     * Constant to indicate the normal mode of the level One
     */
    public static final int NORMAL_MODE = 0;

    /*
     * Constant to indicate the frenzy mode of the level One
     */
    public static final int FRENZY_MODE = 1;

    /**
     * Method that updates the entity's behaviour during normal mode
     */
    public void updateNormal(LevelOne levelOneNormal);

    /*
     * Method that updates the entity's behaviour during frenzy mode
     */
    public void updateFrenzy(LevelOne levelOneFrenzy);
}