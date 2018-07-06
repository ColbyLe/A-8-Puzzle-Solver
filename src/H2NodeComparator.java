// utility class to compare nodes by f2
import java.util.Comparator;

public class H2NodeComparator implements Comparator<Node> {
    public int compare(Node n1, Node n2) {
        if(n1.f2()>n2.f2()) return 1;
        else if(n1.f2()<n2.f2()) return -1;
        return 0;
    }
}
