package nqueen_bfs_dfs_heuristics;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Lenovo
 */
public class NQueen_BFS_DFS_Heuristics {
    private JFrame frame;
    private JPanel chessboardPanel, controlPanel, statisticsPanel;
    private JButton generateButton;
    private JComboBox<String> methodsComboBox;
    private JLabel exploredLabel, generatedLabel, timeLabel, Nlabel;
    private JTable solutionsTable;
    private NqueenBFS bfsSolver;
    private JButton DFSButton;  // add new JButton for selecting DFS method
    private NqueenDFS DFSSolver;
    private JButton heuristic1Button;  // add new JButton for selecting Heuristic1 method
    private Heuristic1 heuristic1Solver;  // create instance of Heuristic1 class
    private JButton heuristic2Button;  // add new JButton for selecting Heuristic1 method
    private Heuristic2 heuristic2Solver;  // create instance of Heuristic1 class
    private int N = 6;

    public NQueen_BFS_DFS_Heuristics() {
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
         generateButton = new JButton("BFS");
    // Generate a solution using BFS ==================================================================================      
     generateButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        try {
            int n = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the number of queens:"));
            if (n < 5) {
                JOptionPane.showMessageDialog(frame, "Please enter a number greater than or equal to 5");
                return;
            }
            N = n;
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
           java.util.List<int[]> solutions = bfsSolver.nQueensBFS(N);
            long endTime = System.currentTimeMillis();
            double duration = (endTime - startTime) / 1000.0;

            // Update the statistics
            methodsComboBox.setSelectedIndex(0);
            Nlabel.setText(String.valueOf(n));
            exploredLabel.setText(String.valueOf(bfsSolver.nodesExpanded));
            generatedLabel.setText(String.valueOf(bfsSolver.nodesGenerated));
            timeLabel.setText(String.format("%.3f", duration));
            
            // ImageIcon queenIcon = new ImageIcon("Queen.png");
            // Display the solution on the chessboard panel
            if (!solutions.isEmpty()) {
                int[] solution = solutions.get(0);
                for (int row = 0; row < N; row++) {
                    for (int col = 0; col < N; col++) {
                        JButton button = (JButton) chessboardPanel.getComponent(row * N + col);
                        if (solution[row] == col) {
                           // button.setIcon(queenIcon);
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
            JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
        }
    }
});
//=====================================================================================================================
 // heuristic 1 panel                  ================================================================================    
       heuristic1Button = new JButton("A* with Heuristic1");
        heuristic1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the number of queens:"));
                    if (n < 5) {
                        JOptionPane.showMessageDialog(frame, "Please enter a number greater than or equal to 5.");
                        return;
                    }
                    N = n;
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

                    // Generate a solution using Heuristic1
                    long startTime = System.currentTimeMillis();
                    int[] solution = heuristic1Solver.A_star(N);
                    long endTime = System.currentTimeMillis();
                    double duration = (endTime - startTime) / 1000.0;

                    // Update the statistics
                    methodsComboBox.setSelectedIndex(2);
                    Nlabel.setText(String.valueOf(n));
                    exploredLabel.setText(String.valueOf(heuristic1Solver.nodesExplored ));
                    generatedLabel.setText(String.valueOf(heuristic1Solver.nodesGenerated ));
                    timeLabel.setText(String.format("%.3f", duration));

                    // Display the solution on the chessboard panel
                    // ImageIcon queenIcon = new ImageIcon("Queen.png");
                    if (solution != null) {
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
                        JOptionPane.showMessageDialog(frame, "Found a solution!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "No solution found.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
                }
            }
        });
        //=====================================================================================================================
 // heuristic 2  panel                  ================================================================================    
       heuristic2Button = new JButton("A* with Heuristic2");
        heuristic2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the number of queens:"));
                    if (n < 5) {
                        JOptionPane.showMessageDialog(frame, "Please enter a number greater than or equal to 5.");
                        return;
                    }
                    N = n;
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

                    // Generate a solution using Heuristic1
                    long startTime = System.currentTimeMillis();
                    java.util.List<int[]> solutions = Heuristic2.nQueensAStar(N);
                    long endTime = System.currentTimeMillis();
                    double duration = (endTime - startTime) / 1000.0;

                    // Update the statistics
                    methodsComboBox.setSelectedIndex(3);
                    Nlabel.setText(String.valueOf(n));
                    exploredLabel.setText(String.valueOf(heuristic2Solver.nodesExpanded ));
                    generatedLabel.setText(String.valueOf(heuristic2Solver.nodesGenerated ));
                    timeLabel.setText(String.format("%.3f", duration));

                    // Display the solution on the chessboard panel
                    // ImageIcon queenIcon = new ImageIcon("Queen.png");
                       if (!solutions.isEmpty()) {
                          int[] solution = solutions.get(0);
                for (int row = 0; row < N; row++) {
                    for (int col = 0; col < N; col++) {
                        JButton button = (JButton) chessboardPanel.getComponent(row * N + col);
                        if (solution[row] == col) {
                           // button.setIcon(queenIcon);
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
            JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
        }
    }
});
        //DFS            ==================================================================================
        
         DFSButton = new JButton("DFS");
        
         DFSButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
        try {
            int n = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the number of queens:"));
            if (n < 5) {
                JOptionPane.showMessageDialog(frame, "Please enter a number greater than or equal to 5");
                return;
            }
            N = n;
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
           java.util.List<int[]> solutions = DFSSolver.nQueensDFS(N);
            long endTime = System.currentTimeMillis();
            double duration = (endTime - startTime) / 1000.0;

            // Update the statistics
            methodsComboBox.setSelectedIndex(1);
            Nlabel.setText(String.valueOf(n));
            exploredLabel.setText(String.valueOf(DFSSolver.nodesExpanded));
            generatedLabel.setText(String.valueOf(DFSSolver.nodesGenerated));
            timeLabel.setText(String.format("%.3f", duration));
            
             ImageIcon queenIcon = new ImageIcon("Queen.png");
            // Display the solution on the chessboard panel
            if (!solutions.isEmpty()) {
                int[] solution = solutions.get(0);
                for (int row = 0; row < N; row++) {
                    for (int col = 0; col < N; col++) {
                        JButton button = (JButton) chessboardPanel.getComponent(row * N + col);
                        if (solution[row] == col) {
                           // button.setIcon(queenIcon);
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
            JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
        }
    }
});
        
        //===================================================================================================
        
        controlPanel.add(generateButton);
        controlPanel.add(DFSButton);
        controlPanel.add(heuristic1Button);  // add new button to control panel 
        controlPanel.add(heuristic2Button);
        
        // Create the statistics panel
        statisticsPanel = new JPanel(new GridLayout(5, 2));
        statisticsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        statisticsPanel.add(new JLabel("Method:"));
        methodsComboBox = new JComboBox<String>(new String[] {"BFS", "DFS", "A* with heuristic 1", "A* with heuristic 2"});
        statisticsPanel.add(methodsComboBox);
        statisticsPanel.add(new JLabel("Number of queens :"));
        Nlabel = new JLabel("0");
        statisticsPanel.add(Nlabel);
        statisticsPanel.add(new JLabel("Nodes generated:"));
        generatedLabel = new JLabel("0");
        statisticsPanel.add(generatedLabel);
        statisticsPanel.add(new JLabel("Nodes explored:"));
        exploredLabel = new JLabel("0");
        statisticsPanel.add(exploredLabel);
     
       
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
        
        NQueen_BFS_DFS_Heuristics gui = new NQueen_BFS_DFS_Heuristics();
    }
}
