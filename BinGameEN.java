import java.util.Scanner;
import java.util.Random;

public class BinGameEN {
    public static void main(String[] args) {
        // Initial setup: randomly decide the number of bins (20 to 25 bins)
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        
        int binMax = random.nextInt(6) + 20;  // Generates a number between 20 and 25
        int bins = binMax;
        
        System.out.println("---- Game Start ----");
        System.out.println("Initial number of bins: " + bins);
        
        // Randomly determine who goes first:
        // 1: player starts, 2: computer starts
        int turn = random.nextInt(2) + 1;
        if (turn == 1) {
            System.out.println("You go first.");
        } else {
            System.out.println("Computer goes first.");
        }
        
        // Game loop
        while (bins > 0) {
            System.out.println("\nBins remaining: " + bins);
            int takeBin = 0;
            
            if (turn == 1) { // Player's turn
                while (true) {
                    System.out.print("Please take between 1 and 3 bins: ");
                    String input = scanner.nextLine();
                    try {
                        takeBin = Integer.parseInt(input);
                        if (takeBin < 1 || takeBin > 3) {
                            System.out.println("Please choose a number between 1 and 3.");
                        } else if (takeBin > bins) {
                            System.out.println("Only " + bins + " bins remaining. Choose a smaller number.");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter an integer.");
                    }
                }
            } else { // Computer's turn: choose a move based on a simple strategy
                takeBin = computerStrategy(bins, random);
                System.out.println("Computer takes " + takeBin + " bins.");
            }
            
            // Update the number of bins based on the move
            bins -= takeBin;
            
            // Check win condition: the person who takes the last bin loses
            if (bins == 0) {
                if (turn == 1) {
                    System.out.println("You took the last bin. You lose!");
                } else {
                    System.out.println("Computer took the last bin. You win!");
                }
                break;
            }
            
            // Switch turn
            turn = (turn == 1) ? 2 : 1;
        }
        
        scanner.close();
    }
    
    /**
     * Computer strategy:
     * 
     * Given the remaining number of bins, where the possible moves are between 1 and min(3, remaining),
     * if remaining % 4 == 1, there is no winning move, so choose randomly.
     * Otherwise, select a move that leaves the opponent with a remainder of 1 modulo 4.
     * For example:
     *     - If remaining % 4 == 0 → take 3 bins
     *     - If remaining % 4 == 2 → take 1 bin
     *     - If remaining % 4 == 3 → take 2 bins
     *
     * @param remaining The remaining number of bins.
     * @param random    The Random generator.
     * @return          The number of bins to take.
     */
    private static int computerStrategy(int remaining, Random random) {
        if (remaining % 4 == 1) {
            // No winning strategy: choose randomly
            return random.nextInt(Math.min(3, remaining)) + 1;
        } else {
            int mod = remaining % 4;
            if (mod == 0) {
                return 3;
            } else if (mod == 2) {
                return 1;
            } else if (mod == 3) {
                return 2;
            }
        }
        // Fallback (should rarely be reached)
        return random.nextInt(Math.min(3, remaining)) + 1;
    }
}
