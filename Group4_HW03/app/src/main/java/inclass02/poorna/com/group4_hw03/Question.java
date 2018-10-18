package inclass02.poorna.com.group4_hw03;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by poorn on 2/16/2018.
 */

public class Question implements Serializable {
    public int index;
    public String question;
    public String url;
    public ArrayList<String> optlist= new ArrayList<>();
    public int answer;

    public Question(int index, String question, String url, ArrayList<String> alist, int option) {
        this.index = index;
        this.question = question;
        this.url = url;
        this.optlist = alist;
        this.answer = option;
    }

    @Override
    public String toString() {
        return "Question{" +
                "index=" + index +
                ", question='" + question + '\'' +
                ", url='" + url + '\'' +
                ", optlist=" + optlist +
                ", answer=" + answer +
                '}';
    }

    public Question() {

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<String> getOptlist() {
        return optlist;
    }

    public void setOptlist(ArrayList<String> optlist) {
        this.optlist = optlist;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
