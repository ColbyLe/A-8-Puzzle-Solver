// utility class to compare nodes by f1
import java.util.Comparator;

public class H1NodeComparator implements Comparator<Node> {
    public int compare(Node n1, Node n2) {
        if(n1.f1()>n2.f1()) return 1;
        else if(n1.f1()<n2.f1()) return -1;
        return 0;
    }
}
