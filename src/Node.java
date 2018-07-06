public class Node {
    private Node parent;
    private String state;
    private int h1n = 0;
    private int h2n = 0;
    private int h1Acc = 0;
    private int h2Acc = 0;
    private int gn;
    private int inv;
    private boolean solvable = true;
    private boolean hasP = false;

    // parameters: parent node, string representation of board state, g(n)
    private Node(Node p, String b, int g) {
        parent = p;
        state = b;
        inv = 0;
        hasP = true;

        int[] bNum = toIntArr(b);

        // calculate # misplaced tiles, manhattan distance
        final int REDIX = 10;
        for(int i = 0; i< state.length(); i++) {
            // calculate # misplaced tiles
            if(state.charAt(i) != Character.forDigit(i, REDIX)) h1n++;

            // calculate manhattan distance
            h2n += Math.abs(Character.getNumericValue(state.charAt(i)) - i);
        }

        // accumulate
        h1Acc += parent.getH1Acc() + h1n;
        h2Acc += parent.getH2Acc() + h2n;

        // calculate # inversions
        for(int i=0; i<bNum.length-1; i++) {
            for(int j=i+1; j<bNum.length; j++) {
                if(bNum[i] != 0 && bNum[j] !=0 && bNum[i] > bNum[j]) inv++;
            }
        }

        if(inv%2 == 1) solvable = false;
        //System.out.println(inv);

        // update gn
        gn = ++g;
    }

    // overloaded constructor for initial nodes
    // parameters: int representation of board state, g(n)
    public Node(String b, int g) {
        state = b;
        inv = 0;

        int[] bNum = toIntArr(b);

        // calculate # misplaced tiles, manhattan distance
        final int REDIX = 10;
        for(int i = 0; i< state.length(); i++) {
            // calculate # misplaced tiles
            if(state.charAt(i) != Character.forDigit(i, REDIX)) h1n++;

            // calculate manhattan distance
            h2n += Math.abs(Character.getNumericValue(state.charAt(i)) - i);

        }
        h1Acc = h1n;
        h2Acc = h2n;
        // calculate # inversions
        for(int i=0; i<bNum.length-1; i++) {
            for(int j=i+1; j<bNum.length; j++) {
                if(bNum[i] != 0 && bNum[j] !=0 && bNum[i] > bNum[j]) inv++;
            }
        }

        if(inv%2 == 1) solvable = false;
        //System.out.println(inv);
        // update gn
        gn = ++g;
    }

    // setters and getters
    public Node getParent() {
        return parent;
    }

    public int getH1() {
        return h1n;
    }

    public int getH2() {
        return h2n;
    }

    public int getH1Acc() {
        return h1Acc;
    }

    public int getH2Acc() {
        return h2Acc;
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

    public boolean hasParent() {
        return hasP;
    }

    public boolean canSolve() {
        return solvable;
    }

    public String getState() {
        return state;
    }

    // utility function to print path taken
    public static Node printPath(Node n) {
        if(!n.hasParent()) {
            System.out.println("Initial State: " + n.getState());
            return n;
        }

        if(n.hasParent()) {
            if(n.getState().equals("012345678")) System.out.print("Goal State:    ");
            else System.out.print("               ");
            System.out.println(n.getState());
            return printPath(n.getParent());
        }
        return n;
    }

    // utility function to convert string to int array
    private static int[] toIntArr(String in) {
        int[] out = new int[in.length()];
        for(int i=0; i<out.length; i++) {
            out[i] = in.charAt(i) - 48;
        }
        return out;
    }

    // utility function to swap tiles
    private static void swap(StringBuilder sb, String state, int sIndex, int zIndex) {
        sb.setCharAt(zIndex, state.charAt(sIndex));
        sb.setCharAt(sIndex, '0');
    }

    // get next state
    // all possible locations of empty tile have been hard coded for simplicity
    public Node[] getNext() {
        int zIndex = this.state.indexOf('0');
        switch(zIndex) {
            // based on position of empty tile, create Node array and generate successors
            case 0:
                Node[] res = new Node[2];
                StringBuilder sb = new StringBuilder(this.state);
                //sb.setCharAt(0, state.charAt(1));
                //sb.setCharAt(1, '0');
                swap(sb, this.state, 0, zIndex);
                res[0] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(this.state);
                //sb.setCharAt(0, state.charAt(3));
                //sb.setCharAt(3, '0');
                swap(sb, this.state, 3, zIndex);
                res[1] = new Node(this, sb.toString(), this.gn());
                return res;

            case 1:
                res = new Node[3];
                sb = new StringBuilder(this.state);
                //sb.setCharAt(1, state.charAt(0));
                //sb.setCharAt(0, '0');
                swap(sb, this.state, 0, zIndex);
                res[0] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(this.state);
                //sb.setCharAt(0, state.charAt(2));
                //sb.setCharAt(2, '0');
                swap(sb, this.state, 2, zIndex);
                res[1] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(state);
                //sb.setCharAt(0, state.charAt(4));
                //sb.setCharAt(4, '0');
                swap(sb, this.state, 4, zIndex);
                res[2] = new Node(this, sb.toString(), this.gn());
                return res;

            case 2:
                res = new Node[2];
                sb = new StringBuilder(state);
                //sb.setCharAt(2, state.charAt(1));
                //sb.setCharAt(1, '0');
                swap(sb, this.state, 1, zIndex);
                res[0] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(state);
                //sb.setCharAt(2, state.charAt(5));
                //sb.setCharAt(5, '0');
                swap(sb, this.state, 5, zIndex);
                res[1] = new Node(this, sb.toString(), this.gn());
                return res;

            case 3:
                res = new Node[3];
                sb = new StringBuilder(state);
                //sb.setCharAt(3, state.charAt(0));
                //sb.setCharAt(0, '0');
                swap(sb, this.state, 0, zIndex);
                res[0] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(state);
                //sb.setCharAt(0, state.charAt(4));
                //sb.setCharAt(4, '0');
                swap(sb, this.state, 4, zIndex);
                res[1] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(state);
                //sb.setCharAt(0, state.charAt(6));
                //sb.setCharAt(6, '0');
                swap(sb, this.state, 6, zIndex);
                res[2] = new Node(this, sb.toString(), this.gn());
                return res;

            case 4:
                res = new Node[4];
                sb = new StringBuilder(state);
                //sb.setCharAt(4, state.charAt(1));
                //sb.setCharAt(1, '0');
                swap(sb, this.state, 1, zIndex);
                res[0] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(state);
                //sb.setCharAt(4, state.charAt(3));
                //sb.setCharAt(3, '0');
                swap(sb, this.state, 3, zIndex);
                res[1] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(state);
                //sb.setCharAt(4, state.charAt(5));
                //sb.setCharAt(5, '0');
                swap(sb, this.state, 5, zIndex);
                res[2] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(state);
                //sb.setCharAt(4, state.charAt(7));
                //sb.setCharAt(7, '0');
                swap(sb, this.state, 7, zIndex);
                res[3] = new Node(this, sb.toString(), this.gn());
                return res;

            case 5:
                res = new Node[3];
                sb = new StringBuilder(state);
                //sb.setCharAt(5, state.charAt(2));
                //sb.setCharAt(2, '0');
                swap(sb, this.state, 2, zIndex);
                res[0] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(state);
                //sb.setCharAt(5, state.charAt(4));
                //sb.setCharAt(4, '0');
                swap(sb, this.state, 4, zIndex);
                res[1] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(state);
                //sb.setCharAt(5, state.charAt(8));
                //sb.setCharAt(8, '0');
                swap(sb, this.state, 8, zIndex);
                res[2] = new Node(this, sb.toString(), this.gn());
                return res;

            case 6:
                res = new Node[2];
                sb = new StringBuilder(state);
                //sb.setCharAt(6, state.charAt(3));
                //sb.setCharAt(3, '0');
                swap(sb, this.state, 3, zIndex);
                res[0] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(state);
                //sb.setCharAt(6, state.charAt(7));
                //sb.setCharAt(7, '0');
                swap(sb, this.state, 7, zIndex);
                res[1] = new Node(this, sb.toString(), this.gn());
                return res;

            case 7:
                res = new Node[3];
                sb = new StringBuilder(state);
                //sb.setCharAt(7, state.charAt(4));
                //sb.setCharAt(4, '0');
                swap(sb, this.state, 4, zIndex);
                res[0] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(state);
                //sb.setCharAt(7, state.charAt(6));
                //sb.setCharAt(6, '0');
                swap(sb, this.state, 6, zIndex);
                res[1] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(state);
                //sb.setCharAt(7, state.charAt(8));
                //sb.setCharAt(8, '0');
                swap(sb, this.state, 8, zIndex);
                res[2] = new Node(this, sb.toString(), this.gn());
                return res;

            case 8:
                res = new Node[2];
                sb = new StringBuilder(state);
                //sb.setCharAt(8, state.charAt(5));
                //sb.setCharAt(5, '0');
                swap(sb, this.state, 5, zIndex);
                res[0] = new Node(this, sb.toString(), this.gn());
                sb = new StringBuilder(state);
                //sb.setCharAt(8, state.charAt(7));
                //sb.setCharAt(7, '0');
                swap(sb, this.state, 7, zIndex);
                res[1] = new Node(this, sb.toString(), this.gn());
                return res;
        }
        return null;
    }
}
