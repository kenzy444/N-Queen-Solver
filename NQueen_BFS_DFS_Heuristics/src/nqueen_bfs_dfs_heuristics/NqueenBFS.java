package nqueen_bfs_dfs_heuristics;

 import java.util.*;
/**
 *
 * @author Lenovo
 */
public class NqueenBFS {
 

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
  public static double dureeEnSecondes =0;
  public static List<int[]> nQueensBFS(int n) {
      
    List<int[]> solutions = new ArrayList<>();
    Queue<int[]> queue = new LinkedList<>();
    int[] startState = new int[0];
    queue.offer(startState);
   
    while (!queue.isEmpty()) {
        int[] currState = queue.poll();
        nodesExpanded++;
        //all queens + valid = solution
        if (currState.length == n && is_valid(currState)) {
            solutions.add(currState);
            break;
        } else if (currState.length < n) {
            // generate and enqueue successor states 
            for (int i = 0; i < n; i++) {
                int[] nextState = Arrays.copyOf(currState, currState.length + 1);
                nextState[nextState.length - 1] = i;
                if (is_valid(nextState)) {
                    queue.offer(nextState);
                    nodesGenerated++;
                }
            }
        }
        //Placing n queens on an n x n chessboard, column by column. It starts with an initial state with zero queens placed, and repeatedly
        //generates and enqueues new states by adding a single new queen to the existing state in each possible column
        //and checking if theresulting state is valid.
    }
    System.out.println("Nombre de noeuds generes : " + nodesGenerated);
    System.out.println("Nombre de noeuds developpes : " + nodesExpanded);
    return solutions;
}



    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of queens : ");
        int n = scanner.nextInt();
        scanner.close();
          long debut = System.currentTimeMillis();
           List<int[]> solutions = nQueensBFS(n);
       
        if (solutions.isEmpty()) {
            System.out.println("Pas de solution trouvée.");
        } else {
            //print in array
            for (int[] sol : solutions) {
                System.out.println(Arrays.toString(sol));
            }
            // Print the solution board
              /*for (int[] sol : solutions) {
           
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
        }*/
        }
            long fin = System.currentTimeMillis();
            long duree = fin - debut;
            //convertir en seconde
             dureeEnSecondes = (double) duree / 1000;
            System.out.println("la duree est "+dureeEnSecondes+" taille tableau : \n"+n);
       
    }
}



    

