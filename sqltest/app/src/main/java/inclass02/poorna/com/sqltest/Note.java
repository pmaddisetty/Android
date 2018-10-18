package inclass02.poorna.com.sqltest;

public class Note {
    private long id;
    private String subject,text;

    public Note()
    {

    }
    public  Note(String subject, String text)
    {
        this.subject=subject;
        this.text=text;
    }
    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
