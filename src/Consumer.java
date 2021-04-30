import java.net.Socket;
import java.util.List;

public class Consumer extends Thread implements ConsumerInterface, Node{

    //fields
    public int port;
    public int id;
    public Socket clientSocket;
    public Broker broker;

    //methods

    public Consumer(){

    }

    public Consumer(int p, int i){
        this.port = p;
        this.id = i;
    }


    //Consumer Methods Implementation


    public void register(Broker b, String s) {

    }

    public void disconnect(Broker b, String s) {

    }

    public void playData(String s, Value v) {

    }


    //Node Methods Implementation


    public void init(int i) {

    }

    public List<Broker> getBrokers() {
        return null;
    }

    public void connect() {

    }

    public void disconnect() {

    }

    public void updateNodes() {

    }
}
