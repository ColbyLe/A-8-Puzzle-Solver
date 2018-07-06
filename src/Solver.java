import java.util.PriorityQueue;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.io.*;

public class Solver {
    public static void main(String[] args) throws IOException {
        int choice;
        char cont;
        Scanner kb = new Scanner(System.in);
        System.out.println("--- 8-Puzzle Solver ---\n");
        do {
            do {
                System.out.println("Select an option:\n0) Generate Random Puzzle\n1) Enter Puzzle\n2) File Input\n3) Exit");
                choice = kb.nextInt();
                if(choice>3) System.out.println("\nInvalid selection");
            } while(choice>3 || choice<0);

            switch(choice){
                case 0:
                    System.out.println("\nRandom Puzzle Generation");
                    randomize();
                    break;
                case 1:
                    System.out.println("\nUser Generated Puzzle");
                    userInput();
                    break;
                case 3:
                    System.exit(0);
                    break;
                case 2:
                    System.out.println("\nFile Input");
                    fileInput();
                    break;
            }
            System.out.print("Would you like to continue? (Y/N): ");
            cont = kb.next().charAt(0);
            System.out.println();
        } while(cont == 'y'|| cont == 'Y');
    }

    // A* searchH1 function
    public static Node searchH1(Node first) {
        // System.out.println("Searching state " + first.getState());
        PriorityQueue<Node> frontier = new PriorityQueue<>(1000, new H1NodeComparator());
        Hashtable<String, Node> explored = new Hashtable<>(1000);

        frontier.add(first);
        while(!frontier.isEmpty()) {
            Node q = frontier.poll();
            // System.out.println("Current: " + q.getState());
            explored.put(q.getState(), q);
            if(q.getState().equals("012345678")) return q;
            Node[] qNext = q.getNext();
            // System.out.println("# of successors: " + qNext.length);
            for(int i=0; i<qNext.length; i++) {
                // System.out.println("i: " + i);
                if(!explored.containsKey(qNext[i].getState())) {
                    frontier.add(qNext[i]);
                }

            }
        }
        return null;
    }

    // A* searchH2 function
    public static Node searchH2(Node first) {
        // System.out.println("Searching state " + first.getState());
        PriorityQueue<Node> frontier = new PriorityQueue<>(1000, new H2NodeComparator());
        Hashtable<String, Node> explored = new Hashtable<>(1000);

        frontier.add(first);
        while(!frontier.isEmpty()) {
            Node q = frontier.poll();
            // System.out.println("Current: " + q.getState());
            explored.put(q.getState(), q);
            if(q.getState().equals("012345678")) return q;
            Node[] qNext = q.getNext();
            // System.out.println("# of successors: " + qNext.length);
            for(int i=0; i<qNext.length; i++) {
                // System.out.println("i: " + i);
                if(!explored.containsKey(qNext[i].getState())) {
                    frontier.add(qNext[i]);
                }

            }
        }
        return null;
    }

    // search from randomly generated board
    public static void randomize() {
        Scanner kb = new Scanner(System.in);
        Random rand = new Random();
        long st, time1, time2;
        Node s1;
        Node s2;
        System.out.print("Enter number of random boards to be generated: ");
        int numBoards = kb.nextInt();
        Node start;

        PriorityQueue<TestData> data = new PriorityQueue<>(numBoards, new TestDataComparator());

        for(int i= 0; i<numBoards; i++) {
            // System.out.print("Board " + (i+1) + ": ");
            int counter = 0;
            int[] board;
            StringBuilder sb;
            do {

                board = new int[9];
                sb = new StringBuilder("");
                Arrays.fill(board, -1);
                for(int j = 0; j < board.length; j++) {
                    int tile = rand.nextInt(9);
                    while (boardValidatorR(board, tile) != -1) {
                        tile = rand.nextInt(9);
                    }
                    board[j] = tile;
                    sb.append(board[j]);
                }

                start = new Node(sb.toString(), 0);
            } while(!start.canSolve());
            //start.canSolve();
            //System.out.println("Initial State: " + start.getState());

            System.out.println("\nPuzzle " + (i+1));
            st = System.nanoTime();
            s1 = searchH1(start);
            time1 = timeElapsed(st);
            System.out.println("\nH1 Path: ");
            Node.printPath(s1);

            st = System.nanoTime();
            s2 = searchH2(start);
            time2 = timeElapsed(st);
            System.out.println("\nH2 Path: ");
            Node.printPath(s2);

            data.add(new TestData(start.getState(), s1.gn()-1, time1, time2, s1.getH1Acc(), s2.getH2Acc()));

            //System.out.println(soln.gn());
            //System.out.println("Cost: " + soln.f1());
            //if(soln.gn() == -1) continue;
        }

        printData(data);

    }

