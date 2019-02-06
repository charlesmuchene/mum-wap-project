package model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User implements Model {

    @Id @GeneratedValue
    private int id;
    private String name;
    private String email;
    private String phone;
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Team> teams;

    public User() {
    }

    public User(String name, String email, String phone, Location location, Collection<Team> teams) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.teams = teams;
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

    public Collection<Team> getTeams() {
        return teams;
    }

    public void setTeams(Collection<Team> teams) {
        this.teams = teams;
    }
}
