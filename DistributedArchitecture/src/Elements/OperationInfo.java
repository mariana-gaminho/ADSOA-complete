package Elements;

public class OperationInfo {
    private int incrementalIndex;
    private String firstNumber;
    private String secondNumber;
    private String result;
    private String event;
    private int hasResult;

    public int getIncrementalIndex() {
        return incrementalIndex;
    }
    public String getFirstNumber() {
        return firstNumber;
    }
    public String getSecondNumber() {
        return secondNumber;
    }
    public String getResult() {
        return result;
    }
    public String getEvent() {
        return event;
    }
    public int getHasResult() { return hasResult; }

    public void setIncrementalIndex(int incrementalIndex) {
        this.incrementalIndex = incrementalIndex;
    }
    public void setFirstNumber(String firstNumber) {
        this.firstNumber = firstNumber;
    }
    public void setSecondNumber(String secondNumber) {
        this.secondNumber = secondNumber;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public void setEvent(String event) {
        this.event = event;
    }
    public void setHasResult(int hasResult) { this.hasResult = hasResult; }
}
