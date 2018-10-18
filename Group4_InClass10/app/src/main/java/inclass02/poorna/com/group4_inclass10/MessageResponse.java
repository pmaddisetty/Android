package inclass02.poorna.com.group4_inclass10;

import java.util.ArrayList;

/**
 * Created by poorn on 4/6/2018.
 */

public class MessageResponse {
    ArrayList<Messages> messages;

    public ArrayList<Messages> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Messages> messages) {
        this.messages = messages;
    }

    public static class Messages{

        String user_fname, user_lname, message, created_at;
        int user_id, id;

        public String getUser_fname() {
            return user_fname;
        }

        public void setUser_fname(String user_fname) {
            this.user_fname = user_fname;
        }

        public String getUser_lname() {
            return user_lname;
        }

        public void setUser_lname(String user_lname) {
            this.user_lname = user_lname;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
