package Middleware;

import Elements.Connection;
import Elements.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static Elements.ContentCodes.*;

public class NodeHandler extends Thread {
    public Socket s;
    public Node n;
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    private boolean isAlive;
    private String uniqueIdentifier;

    public NodeHandler(Socket s, Node n, String uniqueIdentifier, String direction) {
        try {
            this.s = s;
            this.n = n;
            this.uniqueIdentifier = uniqueIdentifier;

            // Obtaining output stream
            oos = new ObjectOutputStream(this.s.getOutputStream());

            if (direction.equals("to")){

                System.out.println("New thread to " + this.s.getLocalPort());
            }
            else if (direction.equals("from"))
            {
                System.out.println("New thread to " + this.s.getPort());
            }
            sayHelloToOthers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(true) {
            try {
                receiveFromInputStream();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sayHelloToOthers() {
        Message msg = new Message();
        this.isAlive = true;
        msg.setCode(NEW_NODE);
        msg.setText("Hello, I'm a node");
        msg.setSenderFootprint(uniqueIdentifier);
        n.sendMessage(msg, s, oos);
    }

    public void receiveFromInputStream() throws ClassNotFoundException {
        try {
            ois = new ObjectInputStream(this.s.getInputStream());
        } catch (IOException e) {
            if(isAlive)
            {
                System.out.println("Element at port " + s.getPort() + " left");
            }
            this.isAlive = false;
        }
        while(isAlive) {
            Message receivedMessage;
            try {
                // The method readObject is used to deserialize an Object
                receivedMessage = (Message) ois.readObject();
                handleMessage(receivedMessage);
            } catch (IOException e) {
                break;
            }
        }
    }

    public void handleMessage(Message msg) {

        switch(msg.getCode()) {
            case NEW_NODE:
            {
                Connection c = new Connection();
                c.setSocketInfo(s);
                c.setTargetElement(NODE);
                c.setOos(oos);
                System.out.println("------------------- INBOX MESSAGE -------------------");
                System.out.println("Message: " + msg.getText());
                System.out.println("-----------------------------------------------------");
                this.n.addSomeoneToArraylist(c);
                break;
            }
            case NEW_SERVER:
            {
                Connection c = new Connection();
                c.setSocketInfo(s);
                c.setTargetElement(SERVER);
                c.setOos(oos);
                System.out.println("------------------- INBOX MESSAGE -------------------");
                System.out.println("Message: " + msg.getText());
                System.out.println("-----------------------------------------------------");
                this.n.addSomeoneToArraylist(c);
                break;
            }
            case NEW_CLIENT:
            {
                Connection c = new Connection();
                c.setSocketInfo(s);
                c.setTargetElement(CLIENT);
                c.setOos(oos);
                System.out.println("------------------- INBOX MESSAGE -------------------");
                System.out.println("Message: " + msg.getText());
                System.out.println("-----------------------------------------------------");
                this.n.addSomeoneToArraylist(c);
                break;
            }

            case SUM:
            case SUB:
            case MULT:
            case DIV:
            {
                System.out.println("--- MESSAGE W/OPERATION");
                System.out.println("Event: " + msg.getEvent());
                System.out.println("Operation: " + msg.getN1() + msg.getText() + msg.getN2());
                System.out.println("-----------------------------------------------------");
                n.sendToEveryone(msg);
                break;
            }
            case RESULT_SUM:
            case RESULT_SUB:
            case RESULT_MULT:
            case RESULT_DIV:
            {
                System.out.println("--- MESSAGE W/RESULT");
                System.out.println("Event: " + msg.getEvent());
                System.out.println("Operation: "+ msg.getN1() + msg.getText() + msg.getN2());
                System.out.println("Result: " + msg.getResult());
                System.out.println("-----------------------------------------------------");
                n.sendToEveryone(msg);
                break;
            }
            case JAR_SUM:
            case JAR_SUB:
            case JAR_MULT:
            case JAR_DIV:
            {
                System.out.println("--- MESSAGE W/JAR FILE");
                System.out.println("Content code: " + msg.getCode());
                System.out.println("-----------------------------------------------------");
                n.sendToEveryone(msg);
                break;
            }
            case NO_RESULT:
            {
                System.out.println("--- MESSAGE W/NO JAR ERROR");
                System.out.println("Content code: " + msg.getCode());
                System.out.println("-----------------------------------------------------");
                n.sendToEveryone(msg);
                break;
            }
            default:
                break;
        }
    }
}

