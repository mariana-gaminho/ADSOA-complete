package Elements;

public class Message implements java.io.Serializable {
    private String code;
    private boolean sendToEveryone;
    private String senderFootprint;
    private String event;
    private String text;
    private int firstNumber;
    private int secondNumber;
    private int result;

    public Message()
    {
        this.setSendToEveryone(false);
    }

    // Unique identifier
    public String getSenderFootprint()
    {
        return senderFootprint;
    }
    public void setSenderFootprint(String senderFootprint)
    {
        this.senderFootprint = senderFootprint;
    }

    // Message text (body)
    public String getText()
    {
        return text;
    }
    public void setText(String Text)
    {
        this.text = Text;
    }

    // Message content code
    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    // Message unique identifier (helps the client determine if the message is for him or not)
    public String getEvent()
    {
        return event;
    }
    public void setEvent(String event)
    {
        this.event = event;
    }

    // These methods help us know if we should send the message to everyone (when it comes from client or server)
    // Node messages shouldn't be sent to everyone to avoid falling into an infinite loop
    public boolean getSendToEveryone()
    {
        return sendToEveryone;
    }
    public void setSendToEveryone(boolean isItForEveryone)
    {
        this.sendToEveryone = isItForEveryone;
    }

    // First number for operations
    public int getN1()
    {
        return firstNumber;
    }
    public void setN1(int firstNumber)
    {
        this.firstNumber = firstNumber;
    }

    // Second number for operations
    public int getN2() {
        return secondNumber;
    }
    public void setN2(int secondNumber)
    {
        this.secondNumber = secondNumber;
    }

    // Result of operation
    public int getResult() {
        return result;
    }
    public void setResult(int result) {
        this.result = result;
    }
}
