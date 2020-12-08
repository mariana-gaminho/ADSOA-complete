import Elements.ContentCodes;
import Elements.Message;

import javax.management.monitor.Monitor;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Elements.ContentCodes.*;

public class Manager {
    public ObjectInputStream ois;
    public ObjectOutputStream oos;
    Integer minPort = 1025;
    Integer maxPort = 1035;
    Socket s;

    public void joinDataField() {
        boolean isAlive = false;
        Random r = new Random();
        while(!isAlive) {
            int randomPort = r.nextInt(maxPort - minPort) + minPort;
            try {
                s = new Socket("127.0.0.1", randomPort);
                isAlive = true;
                oos = new ObjectOutputStream(this.s.getOutputStream());
                ois = new ObjectInputStream(this.s.getInputStream());
                contactNode();
            } catch (Exception ignored) {
            }
        }
    }

    public void contactNode() {
        System.out.println("Contacting node at port " +  s.getPort() + ". Localport: " + s.getLocalPort());
        Message msg = new Message();
        msg.setCode(ContentCodes.NEW_SERVER);
        msg.setText(" Hello, I'm the manager ");
        this.sendMessage(msg);
    }

    public void sendMessage(Message msg) {
        System.out.println("------------------- SENT MESSAGE --------------------");
        System.out.println("Content code: " + msg.getCode());
        try {
            oos.writeObject(msg);
            System.out.println("Message status: Sent");
        } catch (IOException e) {
            System.out.println("Message status: Error. Not sent");
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------------");
    }

    public static String convertToBase64(String operationJar) {
        String jarPath = "/home/mariana/IdeaProjects/ManagerJars/";
        String file_base64 = "";
        FileInputStream fis = null;
        try {
            File file = new File(jarPath+operationJar+".jar");
            fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[3072]; // 3 kB
            file_base64 = "";
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    file_base64 += Base64.getEncoder().encodeToString(Arrays.copyOfRange(buf, 0, readNum));
                    //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                    System.out.println("Reading " + readNum + " bytes from file");
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return file_base64;
    }

    public void createJarMessage(String operation) {
        String jarName="";
        Message jarMsg = new Message();
        switch(operation) {
            case "1":
                jarName = "Sum";
                jarMsg.setCode(JAR_SUM);
                break;
            case "2":
                jarName = "Sub";
                jarMsg.setCode(JAR_SUB);
                break;
            case "3":
                jarName = "Mult";
                jarMsg.setCode(JAR_MULT);
                break;
            case "4":
                jarName = "Div";
                jarMsg.setCode(JAR_DIV);
                break;
            default:
                break;
        }
        String convertedFile = convertToBase64(jarName);
        jarMsg.setSendToEveryone(true);
        jarMsg.setText(convertedFile);
        jarMsg.setSenderFootprint("managerFootprint");
        jarMsg.setEvent("managerEvent");
        this.sendMessage(jarMsg);
    }

    public static void main(String[] args) {
        Manager myManager = new Manager();
        myManager.joinDataField();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        String operation = "";
        while(true) {
            System.out.println("Chose jar file from the following options:");
            System.out.println("1. Addition");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            try {
                operation = reader.readLine();
                myManager.createJarMessage(operation);
            } catch (IOException ex) {
                System.out.println("Error: could not read input");
            }
        }
    }
}
