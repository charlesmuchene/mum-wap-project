package model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "teams")
public class Team implements Model {
    @Id @GeneratedValue
    private int id;
    private String name;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
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
}
