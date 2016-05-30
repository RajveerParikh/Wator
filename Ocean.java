package wator;

import java.util.Observable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author David Matuszek
 * @author Rajveer Parikh
 */
public class Ocean extends Observable {
    private Denizen[][] array;
    private int nRows;
    private int nColumns;
    private static Random rand = new Random();
    private int delay;
    private Timer timer;
    public int speedTimer = 1000;

    /**
     * Constructs an Ocean, initially filled only with Water.
     * @param nRows The number of rows in this Ocean.
     * @param nColumns  The number of columns in this Ocean.
     */
    public Ocean(int nRows, int nColumns) {
        array = new Denizen[nRows][nColumns];
        this.nRows = nRows;
        this.nColumns = nColumns;
        fillWithWater();
    }

    /**
     * Fills the array for this Ocean with water.
     */
    public void fillWithWater() {
        Water water = Water.getInstance();
        for (int i = 0; i < nRows; i += 1) {
            for (int j = 0; j < nColumns; j += 1) {
                array[i][j] = water;
            }
        }
    }
    public void speedChange(int speed){ 
    	speedTimer = 1001 - 10 * speed;
    }
    /**
     * @return The array representing the contents of this Ocean.
     */
    Denizen[][] getArray() { return array; }
    
    /**
     * @return The number of rows in the array representing this Ocean.
     */
    public int getNRows() { return nRows; }
    
    /**
     * @return The number of columns in the array representing this Ocean.
     */
    public int getNColumns() { return nColumns; }
    
    /**
     * Returns the contents of the ocean array at the given (row, column)
     * location. The array is treated as a torus--any location outside the
     * array bounds wraps around to the other side.
     * @param row The row to be examined.
     * @param column The column to be examined.
     * @return The contents of the given (row, column), possibly adjusted.
     */
    public Denizen get(int row, int column) {
        int adjRow = (row + nRows) % nRows;
        int adjColumn = (column + nColumns) % nColumns;
        return array[adjRow][adjColumn];
    }
    
    /**
     * Returns the contents of the ocean array adjacent to the given
     * (row, column) in the given Direction. The array is treated as a torus--
     * any location outside the array bounds wraps around to the other side.
     * @param row The row from which the adjacent location is found.
     * @param column The column from which the adjacent location is found.
     * @param direction The Direction of the array location to examine.
     * @return The contents of the adjacent array location.
     */
    public Denizen get(int row, int column, Direction direction) {
        return get(row + direction.dx, column + direction.dy);
    }
    
    /**
     * Puts the given Denizen in the given (row, column) of the array
     * representing this ocean.  The array is treated as a torus--any
     * location outside the array bounds wraps around to the other side.
     * @param row The row in which to put the Denizen.
     * @param column The column in which to put the Denizen.
     * @param denizen The Denizen to be placed.
     */
    public void set(int row, int column, Denizen denizen) {
        int adjRow = (row + nRows) % nRows;
        int adjColumn = (column + nColumns) % nColumns;
        array[adjRow][adjColumn] = denizen;
    }

    /**
     * Puts the given Denizen in an array location adjacent to the
     * given (row, column) of the array representing this ocean, in the 
     * given Direction. The array is treated as a torus--any location
     * outside the array bounds wraps around to the other side.
     * @param row The row from which the adjacent location is found.
     * @param column The column from which the adjacent location is found.
     * @param direction The direction of the adjacent array location.
     * @param denizen The Denizen to be placed.
     */
    public void set(int row, int column,
                    Direction direction, Denizen denizen) {
        set(row + direction.dx, column + direction.dy, denizen);
    }

    /**
     * Put Fish and Sharks in random locations in this Ocean.
     */
    public void populate() {
        // Make certain there is room for everything
        int oceanSize = nRows * nColumns;
        int nSharks = Parameters.numberOfSharks;
        int nFish = Parameters.numberOfFish;
        if (nSharks + nFish > 0.95 * oceanSize) {
            throw new RuntimeException("Ocean is too crowded.");
        }
        // Place the sharks
        for (int i = 0; i < nSharks; i += 1) {
            int[] location = findEmptyLocation();
            array[location[0]][location[1]] = new Shark(location[0], location[1]);
        }
        // Place the fish
        for (int i = 0; i < nFish; i += 1) {
            int[] location = findEmptyLocation();
            array[location[0]][location[1]] = new Fish(location[0], location[1]);
        }
    }

    /**
     *  Find a random empty location and return it as an array {row, column}.
     * @return An int array containing a row and a column.
     */
    private int[] findEmptyLocation() {
        int row;
        int column;
        do {
            row = rand.nextInt(nRows);
            column = rand.nextInt(nColumns);
        } while (array[row][column] != Water.getInstance());
        return new int[] { row, column };
    }
    
    /**
     * Give everything in the Ocean's array a chance to do something.
     */
    public void makeOneStep() {
        for (int i = 0; i < nRows; i += 1) {
            for (int j = 0; j < nColumns; j += 1) {
                Denizen denizen = array[i][j];
                if(denizen.justMoved == false){
                	denizen.makeOneStep(this);	
                }
            }
        }
        for (int i = 0; i < nRows; i += 1) {
            for (int j = 0; j < nColumns; j += 1) {
                Denizen denizen = array[i][j];
                denizen.justMoved = false;
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Controls whether the simulation is running or not.
     * @param running true to run, false to stop running.
     */
    public void setRunning(boolean running) {
        if (running) {
            timer = new Timer(true);
            timer.schedule(new Strobe(), 0, speedTimer); // Making the speed timer variable according to slider
        } else {
            timer.cancel();
        }
    }

    /**
     * Sets the frame rate to be 1000/delay.
     * @param delay The number of milliseconds between frames.
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }
     
    /**
     * Tells the model to advance one "step."
     */
    private class Strobe extends TimerTask {
        @Override
        public void run() {
            makeOneStep();
        }
    }

}
