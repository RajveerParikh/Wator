/**
 * 
 */
package wator;

import java.awt.Color;

/**
     * The "empty" spaces in the Ocean--those not containing fish, sharks,
     * or seaweed--still have to have some value. That value could be null,
     * but that would mean that the code would have to test for null in
     * many places. The alternative used here is that the empty spaces
     * will contain "Water," which basically doesn't do anything.
     * <p>
     * Since Water doesn't do anything, we really only need ONE instance of
     * it, and we can use that one instance everywhere. That one instance
     * can be referred to as Water.getInstance().
     * 
     * @author David Matuszek
     * @author Rajveer Parikh
 */
public class Water extends Denizen {
    // The location (0, 0) should never be used.
    private static Water theOneAndOnlyInstanceOfWater = new Water(0, 0);
    private Color waterColor = new Color(128, 255, 255);
    
    /**
     * Constructor for the one and only instance of Water. It's private
     * so the only instance of it is Water.getInstance().
     * @param row The row in which to put the Water.
     * @param column The column in which to put the Water.
     */
    private Water(int row, int column) {
        super(row, column);
    }
    
    /**
     * @return An object of type Water.
     */
    public static Water getInstance() { 
        return theOneAndOnlyInstanceOfWater; 
    }
    
    /* (non-Javadoc)
     * @see wator.Denizen#makeOneStep(wator.Ocean)
     */
    @Override
    public void makeOneStep(Ocean ocean) {
        // Water doesn't do anything.
    }

    /**
     * Water never moves.
     * @see wator.Denizen#canMove(wator.Ocean, wator.Direction)
     */
    @Override
    public boolean canMove(Ocean ocean, Direction direction) {
        return false;
    }  

    /**
     * @param o Unused.
     * @param d Unused.
     * @throws RuntimeException if this method is ever called.
     * @see wator.Denizen#moveAndMaybeGiveBirth(wator.Ocean, wator.Direction)
     */
    @Override
    public void moveAndMaybeGiveBirth(Ocean o, Direction d) {
        throw new RuntimeException("Error: Water cannot move or give birth!");
    }


    /**
     * @throws RuntimeException if this method is ever called
     * @see wator.Denizen#giveBirth(wator.Ocean, int, int)
     */
    @Override
    public Denizen giveBirth(Ocean ocean, int r, int c) {
        throw new RuntimeException("Error: Water cannot give birth!");
    }

    /**
     * @return The color to use for water.
     * @see wator.Denizen#getColor()
     */
    @Override
    public Color getColor() {
        return waterColor;
    }

}
