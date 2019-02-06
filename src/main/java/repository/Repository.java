package repository;

import model.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

/**
 * Base repository contract
 */
public interface Repository {

    SessionFactory factory = new Configuration().configure().buildSessionFactory();

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
     * Retrieve all
     *
     * @param klass Class of model
     * @return {@link List} of {@link T}
     */
    @SuppressWarnings("unchecked")
    default <T> List<T> retrieveAll(Class<T> klass) {
        List<?> list = performTask(session -> {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<?> criteriaQuery = criteriaBuilder.createQuery(klass);
            criteriaQuery.from(klass);
            return session.createQuery(criteriaQuery).getResultList();
        });
        return (List<T>) list;
    }

    /**
     * Perform the given task
     *
     * @param task {@link model.Task} to perform
     * @return {@link T} instance
     */
    default <T> T performTask(PersistenceTask<T> task) {
        Session session = factory.openSession();
        session.beginTransaction();
        T result = task.perform(session);
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
