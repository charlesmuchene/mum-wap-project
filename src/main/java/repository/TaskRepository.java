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

    /**
     * Get all tasks
     *
     * @return {@link Collection} of all tasks
     */
    public Collection<Task> getAllTasks() {
        return retrieveAll(Task.class);
    }

    /**
     * Get task with the given id
     *
     * @param id Task id to retrieve
     * @return {@link Task} instance
     */
    public Task getTask(int id) {
        return retrieve(Task.class, id);
    }

    public void saveTask(Task task) {
        persist(task);
    }

}
