package inclass02.poorna.com.group4_inclass10;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by poorn on 4/5/2018.
 */

public class ThreadResponse implements Serializable {
    ArrayList<MessageThread> threads;

    public ArrayList<MessageThread> getThreads() {
        return threads;
    }

    public void setThreads(ArrayList<MessageThread> threads) {
        this.threads = threads;
    }

    public static class MessageThread {
        String user_fname,user_lname,title,created_at;
        int user_id,id;

        @Override
        public String toString() {
            return "MessageThread{" +
                    "user_fname='" + user_fname + '\'' +
                    ", user_lname='" + user_lname + '\'' +
                    ", title='" + title + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", user_id=" + user_id +
                    ", id=" + id +
                    '}';
        }

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
