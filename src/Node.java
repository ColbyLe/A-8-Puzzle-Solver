public class Node {
    private Node parent;
    private int board;
    private String bStr;
    private int h1n;
    private int h2n;
    private int gn;
    private int inv;
    boolean solvable;

    // parameters: parent node, board, current path cost
    public Node(Node p, int b, int g) {
        parent = p;
        board = b;
        bStr = Integer.toString(b);
        inv = 0;
        h1n = 0;
        h2n = 0;

        // calculate # inversions, # misplaced tiles, manhattan distance
        final int REDIX = 10;
        for(int i=0; i<bStr.length(); i++) {

            // calculate # misplaced tiles
            if(bStr.charAt(i) != Character.forDigit(i, REDIX)) h1n++;

            // calculate manhattan distance
            h2n += Math.abs(Character.getNumericValue(bStr.charAt(i)) - i);

            // calculate # inversions
            for(int j=i+1; j<bStr.length(); j++) {
                if(bStr.charAt(i) != '0' && bStr.charAt(j) !='0') {
                    if(bStr.charAt(i)>bStr.charAt(j)) inv++;
                }
            }
        }
        if(inv%2 == 1) solvable = false;

        // update gn
        gn = ++g;

    }

    public Node(Node p, String b, int g) {
        parent = p;
        bStr = b;
        inv = 0;
        h1n = 0;
        h2n = 0;

        // calculate # inversions, # misplaced tiles, manhattan distance
        final int REDIX = 10;
        for(int i=0; i<bStr.length(); i++) {

            // calculate # misplaced tiles
            if(bStr.charAt(i) != Character.forDigit(i, REDIX)) h1n++;

            // calculate manhattan distance
            h2n += Math.abs(Character.getNumericValue(bStr.charAt(i)) - i);

            // calculate # inversions
            for(int j=i+1; j<bStr.length(); j++) {
                if(bStr.charAt(i) != '0' && bStr.charAt(j) !='0') {
                    if(bStr.charAt(i)>bStr.charAt(j)) inv++;
                }
            }
        }
        if(inv%2 == 1) solvable = false;

        // update gn
        gn = ++g;

    }

    public Node(int b, int g) {
        board = b;
        bStr = Integer.toString(b);
        inv = 0;
        h1n = 0;
        h2n = 0;

        // calculate # inversions, # misplaced tiles, manhattan distance
        final int REDIX = 10;
        for(int i=0; i<bStr.length(); i++) {

            // calculate # misplaced tiles
            if(bStr.charAt(i) != Character.forDigit(i, REDIX)) h1n++;

            // calculate manhattan distance
            h2n += Math.abs(Character.getNumericValue(bStr.charAt(i)) - i);

            // calculate # inversions
            for(int j=i+1; j<bStr.length(); j++) {
                if(bStr.charAt(i) != '0' && bStr.charAt(j) !='0') {
                    if(bStr.charAt(i)>bStr.charAt(j)) inv++;
                }
            }
        }
        if(inv%2 == 1) solvable = false;

        // update gn
        gn = ++g;
    }

    public Node getParent() {
        return parent;
    }

    public int h1() {
        return h1n;
    }

    public int h2() {
        return h2n;
    }

    public int gn() {
        return gn;
    }

    public int f1() {
        return gn + h1n + 1;
    }

    public int f2() {
        return gn + h2n + 1;
    }

    // get next state
    public Node[] getNext() {
        int counter=0;
        int zIndex = bStr.indexOf(0);
        switch(zIndex) {
            // based on position of empty tile, create Node array and generate successors
            case 0:
                Node[] res = new Node[2];
                StringBuilder sb = new StringBuilder(bStr);
                sb.setCharAt(1, '0');
                sb.setCharAt(0, bStr.charAt(1));
                res[0] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(3, '0');
                sb.setCharAt(0, bStr.charAt(3));
                res[1] = new Node(this, sb.toString(), this.gn()+1);
                return res;

            case 1:
                res = new Node[3];
                sb = new StringBuilder(bStr);
                sb.setCharAt(0, '0');
                sb.setCharAt(1, bStr.charAt(0));
                res[0] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(2, '0');
                sb.setCharAt(0, bStr.charAt(2));
                res[1] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(4, '0');
                sb.setCharAt(0, bStr.charAt(4));
                res[2] = new Node(this, sb.toString(), this.gn()+1);
                return res;

            case 2:
                res = new Node[2];
                sb = new StringBuilder(bStr);
                sb.setCharAt(1, '0');
                sb.setCharAt(2, bStr.charAt(1));
                res[0] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(5, '0');
                sb.setCharAt(2, bStr.charAt(5));
                res[1] = new Node(this, sb.toString(), this.gn()+1);
                return res;

            case 3:
                res = new Node[3];
                sb = new StringBuilder(bStr);
                sb.setCharAt(0, '0');
                sb.setCharAt(3, bStr.charAt(0));
                res[0] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(4, '0');
                sb.setCharAt(0, bStr.charAt(4));
                res[1] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(6, '0');
                sb.setCharAt(0, bStr.charAt(6));
                res[2] = new Node(this, sb.toString(), this.gn()+1);
                return res;

            case 4:
                res = new Node[4];
                sb = new StringBuilder(bStr);
                sb.setCharAt(1, '0');
                sb.setCharAt(4, bStr.charAt(1));
                res[0] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(3, '0');
                sb.setCharAt(4, bStr.charAt(3));
                res[1] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(5, '0');
                sb.setCharAt(4, bStr.charAt(5));
                res[2] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(7, '0');
                sb.setCharAt(4, bStr.charAt(7));
                res[3] = new Node(this, sb.toString(), this.gn()+1);
                return res;

            case 5:
                res = new Node[3];
                sb = new StringBuilder(bStr);
                sb.setCharAt(2, '0');
                sb.setCharAt(5, bStr.charAt(2));
                res[0] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(4, '0');
                sb.setCharAt(5, bStr.charAt(4));
                res[1] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(8, '0');
                sb.setCharAt(5, bStr.charAt(8));
                res[2] = new Node(this, sb.toString(), this.gn()+1);
                return res;

            case 6:
                res = new Node[2];
                sb = new StringBuilder(bStr);
                sb.setCharAt(3, '0');
                sb.setCharAt(6, bStr.charAt(3));
                res[0] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(7, '0');
                sb.setCharAt(6, bStr.charAt(7));
                res[1] = new Node(this, sb.toString(), this.gn()+1);
                return res;

            case 7:
                res = new Node[3];
                sb = new StringBuilder(bStr);
                sb.setCharAt(4, '0');
                sb.setCharAt(7, bStr.charAt(4));
                res[0] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(6, '0');
                sb.setCharAt(7, bStr.charAt(6));
                res[1] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(8, '0');
                sb.setCharAt(7, bStr.charAt(8));
                res[2] = new Node(this, sb.toString(), this.gn()+1);
                return res;

            case 8:
                res = new Node[2];
                sb = new StringBuilder(bStr);
                sb.setCharAt(5, '0');
                sb.setCharAt(8, bStr.charAt(5));
                res[0] = new Node(this, sb.toString(), this.gn()+1);
                sb = new StringBuilder(bStr);
                sb.setCharAt(7, '0');
                sb.setCharAt(8, bStr.charAt(7));
                res[1] = new Node(this, sb.toString(), this.gn()+1);
                return res;
        }
        return null;
    }
}
