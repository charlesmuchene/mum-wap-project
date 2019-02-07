package model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User implements Model {

    @Id
    @GeneratedValue
    private int id;
    private int teamId;
    private String name;
    private String email;
    private String phone;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Location location;

    public User() {
    }

    public User(String name, String email, String phone, Location location) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
