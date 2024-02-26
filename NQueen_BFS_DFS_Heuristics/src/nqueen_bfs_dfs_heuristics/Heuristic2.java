package nqueen_bfs_dfs_heuristics;

import java.util.*;

/**
 *
 * @author Lenovo
 */
public class Heuristic2 {
       public static boolean is_valid(int[] state) {
        int n = state.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                //selon notre modélisation c pas la peinne de verifié les ligne
                // il reste à Vérifier si les reines i et j sont sur la même colonne ou diagonale
                if (state[i] == state[j] || Math.abs(state[i] - state[j]) == Math.abs(i - j)) {
                    //compte le nombre de reines en attaque
                    count++;
                }
            }
        }
        //si nombre de reines en attaque = 0 renvois true sinon false
        return count == 0;
    }
  public static int nodesGenerated = 0;
  public static int nodesExpanded = 0;
        public static List<int[]> nQueensAStar(int n) {
        List<int[]> solutions = new ArrayList<>();
        Queue<State> queue = new PriorityQueue<>();
        int[] startState = new int[0];
        queue.add(new State(startState, 0, heuristic2(startState,n)));
        
        while (!queue.isEmpty() && solutions.isEmpty()) {
            State currState = queue.poll();
            nodesExpanded++;
            if (currState.state.length == n && is_valid(currState.state)) {
                solutions.add(currState.state);
            } else if (currState.state.length < n) {
                // generate and push successor states with exactly n queens placed
                for (int i = 0; i < n; i++) {
                    int[] nextState = Arrays.copyOf(currState.state, currState.state.length + 1);
                    nextState[nextState.length - 1] = i;
                    if (is_valid(nextState)) {
                        queue.add(new State(nextState, currState.cost + 1, heuristic(nextState,n)));
                        nodesGenerated++;
                    }
                }
            }
        }
        System.out.println("Nombre de noeuds generes : " + nodesGenerated);
        System.out.println("Nombre de noeuds developpes : " + nodesExpanded);
        
        return solutions;
    }
    public static int heuristic(int[] state,int n) {
        int attacks = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = i + 1; j < state.length; j++) {
                if (state[i] == state[j] || Math.abs(state[i] - state[j]) == j - i) {
                    attacks++;
                }
            }
        }
        return n - state.length + attacks;
    }
    public static int heuristic2(int[] state, int n) {
        int[] rowCounts = new int[n];
        int[] colCounts = new int[n];
        int emptySquares = n * n;
        int conflicts = 0;
        if (state.length == 0) {
            return 0; // or some other default value
        }
        // Count the number of empty squares in each row and column
        for (int i = 0; i < n; i++) {
            rowCounts[state[i]]++;
            colCounts[i]++;
            emptySquares -= 1;
        }

        // Subtract the number of empty squares in each queen's row and column
        for (int i = 0; i < n; i++) {
            conflicts += rowCounts[state[i]] + colCounts[i];
            if (rowCounts[state[i]] > 0) {
                conflicts -= 2;
                rowCounts[state[i]]--;
            }
            if (colCounts[i] > 0) {
                conflicts -= 2;
                colCounts[i]--;
            }
        }

        // Subtract the number of empty squares in both the queen's row and column
        for (int i = 0; i < n; i++) {
            if (rowCounts[state[i]] > 0 && colCounts[i] > 0) {
                conflicts += 2;
                rowCounts[state[i]]--;
                colCounts[i]--;
            }
        }

        // Return the heuristic value
        return emptySquares - conflicts;
    }

    static class State implements Comparable<State> {
    
        int[] state;
        int cost;
        int heuristic;

        public State(int[] state, int cost, int heuristic) {
            this.state = state;
            this.cost = cost;
            this.heuristic = heuristic;
        }

        public int compareTo(State other) {
            return Integer.compare(this.cost + this.heuristic, other.cost + other.heuristic);
        }
    }
        public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of queens : ");
        int n = scanner.nextInt();
        scanner.close();
        long debut = System.currentTimeMillis();
        List<int[]> solutions = nQueensAStar(n);

        if (solutions.isEmpty()) {
            System.out.println("Pas de solution trouvée.");
        } else {
            //print in array
            for (int[] sol : solutions) {
                System.out.println(Arrays.toString(sol));
            }
            // Print the solution board
            for (int[] sol : solutions) {

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (sol[i] == j) {
                            System.out.print("Q ");
                        } else {
                            System.out.print(". ");
                        }
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
        long fin = System.currentTimeMillis();
        long duree = fin - debut;
        //convertir en seconde
        double dureeEnSecondes = (double) duree / 1000;
        System.out.println("la duree est "+dureeEnSecondes+" taille tableau : \n"+n);

    }
}
