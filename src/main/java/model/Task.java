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
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    private int priority;

    public Task() {
    }

    public Task(String name, Date dueDate, Category category, User user, int priority) {
        this.name = name;
        this.dueDate = dueDate;
        this.category = category;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
