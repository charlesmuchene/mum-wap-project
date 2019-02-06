package utility;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class TestPersistence {
    public static void main(String[] args) {

        Team team = new Team("Awesome");
        Location location = new Location(0.0, 0.0);
        User user = new User("Charles", "email", "phone", location, new ArrayList<>(Collections.singletonList(team)));
        Task task = new Task("Task", new Date(), Category.Unknown, user);

        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(task);
        session.getTransaction().commit();
        session.close();

    }
}
