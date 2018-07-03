import java.util.PriorityQueue;
import java.util.Hashtable;

public class Solver {
    public static void main(String[] args) {
        PriorityQueue<Node> frontierH1 = new PriorityQueue<>(1000, new H1NodeComparator());
        PriorityQueue<Node> frontierH2 = new PriorityQueue<>(1000, new H2NodeComparator());
        Hashtable<String, Node> exploredH1 = new Hashtable<>(1000);
        Hashtable<String, Node> exploredH2 = new Hashtable<>(1000);
    }
}
