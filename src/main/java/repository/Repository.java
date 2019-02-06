package repository;

import model.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;

/**
 * Base repository contract
 */
public interface Repository {

    /**
     * Persist the given model
     *
     * @param model {@link Model} instance
     */
    default void persist(Model model) {
        performTask(session -> session.save(model));
    }

    /**
     * Retrieve the model represented by the given id
     *
     * @param klass Class of the model
     * @param id    {@link Serializable} instance
     * @return {@link T} instance;
     */
    default <T> T retrieve(Class<T> klass, Serializable id) {
        return (T) performTask(session -> session.get(klass, id));
    }

    /**
     * Perform the given task
     *
     * @param task {@link model.Task} to perform
     * @return {@link T} instance
     */
    default <T> T performTask(PersistenceTask<T> task) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        T result = task.perform(session);
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
