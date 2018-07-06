import java.util.PriorityQueue;

// class to store testing data to be inserted into priority queue
public class TestData {
    private String init;
    private int depth, h1, h2;
    private long time1, time2;

    public TestData(String i, int d, long t1, long t2, int n1, int n2) {
        init = i;
        depth = d;
        time1 = t1;
        time2 = t2;
        h1 = n1;
        h2 = n2;
    }

    private TestData(int d, long t1, long t2, int n1, int n2) {
        depth = d;
        time1 = t1;
        time2 = t2;
        h1 = n1;
        h2 = n2;
    }

    private TestData(TestData t) {
        init = "---------";
        depth = t.depth;
        time1 = t.time1;
        time2 = t.time2;
        h1 = t.h1;
        h2 = t.h2;
    }

    public int getDepth() {
        return depth;
    }

    public String getInit() {
        return init;
    }

    public long getTime1() {
        return time1;
    }

    public long getTime2() {
        return time2;
    }

    public int getH1() {
        return h1;
    }

    public int getH2() {
        return h2;
    }

    private void setNum(int i) {
        init = Integer.toString(i);
    }

    private void add(TestData t) {
        time1+=t.getTime1();
        time2+=t.getTime2();
        h1+=t.getH1();
        h2+=t.getH2();
    }

    private void avg(int i) {
        setNum(i);
        time1/=i;
        time2/=i;
        h1/=i;
        h2/=i;
    }


    public void print() {
        System.out.printf("%16s %4d %16d %16d %6d %6d\n", init, depth++, time1, time2, h1, h2);
    }

    public static PriorityQueue<TestData> process(PriorityQueue<TestData> in) {
        TestData acc;
        PriorityQueue<TestData> average = new PriorityQueue<>(1000, new TestDataComparator());
        int counter;
        if (!in.isEmpty()){
            do {
                acc = new TestData(in.poll());
                counter = 1;

                if(in.isEmpty()) {
                    acc.setNum(counter);
                    average.add(acc);
                    break;
                }

                while(!in.isEmpty() && in.peek().getDepth() == acc.getDepth()) {
                    counter++;
                    acc.add(in.poll());
                }
                acc.avg(counter);
                average.add(acc);
            } while(!in.isEmpty());
            return average;
        }

        return in;
    }
}
