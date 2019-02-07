package utility;

import model.Task;

import java.util.ArrayList;

public class MockData {

    private ArrayList<Task> taskList = new ArrayList<>();

    public ArrayList<Task> retrieveTaskList() {

        taskList.add(new Task(101, "first task","2","2019-11-02", "Personal"));
        taskList.add(new Task(102, "second task","4","2019-11-19", "Personal"));
        taskList.add(new Task(103, "third task","5","2016-11-19", "Work"));
        taskList.add(new Task(104, "fourth task","none","2019-11-19", "Work"));

        return taskList;
    }

}


