package utility;

import model.*;
import repository.TaskRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class TestPersistence {
    public static void main(String[] args) {

        Team team = new Team("Awesome");
        Location location = new Location(0.0, 201.0);
        User user = new User("Charles", "email", "phone", location, new ArrayList<>(Collections.singletonList(team)));
        Task task = new Task("Task", new Date(), Category.Unknown, user, 3);

        TaskRepository taskRepository = new TaskRepository();
        System.out.println(task.getName());
        taskRepository.saveTask(task);
        task = taskRepository.getTask(1);
        System.out.println(task.getName());
        Collection<Task> allTasks = taskRepository.getAllTasks();
        allTasks.forEach(task2 -> System.out.println(task2.getUser().getLocation().getLongitude()));

    }
}
