/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package nqueen_ga;


import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Lenovo
 */
public class NQueen_GA {
    private JFrame frame;
    private JPanel chessboardPanel, controlPanel, statisticsPanel;
    private JLabel GenerationLabel, mutationLabel, popsizeLabel,timeLabel, Nlabel;
    private JButton generateButton;
    private GA GASolver;
    private int n, Popsize, generations ;
    private double mutation;
    private int N = 6;

    public NQueen_GA() {
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
         generateButton = new JButton("Solve With GA");
    // Generate a solution using GA ==================================================================================      
     generateButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
       try {
             n = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the number of queens:"));
            if (n < 5) {
                JOptionPane.showMessageDialog(frame, "Please enter a number greater than or equal to 5");
                return;
            }
            Popsize = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the population size:"));
            generations = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the number of generations:"));
            mutation = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter the mutation rate:"));
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
            GASolver = new GA(N, Popsize , generations , mutation);
            java.util.List<int[]> solutions = GASolver.solve();
            long endTime = System.currentTimeMillis();
            double duration = (endTime - startTime) / 1000.0;

            // Update the statistics

            Nlabel.setText(String.valueOf(n));
            popsizeLabel.setText(String.valueOf(Popsize));
            GenerationLabel.setText(String.valueOf(generations));
            mutationLabel.setText(String.valueOf(mutation)); 
            timeLabel.setText(String.format("%.3f", duration));
            
            // ImageIcon queenIcon = new ImageIcon("Queen.png");
            // Display the solution on the chessboard panel
            int[] solution = solutions.get(0);
            if (GASolver.is_valid(solution )) {
               // int[] solution = solutions.get(0);
                for (int row = 0; row < N; row++) {
                    for (int col = 0; col < N; col++) {
                        JButton button = (JButton) chessboardPanel.getComponent(row * N + col);
                        if (solution[row] == col) {
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
            JOptionPane.showMessageDialog(frame, "OOOPS AN ERROR ACCURS !");
        }
    }
});        
        controlPanel.add(generateButton); 
        // Create the statistics panel
        statisticsPanel = new JPanel(new GridLayout(5, 2));
        statisticsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        statisticsPanel.add(new JLabel("Number of queens :"));
        Nlabel = new JLabel("0");
        statisticsPanel.add(Nlabel);
        statisticsPanel.add(new JLabel("Population size :"));
        popsizeLabel = new JLabel("0");
        statisticsPanel.add(popsizeLabel);
        statisticsPanel.add(new JLabel("Number of generations :"));
        GenerationLabel = new JLabel("0");
        statisticsPanel.add(GenerationLabel);
        statisticsPanel.add(new JLabel("Mutation rate:"));
        mutationLabel = new JLabel("0");
        statisticsPanel.add(mutationLabel);
       
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
        NQueen_GA gui = new NQueen_GA();
    }
}
