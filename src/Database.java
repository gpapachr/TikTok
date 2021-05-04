import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Database implements Serializable {
    private ArrayList<Broker> brokers = new ArrayList<Broker>();
    
    private int port = 10000;
    private transient Socket clientSocket = null;
    private transient ServerSocket dbSocket = null;

    Database(){
        
    }

    public static void main(String args[]){
            Database db = new Database();
    }
}
