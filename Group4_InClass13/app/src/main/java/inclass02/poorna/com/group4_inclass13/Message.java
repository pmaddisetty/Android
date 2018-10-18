package inclass02.poorna.com.group4_inclass13;

import java.io.Serializable;

public class Message implements Serializable{

    String senderid,msgcontent,dateandtime,sender,msgkey;
    boolean isRead;

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getMsgcontent() {
        return msgcontent;
    }

    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getMsgkey() {
        return msgkey;
    }

    public void setMsgkey(String msgkey) {
        this.msgkey = msgkey;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderid='" + senderid + '\'' +
                ", msgcontent='" + msgcontent + '\'' +
                ", dateandtime='" + dateandtime + '\'' +
                ", sender='" + sender + '\'' +
                ", msgkey='" + msgkey + '\'' +
                ", isRead=" + isRead +
                '}';
    }
}
