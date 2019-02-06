package controller;

import com.google.gson.Gson;
import model.Task;
import repository.TaskRepository;
import utility.Fetch;
import utility.Pair;
import utility.Utilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet {

    private TaskRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new TaskRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Task task = Utilities.getJson(request, Task.class);
        repository.saveTask(task);
        response.getWriter().print(Utilities.toJson(task));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Pair<Fetch, Integer> pair = Utilities.extractFetchMethod(request);

        Object data = pair.getFirst() == Fetch.BY_ID ? repository.getTask(pair.getSecond()) : repository.getAllTasks();
        String output = new Gson().toJson(data);

        Utilities.sendAsJSON(output, response);

    }
}
