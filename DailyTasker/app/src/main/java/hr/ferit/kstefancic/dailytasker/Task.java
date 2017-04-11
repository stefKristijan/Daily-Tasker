package hr.ferit.kstefancic.dailytasker;

/**
 * Created by Kristijan on 11.4.2017..
 */

public class Task {

    private String title, description, category, date;
    private int priority;

    public Task(String t, String desc, String cat, String date, int pr){
        this.title=t;
        this.description=desc;
        this.category=cat;
        this.priority=pr;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
