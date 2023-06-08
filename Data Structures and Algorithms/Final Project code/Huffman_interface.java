import java.io.IOException;
import java.util.HashMap;

public interface Huffman_interface {
    public HashMap<String,Integer> readfile(String inputFile) throws IOException;
    public void huffmanTree() throws IOException;
}