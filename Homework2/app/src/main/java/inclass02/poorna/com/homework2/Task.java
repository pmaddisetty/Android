package inclass02.poorna.com.homework2;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by poorn on 1/29/2018.
 */

public class Task implements Serializable{
    String title;
    Date date;
    Date time;
    Date dateandtime;
    String priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(Date dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Task(String title, Date date, Date time, Date dateandtime, String priority) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.dateandtime = dateandtime;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", dateandtime=" + dateandtime +
                ", priority='" + priority + '\'' +
                '}';
    }
}
