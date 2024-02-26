
package nqueen_bfs_dfs_heuristics;

import java.util.*;

public class Heuristic1 {
    public static int nodesGenerated = 0;
    public static int nodesExplored = 0;
    public static double dureeEnSecondes = 0.0;  

//La classe interne Board représente un état du problème des n-reines tel que :
/*n : le nombre de reines  
queens : un tableau représentant l'emplacement de chaque reine  ;
cost : la valeur de l'heuristique pour cet état ie le nombre de conflits entre les reines ;
depth : la profondeur de cet état dans l'arbre de recherche A*.*/    
private static class Board {
    private final int n;
    private final int[] queens;
    private final int cost;
    private final int depth;

    public Board(int n, int[] queens, int depth) {
        this.n = n;
        this.queens = queens;
        this.depth = depth;
        int h = 0; 
        //fonction de vérification
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
               
                if (queens[i] == queens[j] || Math.abs(queens[i] - queens[j]) == j - i) {
                    h++;
                }
            }
        }
        this.cost = h;
    }
//la valeur de l'heuristique pour un  état
    public int getCost() {
        return cost;
    }
//vérifie si l'état actuel est un état objectif ie s'il n'y a aucun conflit entre les reines.
    public boolean isGoal() {
        return cost == 0;
    }
//renvoie un itérable contenant tous les états voisins accessibles depuis l'état actuel.
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (queens[i] != j) {
                    int[] newQueens = queens.clone();
                    newQueens[i] = j;
                    neighbors.add(new Board(n, newQueens, depth + 1));
                }
            }
        }
        return neighbors;
    }
}
// l'algorithme de recherche A*
public static int[] A_star(int n) {
    PriorityQueue<Board> queue = new PriorityQueue<>(Comparator.comparingInt(Board::getCost));
    queue.add(new Board(n, new int[n], 0));
    while (!queue.isEmpty()) {
        Board board = queue.poll();
        nodesExplored++;
        if (board.isGoal()) {
            System.out.println("Nodes generated: " + nodesGenerated);
            System.out.println("Nodes explored: " + nodesExplored);
            return board.queens;
        }
        for (Board neighbor : board.neighbors()) {
            queue.add(neighbor);
            nodesGenerated++;
        }
    }
    return null;
}



public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
     System.out.print("Enter the number of queens : ");
     int n = scanner.nextInt();
    scanner.close();
    long debut = System.currentTimeMillis();
    //nombre de reines 
    int [] sol = A_star(n);
    System.out.println(Arrays.toString(sol));
    long fin = System.currentTimeMillis();
    long duree = fin - debut;
    dureeEnSecondes = (double) duree / 1000;
    System.out.println("la duree est " + dureeEnSecondes+ " Secondes");
}
    
}

