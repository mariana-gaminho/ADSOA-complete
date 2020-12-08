package Ops;

import Elements.ContentCodes;
import Elements.Message;

public class Mult {
    Message reply;
    public Message multNumbers(int n1, int n2, String footprint, String event, String text) {
        reply = new Message();
        reply.setN1(n1);
        reply.setN2(n2);
        reply.setSenderFootprint(footprint);
        reply.setEvent(event);
        reply.setResult(n1*n2);
        reply.setText(text);
        reply.setSendToEveryone(true);
        reply.setCode(ContentCodes.RESULT_MULT);
        System.out.println("------------------- SENT MESSAGE --------------------");
        System.out.println("Content code: " + ContentCodes.RESULT_MULT);
        System.out.println("Message: " + text);
        System.out.println("-----------------------------------------------------");
        return reply;
    }
}