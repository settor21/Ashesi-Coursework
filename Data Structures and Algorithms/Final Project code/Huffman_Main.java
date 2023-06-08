import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Huffman_Main {
    public  static void main(String[] args) throws IOException {
        Huffman test = new Huffman ();
        Scanner input = new Scanner(System.in);
        System.out.println ("Welcome to Our File Compression Application\n" +
                "The goal of this to application is to read a test file provided by a user\n" +
                "Encode the file using Huffman coding, then " +
                "print the Huffman encodings for each character\n" +
                "The application writes the encoded characters into a file then decodes the encoded file\n" +
                "into a text file with the user specified destination\n");








        System.out.println();
        System.out.print("Paste the file location you want to encode and remove the quotation marks: ");
        String user = input.nextLine();
        test.File_input = user;
        System.out.print("Paste the file location you want to save the encoded file and remove the quotation marks: ");
        String user2 = input.nextLine();
        test.File_input2 = user2;
        System.out.println("The file path has been taken!");


        System.out.print("Paste the location you want to store your decoded file: ");
        String input_2 = input.nextLine();
        test.File_input3 = input_2;
        System.out.println("The file path has been taken!");
        System.out.println();

        System.out.println ("-------------The occurence of a character as specified in the HashMap---------------");
        System.out.println ("Character"+ "\t" + "   Frequency");
        System.out.println ("------------------------");
        HashMap<String, Integer> forPrint = test.readfile (test.File_input);
        for (Map.Entry items : forPrint.entrySet ()) {
            System.out.println ("\t" + (String) items.getKey () + " \t\t|    " + (Integer) items.getValue ());
        }


        //Creating an instance of the hufQueue method
        PriorityQueue<Huffman.Node> arr= test.Queue();

        System.out.println();
        System.out.println();
        //Creating the huffmanTree for the test instance
        test.huffmanTree ();
        //Drawing the tree

        System.out.println ("Note: Character with empty strip and encoding\n denotes the empty string character ");
        System.out.println ("=====Printing out the items in the encoded HashMap=====");
        System.out.println ("Character"+ "\t" + "   Encoding");
        System.out.println ("------------------------");
        HashMap<String, String> forCode = test.Huffman_Encoding();
        for (Map.Entry items : forCode.entrySet ()) {
          System.out.println ("\t" + (String) items.getKey () + " \t\t|    " + (String) items.getValue ());
       }

        System.out.println ();

        System.out.println ();

        System.out.println ();
        //Clearing the encoded text file before writing to it
        //Writing to the encoded text file
        test.writeEncoded (test.File_input);
        test.writeDecoded();
        System.out.println();
        System.out.println();
        System.out.println ("----------This is the end of the Program. Check if all files have been stored at the path specified!");

    }

}