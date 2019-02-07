package utility;

import com.google.gson.Gson;
import model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilities class
 * <p>
 * Has commonly used operations
 */
public class Utilities {

    private static Gson gson = new Gson();
    private static SessionFactory sessionFactory;

    private Utilities() {
    }

    /**
     * Get session Factory
     *
     * @return {@link SessionFactory} instance
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Team.class);
            configuration.addAnnotatedClass(Task.class);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Location.class);
            configuration.addAnnotatedClass(Category.class);

            configuration.getProperties().list(System.out);

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());

            try {
                String databaseUrl = System.getenv("DATABASE_URL");
                if (databaseUrl == null) throw new IllegalArgumentException("Malformed database url");
                URI dbUri = new URI(databaseUrl);
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                Map<String, String> settings = new HashMap<>();
                settings.put("hibernate.connection.username", username);
                settings.put("hibernate.connection.password", password);
                settings.put("hibernate.connection.url", dbUrl);

                builder.applySettings(settings);
                System.out.println(settings);

            } catch (URISyntaxException exception) {
                throw new IllegalArgumentException("Malformed database url");
            }

            sessionFactory = configuration.buildSessionFactory(builder.build());

        }

        return sessionFactory;
    }

    /**
     * Send the given data as JSON
     *
     * @param data     Data to send
     * @param response {@link ServletResponse} instance
     * @throws IOException possibly thrown by response
     */
    public static void sendAsJSON(String data, ServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.write(data);
    }

    /**
     * Extract fetch method from {@link ServletRequest}
     *
     * @param request {@link ServletRequest} instance
     * @return {@link Pair} instance
     */
    public static Pair<Fetch, Integer> extractFetchMethod(ServletRequest request) {
        String id = request.getParameter("id");
        if (id == null) return new Pair<>(Fetch.ALL, null);

        try {
            int theId = Integer.parseInt(id);
            return new Pair<>(Fetch.BY_ID, theId);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            return new Pair<>(Fetch.BY_ID, Integer.MIN_VALUE);
        }

    }

    /**
     * Get json
     *
     * @param request {@link ServletRequest} instance
     * @return Json representation
     */
    public static <T> T getJson(ServletRequest request, Class<T> klass) {

        String json = gson.toJson(request.getParameterMap());
        /* Nerve-wracking hack!! */
        json = json.replace("[", "");
        json = json.replace("]", "");
        return gson.fromJson(json, klass);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
