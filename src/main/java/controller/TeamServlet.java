package controller;

import com.google.gson.Gson;
import repository.TeamRepository;
import utility.Fetch;
import utility.Pair;
import utility.Utilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/TeamServlet")
public class TeamServlet extends HttpServlet {

    private TeamRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new TeamRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Pair<Fetch, Integer> pair = Utilities.extractFetchMethod(request);

        Object data = pair.getFirst() == Fetch.BY_ID ? repository.getTeam(pair.getSecond()) : repository.getAllTeams();
        String output = new Gson().toJson(data);

        Utilities.sendAsJSON(output, response);

    }
}
