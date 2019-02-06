package repository;

import model.Task;

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

    /**
     * Save the given task
     *
     * @param task {@link Task} instance
     */
    public void saveTask(Task task) {
        persist(task);
    }

}
