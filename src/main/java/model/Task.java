package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task implements Model {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @Column(name = "due_date")
    private Date dueDate;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(name = "user_id")
    private int userId;
    private int priority;

    public Task() {
    }

    public Task(String name, Date dueDate, Category category, int priority) {
        this.name = name;
        this.dueDate = dueDate;
        this.category = category;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dueDate=" + dueDate +
                ", category=" + category +
                ", priority=" + priority +
                '}';
    }
}
