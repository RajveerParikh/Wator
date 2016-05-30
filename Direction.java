package wator;

/**
 * This is a class with exactly four instances: LEFT, RIGHT, UP, and DOWN.
 * Each instance has accessible values dx and dy, defining the direction
 * of the move.
 * 
 * @author David Matuszek
 * @author Rajveer Parikh
 */
public enum Direction {       
    LEFT(-1, 0), RIGHT(1, 0), UP(0, -1), DOWN(0, 1);
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }        
    int dx; 
    int dy;
}
