package wator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * "Wator" (a misspelling of "water") is a simple predator-prey simulation.
 * In this simulation, there are sharks and fish. Sharks move, eat fish, and
 * reproduce; they might starve to death. Fish move and reproduce, and they
 * eat algae, so they always have enough to eat. Neither fish nor sharks ever
 *  die of old age.
 *  
 * @author David Matuszek
 * @author Rajveer Parikh
 */
public class Wator extends JFrame {
    private static final long serialVersionUID = 1L; // Vestigal; unused
    private Display display = new Display();
    private JPanel controlPanel = new JPanel();
    private JPanel statisticsPanel = new JPanel();
    private JPanel sharkPanel = new JPanel();
    private JPanel fishPanel = new JPanel();
    private JPanel runPanel = new JPanel();
    private JPanel speedControlPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JSlider speedControl = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
    private JButton runButton = new JButton("Run");
    private JButton populateButton = new JButton("Populate");
    private JTextField sharkCount = new JTextField("100");
    private JTextField fishCount = new JTextField("500");
    private JTextField sharkGestationPeriod = new JTextField("20");
    private JTextField fishGestationPeriod = new JTextField("10");
    private JTextField sharkStarvationPeriod = new JTextField("20");
   //Remove fish starvation button from GUI
   // private JTextField fishStarvationPeriod = new JTextField("40");
    private TitledBorder sharkBorder = new TitledBorder("Sharks");
    private TitledBorder fishBorder = new TitledBorder("Fish");
    
    private Wator thisFrame;
    private final int OCEAN_SIZE = 75;
    private Ocean ocean;
    private boolean running = false;
    private boolean okToMove = false;
    
    /**
     * Set up the GUI.
     */
    public Wator() {
        thisFrame = this;
        
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.SOUTH);
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(statisticsPanel, BorderLayout.NORTH);
        controlPanel.add(runPanel, BorderLayout.SOUTH);
        statisticsPanel.setLayout(new GridLayout(1, 2));
        statisticsPanel.add(sharkPanel);
        statisticsPanel.add(fishPanel);
        runPanel.setLayout(new FlowLayout());
        sharkPanel.setLayout(new GridLayout(6, 1));
        fishPanel.setLayout(new GridLayout(6, 1));
        
        sharkPanel.setBorder(sharkBorder);
        sharkPanel.add(new JLabel("Initial number:"));
        sharkPanel.add(sharkCount);
        sharkPanel.add(new JLabel("Gestation period:"));
        sharkPanel.add(sharkGestationPeriod);
        sharkPanel.add(new JLabel("Starvation period:"));
        sharkPanel.add(sharkStarvationPeriod);
        
        fishPanel.setBorder(fishBorder);
        fishPanel.add(new JLabel("Initial number:"));
        fishPanel.add(fishCount);
        fishPanel.add(new JLabel("Gestation period:"));
        fishPanel.add(fishGestationPeriod);
        //remove fish starvation button from GUI
        //fishPanel.add(new JLabel("Starvation period:"));
        //fishPanel.add(fishStarvationPeriod);
        
        runPanel.setLayout(new BorderLayout());
        runPanel.add(speedControlPanel, BorderLayout.CENTER);
        runPanel.add(buttonPanel, BorderLayout.EAST);
        
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(populateButton);
        buttonPanel.add(runButton);
        
        speedControlPanel.add(speedControl);
        speedControl.setMajorTickSpacing(20);
        speedControl.setMinorTickSpacing(5);
        speedControl.setPaintTicks(true);
        speedControl.setPaintLabels(true);
        
        ocean = new Ocean(OCEAN_SIZE, OCEAN_SIZE);
        display.setOcean(ocean);
        attachListeners();
        ocean.addObserver(display);
        
        pack();
        this.setSize(550, 800);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Run the simulation.
     * @param args Unused.
     */
    public static void main(String[] args) {
        new Wator();
    }

    /**
     * Attach listeners to buttons and the speed control.
     */
    private void attachListeners() {
        populateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populate();
                display.repaint();
            }      
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Make slider functioning only if the program is running and not before
            	if (okToMove == true){
            		okToMove = false;
            	}
            	else {
            		okToMove = true;
            	}
                running = !running;
                runButton.setText(running ? "Stop" : "Run");
                ocean.setRunning(running);
            }      
        });

    speedControl.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
        	JSlider source = (JSlider) e.getSource();
        	if (!source.getValueIsAdjusting()){
            int sliderValue = speedControl.getValue();
            ocean.speedChange(sliderValue);
        	if (okToMove){
	            ocean.setRunning(false);
	            ocean.setRunning(true);
	        	}
        	}
        }    
    });
    }
    /**
     * Clear the ocean and add sharks and fish, according to the values
     * specified in the GUI.
     */
    private void populate() {
        try {
            int count = Integer.valueOf(sharkCount.getText());
            int gestation = Integer.valueOf(sharkGestationPeriod.getText());
            int starvation = Integer.valueOf(sharkStarvationPeriod.getText());
            Parameters.setSharkStatistics(count, gestation, starvation);
            
            count = Integer.valueOf(fishCount.getText());
            gestation = Integer.valueOf(fishGestationPeriod.getText());
            // remove fish starvation calculation as fish cannot starve
            //starvation = Integer.valueOf(fishStarvationPeriod.getText());
            Parameters.setFishStatistics(count, gestation, starvation);
            
            ocean.fillWithWater();
            ocean.populate();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(thisFrame, ex.getMessage());
        }
    }
}
