/**
 * 
 */
package wator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * @author David Matuszek
 * @author Rajveer Parikh
 */
public class Display extends JPanel implements Observer {
    private static final long serialVersionUID = 1L;
    private Ocean ocean;
    private static Color oceanColor = new Color(128, 255, 255);
    private Display thisDisplay;
    
    /**
     * CAUTION: This constructor creates an Ocean in an incomplete (and
     * therefore invalid) state. The method setOcean must be called
     * to complete construction!
     */
    public Display() {
        thisDisplay = this;
    }
    
    /**
     * Tells the Display what Ocean to display.
     * @param ocean The ocean.
     */
    public void setOcean(Ocean ocean) {
        this.ocean = ocean;
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        int width = this.getWidth();
        int height = this.getHeight();
        int nRows = ocean.getNRows();
        int nColumns = ocean.getNColumns();
        Denizen water = Water.getInstance();
        g.setColor(oceanColor);
        g.fillRect(0, 0, width, height);
        double cellWidth = (double)width / nColumns;
        double cellHeight = (double) height / nRows;
        for (int i = 0; i < nRows; i += 1) {
            int top = (int)(i * cellHeight);
            for (int j = 0; j < nColumns; j += 1) {
                int left = (int)(j * cellWidth);
                Denizen denizen = ocean.get(i, j);
                if (denizen != water) {
                    if (denizen instanceof Shark) {
                        g.setColor(Color.red);
                    } else if (denizen instanceof Fish) {
                        g.setColor(Color.blue);
                    }
                    g.fillOval(left, top, (int)cellWidth, (int)cellHeight);
                }
            }
        }
    }

    /**
     * 
     * @param o The Ocean. Unused.
     * @param arg Possible additional data. Unused.
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        thisDisplay.repaint();
    }

}
