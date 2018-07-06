// utility class to compare testdata by depth
import java.util.Comparator;

public class TestDataComparator implements Comparator<TestData>{
    public int compare(TestData t1, TestData t2) {
        if(t1.getDepth()>t2.getDepth()) return 1;
        else if(t1.getDepth()<t2.getDepth()) return -1;
        return 0;
    }
}
