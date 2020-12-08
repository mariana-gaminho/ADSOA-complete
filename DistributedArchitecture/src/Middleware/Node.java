package Middleware;

import Elements.Connection;
import Elements.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.UUID;

import static Elements.ContentCodes.NODE;

// DATA FIELD NODE //
// * Create a server socket for the node
// * Connect to data field
// * Continue listening for more nodes

public class Node {
    // Range of ports for nodes in DF
    int minPort = 1025;
    int maxPort = 1035;

    // Unique identifier, port and address for each node
    String uniqueIdentifier;
    int port;
    InetAddress ip;

    // Server Socket
    ServerSocket ss;

    // Socket object for client requests
    Socket s;

    // Array list to save all sockets with ports
    // Note: An array list is a resizable array
    ArrayList<Connection> eveyonesInfo = new ArrayList<>();

    // Start Node and Get a Port
    public void startNode() throws UnknownHostException {

        // Assign an identifier and ip address to the node
        uniqueIdentifier = String.valueOf(UUID.randomUUID());
        ip = InetAddress.getByName("localhost");

        for(int i = this.minPort; i <= this.maxPort; i++) {
            try {
                // Node is listening on port i
                this.ss = new ServerSocket(i);
                this.port = ss.getLocalPort();

                System.out.println("New node at port " + this.port);

                return;
            } catch (IOException e) {
                // e.printStackTrace(); This displays a huuuge error in the terminal
                System.out.println("--- Port " + i + " is already in use ---");
            }
        }
    }

    // Establish a connection with the other nodes
    public void joinDataField() {
        // Iterate through ports looking for other nodes
        for(int i = this.minPort; i <= this.maxPort; i++) {
            if(i != this.port) {
                // Try to connect to other nodes using a Socket
                try {
                    // Establish the connection with other node port.
                    // ip is my localhost
                    Socket mysterious = new Socket(ip, i);
                    System.out.println("Found another node at port " + mysterious.getPort());
                    // Create a new thread object
                    Thread t = new NodeHandler(mysterious, this, uniqueIdentifier,"to");
                    // Invoking the start() method
                    t.start();
                } catch (IOException e) {
                    // e.printStackTrace(); Let's just comment this to avoid another huge error msg
                    // System.out.println("Port " + i + " is empty");
                }
            }
        }
    }

    // Always keep listening for new nodes
    public void listen() {
        // running infinite loop for getting
        // new node request
        while(true) {
            try {
                // socket object to receive incoming client requests
                this.s = this.ss.accept();

                // Create a new thread object
                Thread t = new NodeHandler(this.s, this, uniqueIdentifier,"from");

                // Invoking the start() method
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method for sending a message (it can be between nodes or between clients and servers)
    // We use a synchronized method to prevent multiple threads trying to access the same resources
    public synchronized void sendMessage(Message msg, Socket mysterious, ObjectOutputStream oos) {
        System.out.println("------------------- SENT MESSAGE --------------------");
        System.out.println("Content code: " + msg.getCode());
        System.out.println("Message: " + msg.getText());
        System.out.println("Footprint: " + msg.getSenderFootprint());
        System.out.println("Event: " + msg.getEvent());
        try {
            // Send the object through output stream
            // The writeObject method serializes an Object and sends it to the output stream
            oos.writeObject(msg);
            System.out.println("Message status: Sent");
        } catch (IOException e) {
            System.out.println("Message status: Error. Not sent");
        }
        System.out.println("-----------------------------------------------------");
    }

    // Add every new node, client and server to the arraylist
    public synchronized void addSomeoneToArraylist(Connection c) {
        eveyonesInfo.add(c);
        System.out.println("Added a " + c.getTargetElement() + " to my contacts " + c.getSocketInfo());
    }

    public synchronized void sendToEveryone(Message msg) {
        boolean forAll =  false;
        if (msg.getSendToEveryone())
        {
            msg.setSendToEveryone(false);
            forAll = true;
        }

        // Iterate through arraylist of contacts
        for(Connection c: eveyonesInfo) {
            if(forAll) {
                sendMessage(msg, c.getSocketInfo(), c.getOos());
            }else {
                if(c.getTargetElement() != NODE) {
                    sendMessage(msg, c.getSocketInfo(), c.getOos());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Node n = new Node();

        // Assign a ServerSocket and port to the node
        n.startNode();
        // Join all the other nodes in the DF
        n.joinDataField();
        // Stay listening for new nodes/clients/servers
        n.listen();
    }
}
