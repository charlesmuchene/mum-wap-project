package repository;

import model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Collection;

/**
 * Task repository
 */
public class TaskRepository implements Repository {

    Collection<Task> getAllTasks() {
        return null;
    }

    /**
     * Get task with the given id
     *
     * @param id Task id to retrieve
     * @return {@link Task} instance
     */
    Task getTask(int id) {
        return retrieve(Task.class, id);
    }

}
