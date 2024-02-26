/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package nqueen_pso;



import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Lenovo
 */
public class NQueen_PSO {
    private JFrame frame;
    private JPanel chessboardPanel, controlPanel, statisticsPanel;
    private JLabel GenerationLabel, c1Label,c2Lable,iteriaLable, popsizeLabel,timeLabel, Nlabel;
    private JButton generateButton;
    private PSO PSOsolver;
    private int n, Popsize, maxiteration ;
    private double c1,c2,w;
    private int N = 6;

    public NQueen_PSO() {
        // Create the frame and set its properties
    frame = new JFrame("N-Queen Problem Solver");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());

        // Create the chessboard panel 
        chessboardPanel = new JPanel(new GridLayout(N, N));
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(60, 60));
                if ((row + col) % 2 == 0) {
                    button.setBackground(Color.WHITE);
                } else {
                    button.setBackground(Color.GRAY);
                }
                chessboardPanel.add(button);
            }
        }

    // Create the control panel
         controlPanel = new JPanel(new FlowLayout());
         generateButton = new JButton("Solve With PSO");
    // Generate a solution using GA ==================================================================================      
     generateButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
       try {
             n = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the number of queens:"));
            if (n < 5) {
                JOptionPane.showMessageDialog(frame, "Please enter a number greater than or equal to 5");
                return;
            }
            Popsize = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter number of particles in the population:"));
            maxiteration = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the maximum number of iterations:"));
            c1 = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter cognitive parameter:"));
            c2 = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter social parameter:"));
            w = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter inertia weight:"));
            N=n;
            frame.remove(chessboardPanel);
            chessboardPanel = new JPanel(new GridLayout(N, N));
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N; col++) {
                    JButton button = new JButton();
                    button.setPreferredSize(new Dimension(60, 60));
                    if ((row + col) % 2 == 0) {
                        button.setBackground(Color.WHITE);
                    } else {
                        button.setBackground(Color.GRAY);
                    }
                    chessboardPanel.add(button);
                }
            }
            frame.add(chessboardPanel, BorderLayout.CENTER);
            frame.pack();

           
            long startTime = System.currentTimeMillis();
            PSOsolver = new PSO(N, Popsize , maxiteration ,c1,c2, w);
            int[] solutions = PSOsolver.solve();
            long endTime = System.currentTimeMillis();
            double duration = (endTime - startTime) / 1000.0;

            // Update the statistics

            Nlabel.setText(String.valueOf(n));
            popsizeLabel.setText(String.valueOf(Popsize));
            GenerationLabel.setText(String.valueOf(maxiteration));
            c1Label.setText(String.valueOf(c1)); 
            c2Lable.setText(String.valueOf(c2)); 
            iteriaLable.setText(String.valueOf(w)); 
            timeLabel.setText(String.format("%.3f", duration));
            
            //ImageIcon queenIcon = new ImageIcon("Queen.jpg");
            // Display the solution on the chessboard panel
            if (PSOsolver.is_valid(solutions )) {
                System.out.println("it enters");
               
                for (int row = 0; row < N; row++) {
                    for (int col = 0; col < N; col++) {
                        JButton button = (JButton) chessboardPanel.getComponent(row * N + col);
                        if (solutions[row] == col) {
                            //button.setIcon(queenIcon);
                            button.setText("Queen");
                        } else {
                            button.setIcon(null);
                        }
                    }
                }
                JOptionPane.showMessageDialog(frame, "Solution Found!");
            } else {
                JOptionPane.showMessageDialog(frame, "No solution found.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "OOOPS AN ERROR ACCURS! ");
        }
    }
});        
        controlPanel.add(generateButton); 
        // Create the statistics panel
        statisticsPanel = new JPanel(new GridLayout(7, 2));
        statisticsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        statisticsPanel.add(new JLabel("Number of queens :"));
        Nlabel = new JLabel("0");
        statisticsPanel.add(Nlabel);
        statisticsPanel.add(new JLabel("Number of particles in the population :"));
        popsizeLabel = new JLabel("0");
        statisticsPanel.add(popsizeLabel);
        statisticsPanel.add(new JLabel("Maximum number of iterations :"));
        GenerationLabel = new JLabel("0");
        statisticsPanel.add(GenerationLabel);
        statisticsPanel.add(new JLabel("Cognitive parameter :"));
        c1Label = new JLabel("0");
        statisticsPanel.add(c1Label);
        statisticsPanel.add(new JLabel("Social parameter :"));
        c2Lable = new JLabel("0");
        statisticsPanel.add(c2Lable);
        statisticsPanel.add(new JLabel("Inertia weight :"));
        iteriaLable = new JLabel("0");
        statisticsPanel.add(iteriaLable);
        
        statisticsPanel.add(new JLabel("Execution time (S):"));
        timeLabel = new JLabel("0");
        statisticsPanel.add(timeLabel);
       // Add the components to the frame
        frame.add(chessboardPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(statisticsPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        NQueen_PSO gui = new NQueen_PSO();
    }
}
