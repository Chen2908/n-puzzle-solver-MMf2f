import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStarSearch {

    /**
     * Solve n-puzzle using A* search Algorithm
     *
     * @param initial initial state
     * @param goal    goal state
     * @return Array the goal node which includes a back pointer
     *         towards goal state.
     */
    public static Node AStarSolve(State initial, State goal, FileWriter myWriter) {
        try{
            // Min-heap for removing the node from the open set with the
            // smallest f-score.
            Queue<Node> openHeap = new PriorityQueue<Node>();

            // Hash tables with States as keys and Nodes as data for
            // checking if a state is in the open or closed set.
            Map<State, Node> openHash = new HashMap<State, Node>();
            Map<State, Node> closedHash = new HashMap<State, Node>();

            // Add initial node to the open set
            Node n = new Node(initial, null, null, (initial.h(goal)));
            openHash.put(initial, n);
            openHeap.add(n);

            // While there are still elements in the open set
            while(!openHeap.isEmpty()) {
                // Remove node with minimum f-score
                Node n1 = openHeap.poll();
                State s = n1.getState();

                // Move the node from the open to closed set
                openHash.remove(s);
                closedHash.put(s, n1);

                // For each of the four possible operators
                for (State.Operator op : State.Operator.values()) {
                    // Create a new state that is the result of the move
                    State newState = s.move(op);

                    // If the move is invalid or has already been tried,
                    // go on to next move
                    if (newState == null || closedHash.containsKey(newState))
                        continue;

                    // If the new state is not already in the open set
                    if (!openHash.containsKey(newState)) {
                        // Create a new Node for this state
                        Node newNode = new Node(newState, n1, op, (newState.h(goal)));
                        // If goal state is found - finish
                        if (newState.equals(goal)){
                            int openNodeCount = openHash.size()+ 1;
                            int closedNodeCount = closedHash.size();
                            myWriter.write("Found path: depth:" + newNode.getDepth() + "\n");
                            myWriter.write("Nodes Generated: " + (openNodeCount + closedNodeCount));
                            myWriter.write(" (" + openNodeCount + " open/");
                            myWriter.write(closedNodeCount + " closed)\n");
                            return newNode;
                        }
                        else{
                           openHeap.add(newNode);
                           openHash.put(newState, newNode);
                        }
                    }
                }
            }
        }catch(IOException e) {
            System.out.println("An error occurred while writing to output file");
        }
        return null;    // No solution found
    }
}
