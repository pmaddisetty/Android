package inclass02.poorna.com.listproj;

/**
 * Created by poorn on 2/25/2018.
 */

public class Email {
    String subject;
    String summary;
    String email;

    public Email(String subject, String summary, String email) {
        this.subject = subject;
        this.summary = summary;
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
