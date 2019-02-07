package repository;

import org.hibernate.Session;

/**
 * Persistence task
 * <p>
 * Represents some work to be done on the persistence implementation
 */
@FunctionalInterface
public interface PersistenceTask<T> {

    /**
     * Perform task using the given session
     *
     * @param session {@link Session} instance
     * @return {@link T} instance
     */
    T perform(Session session);
}
