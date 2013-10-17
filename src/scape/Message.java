package scape;

/*
 * ALERT! this class is not complete, but serves as a starting point.
 * 
 * These messages should be inspired by the FIPA agent communication language.
 */
public class Message {

    public enum Content {

        WHAT_IS_PRICE,  // cfp (call for proposal)
        PRICE_IS,       // propose
        ACCEPT_PRICE,   // accept-proposal
        REJECT_PRICE,   // reject-proposal
    }
    private Agent sender;
    private Content content;
    private String what;
    private int number = 0;

    public Message(Agent sender, Content content) {
        this.content = content;
        this.sender = sender;
    }

    public Message(Agent sender, Content content, String what) {
        this.content = content;
        this.sender = sender;
        this.what = what;
    }

    public Message(Agent sender, Content content, String what, int number) {
        this.number = number;
        this.content = content;
        this.sender = sender;
        this.what = what;
    }

    public String what() {
        return what;
    }

    public Content content() {
        return content;
    }

    public Agent sender() {
        return sender;
    }

    public int number() {
        return number;
    }
}
