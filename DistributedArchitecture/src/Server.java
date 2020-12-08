import Elements.ContentCodes;
import Elements.Message;
import Ops.Div;
import Ops.Mult;
import Ops.Sub;
import Ops.Sum;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

import static Elements.ContentCodes.*;

public class Server {
    Integer minPort = 1025;
    Integer maxPort = 1035;
    int number1, number2;
    String footprint;
    private Socket s;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    URL[] classLoaderUrls;
    URLClassLoader urlClassLoader;
    Class<?> clazz;
    // Operations array: {sum, sub, mult, div}
    Boolean[] operationsArray = {false, false, false, false};

    private void assignFootprint() {
        String milliseconds = String.valueOf(new Date().getTime());
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(milliseconds.getBytes(StandardCharsets.UTF_8));
            footprint = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void joinDataField() {
        Boolean isAlive = false;
        Random r = new Random();

        while(!isAlive) {
            // Get a random node port
            int randomPort = r.nextInt(maxPort - minPort) + minPort;

            try {
                this.s = new Socket("127.0.0.1", randomPort);
                isAlive = true;
                oos = new ObjectOutputStream(this.s.getOutputStream());
            } catch (Exception ignored) {
            }
        }

    }

    public void contactNode() {
        System.out.println("Contacting node at port " +  s.getPort() + ". Localport: " + s.getLocalPort());
        Message msg = new Message();
        msg.setCode(ContentCodes.NEW_SERVER);
        msg.setSenderFootprint(this.footprint);
        msg.setText(" Hello, I'm a server ");
        this.sendMessage(msg);
    }

    public void sendMessage(Message msg) {

        System.out.println("------------------- SENT MESSAGE --------------------");
        System.out.println("Content code: " + msg.getCode());
        System.out.println("Message: " + msg.getText());
        try {
            oos.writeObject(msg);
            System.out.println("Message status: Sent");
        } catch (IOException e) {
            System.out.println("Message status: Error. Not sent");
        }
        System.out.println("-----------------------------------------------------");
    }

    // Receive messages from nodes. Messages can contain a "hello" message or an operation.
    public void receiveFromNode() {
        try {
            this.ois = new ObjectInputStream(this.s.getInputStream());
            this.contactNode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            Message message;
            try {
                message = (Message) this.ois.readObject();
                System.out.println("------------------- INBOX MESSAGE -------------------");
                System.out.println("Content code: #" + message.getCode() + "#");
                System.out.println("Message: " + message.getText());
                System.out.println("-----------------------------------------------------");
                calculateResult(message);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Lost connection to Data Field");
                break;
            }
        }
    }

    private void checkExistingJars() {
        File auxFile = new File("/home/mariana/IdeaProjects/Server/Sum.jar");
        if(auxFile.exists()) {
            operationsArray[0] = true;
        } else {
            auxFile.delete();
            operationsArray[0] = false;
        }
        auxFile = new File("/home/mariana/IdeaProjects/Server/Sub.jar");
        if(auxFile.exists()) {
            operationsArray[1] = true;
        } else {
            auxFile.delete();
            operationsArray[1] = false;
        }
        auxFile = new File("/home/mariana/IdeaProjects/Server/Mult.jar");
        if(auxFile.exists()) {
            operationsArray[2] = true;
        } else {
            auxFile.delete();
            operationsArray[2] = false;
        }
        auxFile = new File("/home/mariana/IdeaProjects/Server/Div.jar");
        if(auxFile.exists()) {
            operationsArray[3] = true;
        } else {
            auxFile.delete();
            operationsArray[3] = false;
        }
    }

    private void calculateResult(Message msg) throws IOException {
        checkExistingJars();
        switch(msg.getCode()) {
            case SUM:
                if(operationsArray[0]) {
                    try {
                        classLoaderUrls = new URL[]{new URL("file:////home/mariana/IdeaProjects/Server/Sum.jar")};
                        urlClassLoader = new URLClassLoader(classLoaderUrls);
                        clazz = urlClassLoader.loadClass("Ops.Sum");
                        Sum sumOp = (Sum) clazz.newInstance();
                        oos.writeObject(sumOp.sumNumbers(msg.getN1(), msg.getN2(), msg.getSenderFootprint(),
                                msg.getEvent(), msg.getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Message errorMsg = new Message();
                    errorMsg.setCode(NO_RESULT);
                    errorMsg.setSenderFootprint(msg.getSenderFootprint());
                    errorMsg.setEvent(msg.getEvent());
                    errorMsg.setText("No Sum.jar file found");
                    sendMessage(errorMsg);
                }
                break;
            case SUB:
                if(operationsArray[1]) {
                    try {
                        classLoaderUrls = new URL[]{new URL("file:////home/mariana/IdeaProjects/Server/Sub.jar")};
                        urlClassLoader = new URLClassLoader(classLoaderUrls);
                        clazz = urlClassLoader.loadClass("Ops.Sub");
                        Sub subOp = (Sub) clazz.newInstance();
                        oos.writeObject(subOp.subNumbers(msg.getN1(), msg.getN2(), msg.getSenderFootprint(),
                                msg.getEvent(), msg.getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Message errorMsg = new Message();
                    errorMsg.setCode(NO_RESULT);
                    errorMsg.setSenderFootprint(msg.getSenderFootprint());
                    errorMsg.setEvent(msg.getEvent());
                    errorMsg.setText("No Sub.jar file found");
                    sendMessage(errorMsg);
                }
                break;
            case MULT:
                if(operationsArray[2]) {
                    try {
                        classLoaderUrls = new URL[]{new URL("file:////home/mariana/IdeaProjects/Server/Mult.jar")};
                        urlClassLoader = new URLClassLoader(classLoaderUrls);
                        clazz = urlClassLoader.loadClass("Ops.Mult");
                        Mult multOp = (Mult) clazz.newInstance();
                        oos.writeObject(multOp.multNumbers(msg.getN1(), msg.getN2(), msg.getSenderFootprint(),
                                msg.getEvent(), msg.getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Message errorMsg = new Message();
                    errorMsg.setCode(NO_RESULT);
                    errorMsg.setSenderFootprint(msg.getSenderFootprint());
                    errorMsg.setEvent(msg.getEvent());
                    errorMsg.setText("No Mult.jar file found");
                    sendMessage(errorMsg);
                }
                break;
            case DIV:
                if(operationsArray[3]) {
                    try {
                        classLoaderUrls = new URL[]{new URL("file:////home/mariana/IdeaProjects/Server/Div.jar")};
                        urlClassLoader = new URLClassLoader(classLoaderUrls);
                        clazz = urlClassLoader.loadClass("Ops.Div");
                        Div divOp = (Div) clazz.newInstance();
                        oos.writeObject(divOp.divNumbers(msg.getN1(), msg.getN2(), msg.getSenderFootprint(),
                                msg.getEvent(), msg.getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Message errorMsg = new Message();
                    errorMsg.setCode(NO_RESULT);
                    errorMsg.setSenderFootprint(msg.getSenderFootprint());
                    errorMsg.setEvent(msg.getEvent());
                    errorMsg.setText("No Div.jar file found");
                    sendMessage(errorMsg);
                }
                break;
            case JAR_SUM: {
                operationsArray[0] = true;
                try {
                    byte[] bytes = Base64.getDecoder().decode(msg.getText());
                    File jarFile = new File("/home/mariana/IdeaProjects/Server/Sum.jar");
                    FileOutputStream os = new FileOutputStream(jarFile);
                    os.write(bytes);
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    System.out.println("Could not create jar file");
                }
                break;
            }
            case JAR_SUB: {
                operationsArray[1] = true;
                try {
                    byte[] bytes = Base64.getDecoder().decode(msg.getText());
                    File jarFile = new File("/home/mariana/IdeaProjects/Server/Sub.jar");
                    FileOutputStream os = new FileOutputStream(jarFile);
                    os.write(bytes);
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    System.out.println("Could not create jar file");
                }
                break;
            }
            case JAR_DIV: {
                operationsArray[2] = true;
                try {
                    byte[] bytes = Base64.getDecoder().decode(msg.getText());
                    File jarFile = new File("/home/mariana/IdeaProjects/Server/Div.jar");
                    FileOutputStream os = new FileOutputStream(jarFile);
                    os.write(bytes);
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    System.out.println("Could not create jar file");
                }
                break;
            }
            case JAR_MULT:
            {
                operationsArray[3] = true;
                try {
                    byte[] bytes = Base64.getDecoder().decode(msg.getText());
                    File jarFile = new File("/home/mariana/IdeaProjects/Server/Mult.jar");
                    FileOutputStream os = new FileOutputStream(jarFile);
                    os.write(bytes);
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    System.out.println("Could not create jar file");
                }
                break;
            }
            default:
                break;
        }
    }
    public static void main(String[] args) {
        Server myServer = new Server();
        myServer.assignFootprint();
        myServer.checkExistingJars();
        myServer.joinDataField();
        myServer.receiveFromNode();
    }
}
