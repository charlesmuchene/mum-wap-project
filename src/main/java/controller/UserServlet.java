package controller;

import com.google.gson.Gson;
import model.Task;
import model.User;
import repository.UserRepository;
import utility.Utilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * User servlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    private UserRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new UserRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<User> userList = repository.getAllUsers();
        String users = new Gson().toJson(userList);
        System.out.println(users);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.write(users);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = Utilities.getJson(request, User.class);
        repository.saveUser(user);
        response.getWriter().print(Utilities.toJson(user));
    }
}
