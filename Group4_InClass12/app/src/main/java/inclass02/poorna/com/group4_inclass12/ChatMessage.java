package inclass02.poorna.com.group4_inclass12;

public class ChatMessage {

    String uid, timecreated, msgcontent,key,name;

    public ChatMessage() {

    }

    public ChatMessage(String uid, String timecreated, String msgcontent) {
        this.uid = uid;
        this.timecreated = timecreated;
        this.msgcontent = msgcontent;
        this.name=name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(String timecreated) {
        this.timecreated = timecreated;
    }

    public String getMsgcontent() {
        return msgcontent;
    }

    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
