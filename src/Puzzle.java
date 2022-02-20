import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Puzzle {

	/**
	 * Tile Puzzle Solver. Code is based on https://github.com/yuvallb/15-puzzle-solver-MM-search
	 * Can solve 8-puzzle and 15-puzzle.
	 *
	 * @param args Puzzle size and input file with initial puzzle states
	 */
	public static void main(String args[]) {

		// Initial states of puzzles to solve
		List<State> initials = new ArrayList<State>();

		if (args.length < 2) {
			System.out.println("Error: input is not valid");
			System.exit(1);
		}

		// Define the puzzle size
		int size = 0;
		try {
			size = Integer.parseInt(args[1]);
		} catch (Exception e) {
			System.out.println("Couldn't parse size");
		}

		// Read input from file given on command line
		Scanner s = null;
		try {
			s = new Scanner(new FileInputStream(args[0]));
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't open input file '" + args[0] + "'");
			System.exit(1);
		}
		while (s.hasNextLine()) {
			String line = s.nextLine();
			// Skip blank lines
			if (line.isEmpty())
				continue;
			Scanner ss = new Scanner(line);
			byte[][] board = new byte[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (ss.hasNextInt()) {
						board[i][j] = (byte) ss.nextInt();
					} else {
						System.out.println("Invalid input file");
						System.exit(1);
					}
				}
			}
			ss.close();
			initials.add(new State(board));
		}
		s.close();

		// Goal state for different puzzle sizes
		State goal_15 = new State(new byte[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}});
		State goal_8 = new State(new byte[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});

		State goal = null;
		if (size == 4) {
			goal = goal_15;
		} else if (size == 3) {
			goal = goal_8;
		}

		try{
            FileWriter myWriter = new FileWriter("results.txt");
            myWriter.write("--------------------- Tile Puzzle Results ---------------------\n");

            // Run solver on each test case
            for (State initial : initials) {
                System.out.println("Solving input: \n ");
                System.out.println(initial + "\n========================\n");

                myWriter.write("Initial state: \n" + initial + "\n========================\n");
                String[] algorithms = {"bidirectional A*", "A*", "MM", "MMf2f", "MMf2fEC"};
                String[] hueristics = {"linearConflict", "manhattanDistance"};

                for (String alg : algorithms) {
					myWriter.write("\n------------------------------------------------------\n");
                    myWriter.write("Solving tile puzzle using " + alg + "\n------------------------------\n");
					System.out.println("\n" + alg + "\n");
                    switch (alg) {
                        case "bidirectional A*":
                            Config.f2fEndCondition = false;
                            Config.f2f = false;
                            break;
                        case "A*":
                            Config.f2fEndCondition = false;
                            Config.f2f = false;
							break;
                        case "MM":
                            Config.f2fEndCondition = false;
                            Config.f2f = false;
							break;
                        case "MMf2f":
                            Config.f2fEndCondition = false;
                            Config.f2f = true;
							break;
                        case "MMf2fEC":
                            Config.f2fEndCondition = true;
                            Config.f2f = false;
							break;
                    }
                    for (String hue : hueristics) {
                        if (hue == "linearConflict")
                            Config.LinearConflict = true;
                        else
                            Config.LinearConflict = false;
						myWriter.write("Heuristic: " + hue + "\n------------------------------\n");
                        solve(alg, initial, goal, myWriter);
                        myWriter.write("\n------------------------------------------------------\n");
                    }
                }
				myWriter.write("\n\n\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to output file");
        }
	}

	public static void solve(String algo, State initial, State goal, FileWriter myWriter) {
		Node[] solution = new Node[1];
		long start = System.currentTimeMillis();
		switch (algo) {
			case "bidirectional A*":
				solution = BidiAStarSearch.biDirectionalSolve(initial, goal, myWriter);
				break;
			case "A*":
				solution[0] = AStarSearch.AStarSolve(initial, goal, myWriter);
				break;
			case "MM":
				solution = MMsearch.MMSolve(initial, goal, myWriter);
				break;
			case "MMf2f":
				solution = MMsearch.MMSolve(initial, goal, myWriter);
				break;
			case "MMf2fEC":
                solution = MMsearch.MMSolve(initial, goal, myWriter);
				break;
		}
		long end = System.currentTimeMillis();
		long measuredTime = end - start;
		try {
			if (solution == null) {
				myWriter.write("No solution Found!\n");
			} else {
				myWriter.write("Run time: " + measuredTime + " ms\n");
			}
		} catch (IOException e) {
			System.out.println("An error occurred while writing to output file");
		}
	}

}
