import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman implements Huffman_interface{
    public String File_input;
    public String File_input2;
    public String File_input3;
    //Instance variables
    private String file;
    public String[] Characters;
    private String encoded1 = "";
    private String decoded = "";


    //Creating a root node and initializing it to null
    Node root = null;

    protected static class Node {
        private Node left;
        private Node right;
        private String data;
        private Integer data_frequency;

        //Default constructor of Node class
        Node() {
            left = null;
            right = null;
            data = null;
            data_frequency = null;
        }

        //constructor
        Node(Node leftChild, Node Rightchild, String value, Integer frequency) {
            left = leftChild;
            right = Rightchild;
            data = value;
            data_frequency = frequency;
        }

    }

    /*Creating a comparator class that compares the right and left node
    ]* and returns the lowest frequency
     */

    public class Nodecomparator implements Comparator<Node> {

        public int compare(Node left, Node right) {

            return (Integer) left.data_frequency - (Integer) right.data_frequency;
        }
    }

    /*Constructor of Huffman class
     */
    public Huffman() {
    }

    public HashMap<String, Integer> readfile(String File_input) throws IOException {
        HashMap<String, Integer> Mapped_strings = new HashMap<> ();
        // A file reader to read the contents of the file
        FileReader file = new FileReader(File_input);
        BufferedReader Read = new BufferedReader (file);
        String line = null;
        String data = "";
        // Splitting each line in the file into an array
        while ((line = Read.readLine ()) != null) {
            data = line;
            // Looping through the hashmap to locate the key and if the character is not there insert
            // it and give the key 1 or else increase the key for the number of occurence
            if (!line.equals(" ")) {
                Characters = data.split ("");
            }
            for (String i : Characters) {
                if (!Mapped_strings.containsKey (i)) {
                    Mapped_strings.put (i, 1);
                }
                // If the character is already in the hashmap, then increment it's key by 1
                // for each occurence
                Mapped_strings.replace (i, (Mapped_strings.get(i) + 1));




            }
        }
        return Mapped_strings;
    }

    /*
     * Creating a PriorityQueue method that will give the smallest element the highest priority
     * This works similarly as a max heap
     */
    protected PriorityQueue<Node> Queue() throws IOException {
        PriorityQueue<Node> Priorityqueue = new PriorityQueue<> (new Nodecomparator ());
        // Getting Items from the hash map
        Map<String, Integer> queue = this.readfile (File_input);
        // Iterate through the HashMap
        // and create a single node using values and keys from hashmap
        //Store values in a queue
        for (Map.Entry elements : queue.entrySet ()) {
            Priorityqueue.add (new Node(null, null, (String) elements.getKey (), (Integer) elements.getValue ()));

        }
        return Priorityqueue;
    }

    /*
     * Using the implementation or logicin a huffman tree to build a
     * binary tree
     *
     *
     */
    public void huffmanTree() throws IOException {
        PriorityQueue<Node> Tree = Queue ();
        /*
         * Extract the first priority element from the left side of the tree
         * so that we don't violate the binary tree order
         *
         * Afterwards extract the next priority element from the right side of the tree
         * This is performed on the internal nodes of the tree (i.e., nodes with leafs)
         * Provided the left and right nodes are not empty, the frequency of a character in a parent node
         * is the sum of the frequency of the left and right child of the internal nodes to form the parent node
         */
        while (Tree.size() > 1) {

            Node left = Tree.poll();
            Node right = Tree.poll();

            //Creating an internal node
            Node Parent_Node = new Node ();

            // Total frequency of the parent node is the sum of the frequency of the right and left internal nodes
            assert right != null;
            assert left != null;
            Parent_Node.data_frequency = (Integer) left.data_frequency + (Integer) right.data_frequency;

            /*
             * Extracting the left and right node of the parent
             */
            Parent_Node.left = left;
            Parent_Node.right = right;
            /*
             * After summing the left and right child of the internal nodes, move the total frequency accumulated
             * from the internal nodes to be the root node of the tree. (This is how Huffman tree is implemented)
             * Afterwards, insert the frequency of the internal node into PriorityQueue
             */
            root = Parent_Node;
            Tree.add(Parent_Node);

        }

    }

    HashMap<String, String> UniqueCharacter_code = new HashMap<> ();

    public HashMap<String, String> Huffman_Encoding() {
        return Huffman_Encoding (root, "");
    }

    /**
     * A recursive method that provides the huffman encoding of each character
     * The paramaters used here is the huffman root node and the distance of the
     * root to the deepest leaf
     *
     * This method encodes the left distance from the root node to the deepest leaf as zero(0)
     * And the right distance from the root node to the deepest leaf of the tree as 0ne(1)
     * This way the encoding of a character will be the concatenation of the values (0's and 1's)
     * on the character's distance (From root node to the character's location).
     */
    public HashMap<String, String> Huffman_Encoding(Node Huffman_root, String distance) {
        if (Huffman_root != null) {
            /*
             * Performing a left traversal to locate the character being searched and set the distance
             * to zero(0).
             */
            if (Huffman_root.right != null) {Huffman_Encoding (Huffman_root.left, distance + "0");}
            if (Huffman_root.left != null) {Huffman_Encoding(Huffman_root.right, distance + "1");}

            // otherwise place the character with its corresponding encoding (distance) into the new hash map created.
            else {UniqueCharacter_code.put ((String) Huffman_root.data, distance);

            }
        }
        return UniqueCharacter_code;
    }

    /**
     * method to clear the Encoded text file
     *
     * @throws IOException
     */
//    public void clearFile() throws IOException {
//        FileWriter fileWrite = new FileWriter (File_input2, false);
//        PrintWriter pWrite = new PrintWriter (fileWrite, false);
//        pWrite.flush ();
//        fileWrite.close ();
//    }


    /**
     * A function to write the encoded file to a text file
     * called Encoded.txt
     *
     * @param inputFile
     * @throws IOException
     */
    public void writeEncoded(String inputFile) throws IOException {
        String encoded = "";
        /**
         Creating a FileReader to read streams of characters
         */
        FileReader file;
        file = new FileReader (inputFile);
        /**
         Reads text from a character-input stream,
         buffering characters so as to provide for the
         efficient reading of characters, and lines.
         Because FileReader reads bytes from them file and converts them to characters
         and is returned which is sometimes inefficient
         */
        BufferedReader buffRead = new BufferedReader (file);
        String line = null;
        String data = "";
        /**Reading lines and spliting the data
         into an array of String characters
         */
        while ((line = buffRead.readLine ()) != null) {
            data = line;

            if (line != "") {
                Characters = data.split ("");
            }
            /**
             * Loop through the array of characters
             * Loop through the hash map
             * if character at index i equals key in map
             * get the encoded value and add to the encoded string for that line read
             */

            for (int i = 0; i < Characters.length; i++) {
                for (Map.Entry items : UniqueCharacter_code.entrySet ()) {
                    if (Characters[i].equals (items.getKey ())) {
                        encoded += items.getValue () + " ";

                    }
                }
            }



            /**
             * Creating a new file writer instance
             * Wrapping buffered writer around file read
             * to ensure efficient writing of encodings
             * create a newline to write characters to and writing the characters
             * The file is closed and encoded string is made empty again.
             */
            BufferedWriter inW = new BufferedWriter (new FileWriter (File_input2, true));
            inW.flush ();
            inW.newLine ();
            inW.write (encoded);
            encoded = "";
            inW.close ();
        }
    }


    public void writeDecoded() throws IOException {
        String decoded = "";
        /**
         Creating a FileReader to read streams of characters
         */
        FileReader file;
        file = new FileReader (File_input2);
        /**
         Reads text from a character-input stream,
         buffering characters so as to provide for the
         efficient reading of characters, and lines.
         Because FileReader reads bytes from them file and converts them to characters
         and is returned which is sometimes inefficient
         */
        BufferedReader buffRead = new BufferedReader (file);
        String line = null;
        String data = "";
        /**Reading lines and spliting the data
         into an array of String characters
         */
        while ((line = buffRead.readLine ()) != null) {
            data = line;

            if (line != "") {
                Characters = data.split ("");
            }
            /**
             * Loop through the array of characters
             * Loop through the hash map
             * if character at index i equals key in map
             * get the encoded value and add to the encoded string for that line read
             */

            String[] encodeArray = data.split (" ");
            for (int i = 0; i < encodeArray.length; i++) {
                for (Map.Entry items : UniqueCharacter_code.entrySet ()) {
                    if (encodeArray[i].equals (items.getValue ())) {
                        decoded += items.getKey ();
                    }
                }
            }



            /**
             * Creating a new file writer instance
             * Wrapping buffered writer around file read
             * to ensure efficient writing of encodings
             * create a newline to write characters to and writing the characters
             * The file is closed and encoded string is made empty again.
             */
            BufferedWriter inW = new BufferedWriter (new FileWriter (File_input3, true));
            inW.flush ();
            inW.newLine ();
            inW.write (decoded);
            decoded = "";
            inW.close ();
        }
    }




    /**
     * A simple method to print the encoded file,
     * to the console
     *
     * @throws IOException
     */
    public void printEncoded() throws IOException {
        /**
         * Creating a new file reader instance
         * Wrapping buffered reader around file read
         * to ensure efficient reading of encodings
         */


        BufferedReader buffRead = new BufferedReader (new FileReader (File_input2));
        String line = null;
        String data = "";
        /**Reading lines and spliting the data
         into an array of String encodings
         */
        while ((line = buffRead.readLine ()) != null) {
            data = line;

            String[] encodeArray = data.split (" ");
            for (int i = 0; i < encodeArray.length; i++) {
                decoded += encodeArray[i];
            }
            System.out.println (decoded);
            decoded = "";

        }
    }

    /**
     * A function which reads from Encoded.txt
     * prints the decoded results to the console
     *
     * @throws IOException
     */
    public void printDecoded() throws IOException {
        /**
         * Creating a new file reader instance
         * Wrapping buffered reader around file read
         * to ensure efficient reading of encodings
         */

        BufferedReader buffRead = new BufferedReader (new FileReader (File_input2));
        String line = null;
        String data = "";
        /**Reading lines and spliting the data
         into an array of String encodings
         */
        while ((line = buffRead.readLine ()) != null) {
            data = line;

            String[] encodeArray = data.split (" ");

            /**
             * Loop through the array of string encodings
             * Loop through the hash map
             * if encoding at index i equals value in map
             * get the character value and add to the decoded string for that line read
             */
            for (int i = 0; i < encodeArray.length; i++) {
                for (Map.Entry items : UniqueCharacter_code.entrySet ()) {
                    if (encodeArray[i].equals (items.getValue ())) {
                        decoded += items.getKey ();
                    }
                }
            }
            /**
             * Print the decoded string to the console for that line
             * make decoded string empty again.
             */
            System.out.println (decoded);
            decoded = "";

        }
    }
}