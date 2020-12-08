import Elements.ContentCodes;
import Elements.Message;
import Elements.OperationInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static Elements.ContentCodes.*;

public class Client extends javax.swing.JFrame {
    public ObjectInputStream ois;
    public ObjectOutputStream oos;

    String operator;
    String number1, number2, result;
    int operationType;
    String footprint;
    String expecting;
    int NumOpr;
    int port = 1;
    Socket s;
    int minPort = 1025;
    int maxPort = 1035;
    boolean band =  true;
    ArrayList<OperationInfo> sumsArray = new ArrayList<>();
    ArrayList<OperationInfo> subsArray = new ArrayList<>();
    ArrayList<OperationInfo> multsArray = new ArrayList<>();
    ArrayList<OperationInfo> divsArray = new ArrayList<>();
    Message pendingOperation;

    public Client() {
        assignFootprint();
        initComponents();
        joinDataField();
    }

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

    private void initComponents() {
        Font largeFont = new Font("Consolas", Font.BOLD,16);
        Font smallFont = new Font("Consolas", Font.BOLD,14);
        screenInput = new javax.swing.JTextField();
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        JButton button3 = new JButton();
        JButton button4 = new JButton();
        JButton button5 = new JButton();
        JButton button6 = new JButton();
        JButton button7 = new JButton();
        JButton button8 = new JButton();
        JButton button9 = new JButton();
        JButton button10 = new JButton();
        JButton button11 = new JButton();
        JButton button12 = new JButton();
        JButton button13 = new JButton();
        JButton button14 = new JButton();
        JButton button15 = new JButton();
        JButton button16 = new JButton();
        JLabel label1 = new JLabel("Addition");
        label1.setFont(smallFont);
        completeSum = new JTextArea(5, 5);
        JLabel label2 = new JLabel("Subtraction");
        label2.setFont(smallFont);
        completeSub = new JTextArea(5, 5);
        JLabel label3 = new JLabel("Multiplication");
        label3.setFont(smallFont);
        completeMult = new JTextArea(5, 5);
        JLabel label4 = new JLabel("Division");
        label4.setFont(smallFont);
        completeDiv = new JTextArea(5, 5);
        JLabel emptyLabel = new JLabel("\n");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        button1.setBackground(new java.awt.Color(233, 30, 99));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setText("1");
        button1.setFont(largeFont);
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        button2.setBackground(new java.awt.Color(233, 30, 99));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("2");
        button2.setFont(largeFont);
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        button3.setBackground(new java.awt.Color(233, 30, 99));
        button3.setForeground(new java.awt.Color(255, 255, 255));
        button3.setText("3");
        button3.setFont(largeFont);
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        button4.setBackground(new java.awt.Color(233, 30, 99));
        button4.setForeground(new java.awt.Color(255, 255, 255));
        button4.setText("4");
        button4.setFont(largeFont);
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        button5.setBackground(new java.awt.Color(233, 30, 99));
        button5.setForeground(new java.awt.Color(255, 255, 255));
        button5.setText("5");
        button5.setFont(largeFont);
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        button6.setBackground(new java.awt.Color(233, 30, 99));
        button6.setForeground(new java.awt.Color(255, 255, 255));
        button6.setText("6");
        button6.setFont(largeFont);
        button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        button7.setBackground(new java.awt.Color(233, 30, 99));
        button7.setForeground(new java.awt.Color(255, 255, 255));
        button7.setText("7");
        button7.setFont(largeFont);
        button7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        button8.setBackground(new java.awt.Color(233, 30, 99));
        button8.setForeground(new java.awt.Color(255, 255, 255));
        button8.setText("8");
        button8.setFont(largeFont);
        button8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        button9.setBackground(new java.awt.Color(233, 30, 99));
        button9.setForeground(new java.awt.Color(255, 255, 255));
        button9.setText("9");
        button9.setFont(largeFont);
        button9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        button10.setBackground(new java.awt.Color(233, 30, 99));
        button10.setForeground(new java.awt.Color(255, 255, 255));
        button10.setText("0");
        button10.setFont(largeFont);
        button10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        button11.setBackground(new java.awt.Color(38, 91, 121));
        button11.setForeground(new java.awt.Color(255, 255, 255));
        button11.setText("AC");
        button11.setFont(largeFont);
        button11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        button12.setBackground(new java.awt.Color(38, 91, 121));
        button12.setForeground(new java.awt.Color(255, 255, 255));
        button12.setText("=");
        button12.setFont(largeFont);
        button12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        button13.setBackground(new java.awt.Color(38, 91, 121));
        button13.setForeground(new java.awt.Color(255, 255, 255));
        button13.setText("+");
        button13.setFont(largeFont);
        button13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        button14.setBackground(new java.awt.Color(38, 91, 121));
        button14.setForeground(new java.awt.Color(255, 255, 255));
        button14.setText("*");
        button14.setFont(largeFont);
        button14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        button15.setBackground(new java.awt.Color(38, 91, 121));
        button15.setForeground(new java.awt.Color(255, 255, 255));
        button15.setText("-");
        button15.setFont(largeFont);
        button15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        button16.setBackground(new java.awt.Color(38, 91, 121));
        button16.setForeground(new java.awt.Color(255, 255, 255));
        button16.setText("/");
        button16.setFont(largeFont);
        button16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        completeSum.setBackground(Color.white);
        completeSub.setBackground(Color.white);
        completeMult.setBackground(Color.white);
        completeDiv.setBackground(Color.white);

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Calculator");
        frame.setPreferredSize(new Dimension(500, 850));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Define the panel to hold the buttons
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Put constraints on different buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        panel.add(screenInput, gbc);

        // First section
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(button1, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(button2, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(button3, gbc);
        // AC button
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(button11, gbc);
        // Equals button
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(button12, gbc);

        // Second section
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(button4, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(button5, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(button6, gbc);

        // Third section
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(button7, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(button8, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(button9, gbc);
        // Plus and mult symbols
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(button13, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(button14, gbc);

        // Fourth section
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(button10, gbc);
        // Minus and div symbols
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(button15, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(button16, gbc);

        // Labels section
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(emptyLabel, gbc);
        // SUM
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(label1, gbc);
        // ---
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        panel.add(emptyLabel, gbc);
        // ----
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.ipady = 40;
        gbc.gridwidth = 6;
        panel.add(completeSum, gbc);
        // SUB
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        panel.add(label2, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.ipady = 40;
        gbc.gridwidth = 6;
        panel.add(completeSub, gbc);
        // MULT
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        panel.add(label3, gbc);
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.ipady = 40;      //make this component tall
        gbc.gridwidth = 6;
        panel.add(completeMult, gbc);
        // DIV
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 1;
        panel.add(label4, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.ipady = 40;
        gbc.gridwidth = 6;
        panel.add(completeDiv, gbc);

        // Set the window to be visible as the default to be false
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        screenInput.setText(screenInput.getText()+"1");
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        screenInput.setText(screenInput.getText()+"2");
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        screenInput.setText(screenInput.getText()+"3");
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        screenInput.setText(screenInput.getText()+"4");
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        screenInput.setText(screenInput.getText()+"5");
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
        screenInput.setText(screenInput.getText()+"6");
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        screenInput.setText(screenInput.getText()+"7");
    }

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {
        screenInput.setText(screenInput.getText()+"8");
    }

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {
        screenInput.setText(screenInput.getText()+"9");
    }

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {
        screenInput.setText(screenInput.getText()+"0");
    }

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {
        screenInput.setText("");
        completeSum.setText("");
        completeSub.setText("");
        completeMult.setText("");
        completeDiv.setText("");
    }

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {
        NumOpr = NumOpr + 1;
        number2 =  screenInput.getText();
        if (!number2.equals("")) {
            createMessage(number1, number2);
            receiveResult();
            screenInput.setText("");
        }
    }

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {
        if (!screenInput.getText().equals("")) {
            number1 =  screenInput.getText();
            operator = "+";
            operationType = 1;
            screenInput.setText("");
        }
    }

    private void jButton14ActionPerformed(ActionEvent evt) {
        if (!screenInput.getText().equals("")) {
            number1 =  screenInput.getText();
            operator = "*";
            operationType = 3;
            screenInput.setText("");
        }
    }

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {
        if (!screenInput.getText().equals("")) {
            number1 =  screenInput.getText();
            operator = "-";
            operationType = 2;
            screenInput.setText("");
        }
    }

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {
        if (!screenInput.getText().equals("")) {
            number1 =  screenInput.getText();
            operator = "/";
            operationType = 4;
            screenInput.setText("");
        }
    }

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
        msg.setCode(ContentCodes.NEW_CLIENT);
        msg.setSenderFootprint(this.footprint);
        msg.setText("Hello, I'm a client");
        this.sendMessage(msg);
    }

    public void receiveResult(){
        while(band) {
            Message incomingMsg;
            try{
                incomingMsg = (Message) ois.readObject();
                if(
                        (incomingMsg.getSenderFootprint().equals(this.footprint)
                        && incomingMsg.getEvent().equals(String.valueOf(this.expecting)))
                        || incomingMsg.getSenderFootprint().equals("managerFootprint")
                )
                {
                    switch (incomingMsg.getCode())
                    {
                        case RESULT_SUM: {
                            System.out.println("------------------- INBOX MESSAGE -------------------");
                            System.out.println("Content code: " + incomingMsg.getCode());
                            System.out.println("Footprint: " + incomingMsg.getSenderFootprint());
                            System.out.println("Result: " + incomingMsg.getResult());
                            result = String.valueOf(incomingMsg.getResult());
                            completeSum.append(incomingMsg.getN1() + "+"
                                    + incomingMsg.getN2() + "=" + incomingMsg.getResult() + "\n");
                            pendingOperation=null;
                            OperationInfo sum = new OperationInfo();
                            sum.setFirstNumber(String.valueOf(incomingMsg.getN1()));
                            sum.setSecondNumber(String.valueOf(incomingMsg.getN2()));
                            sum.setResult(String.valueOf(incomingMsg.getResult()));
                            sum.setIncrementalIndex(sumsArray.size());
                            sum.setEvent(String.valueOf(String.valueOf(incomingMsg.getEvent())));
                            this.sumsArray.add(sum);
                            for (OperationInfo o: sumsArray) {
                                System.out.println(o.getIncrementalIndex() + " | " + o.getFirstNumber()
                                        + "," + o.getSecondNumber()
                                        + " | " + o.getResult() + " | " + o.getEvent());
                            }
                            result=null;
                            band = !band;
                            break;
                        }
                        case RESULT_SUB: {
                            System.out.println("------------------- INBOX MESSAGE -------------------");
                            System.out.println("Content code: " + incomingMsg.getCode());
                            System.out.println("Footprint: " + incomingMsg.getSenderFootprint());
                            System.out.println("Result: " + incomingMsg.getResult());
                            result = String.valueOf(incomingMsg.getResult());
                            completeSub.append(incomingMsg.getN1() + "-"
                                    + incomingMsg.getN2() + "=" + incomingMsg.getResult() + "\n");
                            pendingOperation=null;
                            OperationInfo sub = new OperationInfo();
                            sub.setFirstNumber(String.valueOf(incomingMsg.getN1()));
                            sub.setSecondNumber(String.valueOf(incomingMsg.getN2()));
                            sub.setResult(String.valueOf(incomingMsg.getResult()));
                            sub.setIncrementalIndex(subsArray.size());
                            sub.setEvent(String.valueOf(expecting));
                            this.subsArray.add(sub);
                            for (OperationInfo o: subsArray) {
                                System.out.println(o.getIncrementalIndex() + " | " + o.getFirstNumber()
                                        + "," + o.getSecondNumber()
                                        + " | " + o.getResult() + " | " + o.getEvent());
                            }
                            result=null;
                            band = !band;
                            break;
                        }
                        case RESULT_MULT: {
                            System.out.println("------------------- INBOX MESSAGE -------------------");
                            System.out.println("Content code: " + incomingMsg.getCode());
                            System.out.println("Footprint: " + incomingMsg.getSenderFootprint());
                            System.out.println("Result: " + incomingMsg.getResult());
                            result = String.valueOf(incomingMsg.getResult());
                            completeMult.append(incomingMsg.getN1() + "*"
                                    + incomingMsg.getN2() + "=" + incomingMsg.getResult() + "\n");
                            pendingOperation=null;
                            OperationInfo mult = new OperationInfo();
                            mult.setFirstNumber(String.valueOf(incomingMsg.getN1()));
                            mult.setSecondNumber(String.valueOf(incomingMsg.getN2()));
                            mult.setResult(String.valueOf(incomingMsg.getResult()));
                            mult.setIncrementalIndex(multsArray.size());
                            mult.setEvent(String.valueOf(expecting));
                            this.multsArray.add(mult);
                            for (OperationInfo o: multsArray) {
                                System.out.println(o.getIncrementalIndex() + " | " + o.getFirstNumber()
                                        + "," + o.getSecondNumber()
                                        + " | " + o.getResult() + " | " + o.getEvent());
                            }
                            result=null;
                            band = !band;
                            break;
                        }
                        case RESULT_DIV:
                        {
                            System.out.println("------------------- INBOX MESSAGE -------------------");
                            System.out.println("Content code: " + incomingMsg.getCode());
                            System.out.println("Footprint: " + incomingMsg.getSenderFootprint());
                            System.out.println("Result: " + incomingMsg.getResult());
                            result = String.valueOf(incomingMsg.getResult());
                            completeDiv.append(incomingMsg.getN1() + "/"
                                    + incomingMsg.getN2() + "=" + incomingMsg.getResult() + "\n");
                            pendingOperation=null;
                            OperationInfo div = new OperationInfo();
                            div.setFirstNumber(String.valueOf(incomingMsg.getN1()));
                            div.setSecondNumber(String.valueOf(incomingMsg.getN2()));
                            div.setResult(String.valueOf(incomingMsg.getResult()));
                            div.setIncrementalIndex(divsArray.size());
                            div.setEvent(String.valueOf(expecting));
                            this.divsArray.add(div);
                            for (OperationInfo o: divsArray) {
                                System.out.println(o.getIncrementalIndex() + " | " + o.getFirstNumber()
                                        + "," + o.getSecondNumber()
                                        + " | " + o.getResult() + " | " + o.getEvent());
                            }
                            result=null;
                            band = !band;
                            break;
                        }
                        case NO_RESULT:
                        {
                            System.out.println("The server doesn't have the necessary jar file");
                            ActionListener resendMessage = new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    band = true;
                                    sendMessage(pendingOperation);
                                    receiveResult();
                                }
                            };
                            Timer timer = new Timer(3000, resendMessage);
                            timer.start();
                            timer.setRepeats(false);
                            band = !band;
                            break;
                        }
                        default:
                            break;
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
            }
        }
    }

    public String createEvent(String footprint, String n1, String n2) {
        String uniqueIdentifier = String.valueOf(UUID.randomUUID());
        String beforeHash = footprint + n1 + n2 + uniqueIdentifier;
        String hash = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(beforeHash.getBytes(StandardCharsets.UTF_8));
            hash = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
        }
        return hash;
    }

    public void createMessage(String number1, String number2){
        Message operationMsg = new Message();
        switch (operationType)
        {
            case 1:
                operationMsg.setSenderFootprint(footprint);
                operationMsg.setCode(SUM);
                operationMsg.setSendToEveryone(true);
                operationMsg.setText("+");
                operationMsg.setN1(Integer.parseInt(number1));
                operationMsg.setN2(Integer.parseInt(number2));
                break;
            case 2:
                operationMsg.setSenderFootprint(footprint);
                operationMsg.setCode(SUB);
                operationMsg.setSendToEveryone(true);
                operationMsg.setText("-");
                operationMsg.setN1(Integer.parseInt(number1));
                operationMsg.setN2(Integer.parseInt(number2));
                break;
            case 3:
                operationMsg.setSenderFootprint(footprint);
                operationMsg.setCode(MULT);
                operationMsg.setSendToEveryone(true);
                operationMsg.setText("*");
                operationMsg.setN1(Integer.parseInt(number1));
                operationMsg.setN2(Integer.parseInt(number2));
                break;
            case 4:
                operationMsg.setSenderFootprint(footprint);
                operationMsg.setCode(DIV);
                operationMsg.setSendToEveryone(true);
                operationMsg.setText("/");
                operationMsg.setN1(Integer.parseInt(number1));
                operationMsg.setN2(Integer.parseInt(number2));
                break;
            default:
        }
        band = true;
        this.expecting = createEvent(footprint, number1, number2);
        System.out.println("Message event: " + expecting);
        operationMsg.setEvent(expecting);
        pendingOperation=operationMsg;
        this.sendMessage(operationMsg);
    }

    public void sendMessage(Message msg) {
        System.out.println("------------------- SENT MESSAGE --------------------");
        System.out.println("Sender footprint: " + msg.getSenderFootprint());
        System.out.println("Content code: " + msg.getCode());
        System.out.println("Event: " + msg.getEvent());
        System.out.println("Message: " + msg.getN1() + msg.getText() + msg.getN2());
        try {
            oos.writeObject(msg);
            System.out.println("Message status: Sent");
        } catch (IOException e) {
            System.out.println("Message status: Error. Not sent");
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------------");
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // Create and display the form
        java.awt.EventQueue.invokeLater(() -> new Client().setVisible(true));
    }

    private javax.swing.JTextField screenInput;
    private javax.swing.JTextArea completeSum;
    private javax.swing.JTextArea completeSub;
    private javax.swing.JTextArea completeMult;
    private javax.swing.JTextArea completeDiv;
}