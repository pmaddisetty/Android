package inclass02.poorna.com.group4_inclass12;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatThread implements Serializable {

    String title, uid,key;
    ChatMessage msgList;


    public ChatThread() {
        this.title = null;
        this.uid = null;
        this.msgList = null;
        this.key=null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public ChatMessage getMsgList() {
        return msgList;
    }

    public void setMsgList(ChatMessage msgList) {
        this.msgList = msgList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
