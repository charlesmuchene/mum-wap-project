package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue
    private int id;
    private String task;
    @Column(name = "due_date")
    private Date dueDate;
    @Enumerated(EnumType.STRING)
    private Category category;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public Task() {
    }

    public Task(String task, Date dueDate, Category category, User user) {
        this.task = task;
        this.dueDate = dueDate;
        this.category = category;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getCategory() {
        return category.name();
    }

    public void setCategory(String category) {
        Category cat;
        try {
            cat = Category.valueOf(category);
        } catch (IllegalArgumentException exception) {
            cat = Category.Unknown;
        }
        this.category = cat;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
