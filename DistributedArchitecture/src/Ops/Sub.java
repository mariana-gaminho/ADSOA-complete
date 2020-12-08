package Ops;

import Elements.ContentCodes;
import Elements.Message;

public class Sub {
    Message reply;
    public Message subNumbers(int n1, int n2, String footprint, String event, String text) {
        reply = new Message();
        reply.setN1(n1);
        reply.setN2(n2);
        reply.setSenderFootprint(footprint);
        reply.setEvent(event);
        reply.setResult(n1-n2);
        reply.setText(text);
        reply.setSendToEveryone(true);
        reply.setCode(ContentCodes.RESULT_SUB);
        System.out.println("------------------- SENT MESSAGE --------------------");
        System.out.println("Content code: " + ContentCodes.RESULT_SUB);
        System.out.println("Message: " + text);
        System.out.println("-----------------------------------------------------");
        return reply;
    }
}