    // search from user generated board
    public static void userInput() {
        Scanner kb = new Scanner(System.in);
        Node start, s1, s2;
        long st, time1, time2;
        // while user generated board cannot be solved, enter new board
        do {
            System.out.print("Enter board: ");
            String bStr = kb.nextLine();
            if(bStr.length()!=9) {
                System.out.println("Invalid board");
                return;
            }
            int[] board = new int[9];
            // convert string to int array for validation
            for(int i=0; i<bStr.length(); i++) {
                board[i] = Character.getNumericValue(bStr.charAt(i));
            }
            // validate board
            for(int i=0; i<9; i++) {
                int res = boardValidatorU(board, i);
                if(res>1) {
                    System.out.println("Invalid board");
                    return;
                }
            }
            start = new Node(bStr, -1);
            if(!start.canSolve()) System.out.println("Board cannot be solved.");
        } while(!start.canSolve());
        // create node, then searchH1 for goal state
        //System.out.println("Initial State: " + start.getState());



        st = System.nanoTime();
        s1 = searchH1(start);
        time1 = timeElapsed(st);
        System.out.println("\nH1 Path: ");
        Node.printPath(s1);

        st = System.nanoTime();
        s2 = searchH2(start);
        time2 = timeElapsed(st);
        System.out.println("\nH2 Path: ");
        Node.printPath(s2);

        printData(start.getState(), time1, time2, s1, s2);
    }

    // search by file input
    // assumes all file input contains a valid puzzle
    public static void fileInput() throws IOException{
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter file name: ");
        String fName = kb.nextLine();
        File fIn = new File(fName);
        Scanner fScan = new Scanner(fIn);
        String cursor;
        Node start, s1, s2;
        long st, time1, time2;
        int counter = 0;

        PriorityQueue<TestData> data = new PriorityQueue<>(1000, new TestDataComparator());

        do {
            cursor = fScan.nextLine();
            //System.out.println(counter + ") Initial State: " + cursor);
            start = new Node(cursor, -1);
            if(!start.canSolve()) continue;

            System.out.println("\nPuzzle " + (counter+1));

            st = System.nanoTime();
            s1 = searchH1(start);
            time1 = timeElapsed(st);
            System.out.println("\nH1 Path: ");
            Node.printPath(s1);

            st = System.nanoTime();
            s2 = searchH2(start);
            time2 = timeElapsed(st);
            System.out.println("\nH2 Path: ");
            Node.printPath(s2);

            data.add(new TestData(start.getState(), s1.gn()-1, time1, time2, s1.getH1Acc(), s2.getH2Acc()));
            //printData(start.getState(), time1, time2, s1, s2);
            counter++;
        } while(fScan.hasNextLine());
        printData(data);
    }

    // utility method to find index of a given digit in randomly generated board
    public static int boardValidatorR(int[] a, int search) {
        for(int i = 0; i < a.length; ++i){
            if(a[i] == search) return i;
        }
        return -1;
    }

    // utility method to validate user generated board
    public static int boardValidatorU(int[] a, int search) {
        int acc = 0;
        if(a.length<9) return 999;
        for(int i=0; i<a.length; i++) {
            if(a[i] == search) acc++;
        }
        return acc;
    }

    public static long timeElapsed(long start) {
        return (System.nanoTime() - start);
    }

    // utility methods to print relevant data
    public static void printData(String init, long t1, long t2, Node n1, Node n2) {
        // print in order: depth, getH1 time, getH2 time, getH1, getH2
        System.out.printf("\n%16s %4s %16s %16s %6s %6s\n", "Initial State", "d", "H1 Time (ns)", "H2 Time (ns)", "A*H1", "A*H2");
        System.out.printf("%16s %4d %16d %16d %6d %6d\n\n", init, n1.gn(), t1, t2, n1.getH1Acc(), n2.getH2Acc());
    }

    public static void printData(PriorityQueue<TestData> p) {
        Scanner kb = new Scanner(System.in);
        System.out.println("\nSelect an option: \n0) Print Raw Data\n1) Print Averaged Data\n2) Print Both");
        int choice = kb.nextInt();
        PriorityQueue<TestData> pCopy = new PriorityQueue<>(p);

        if(choice == 0 || choice == 2) {
            System.out.println("\nRaw Data: ");
            System.out.printf("%16s %4s %16s %16s %6s %6s\n", "Initial State", "d", "H1 Time (ns)", "H2 Time (ns)", "A*H1", "A*H2");
            while(!p.isEmpty()) {
                p.poll().print();
            }
        }

        if (choice == 1 || choice ==2) {
            System.out.println("\nAveraged Data: ");
            PriorityQueue<TestData> proc = TestData.process(pCopy);
            System.out.printf("%16s %4s %16s %16s %6s %6s\n", "# of Data Points", "d", "H1 Time (ns)", "H2 Time (ns)", "A*H1", "A*H2");
            while(!proc.isEmpty()) {
                proc.poll().print();
            }
            System.out.println();
        }
    }
}
