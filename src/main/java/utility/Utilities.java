package utility;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Utilities class
 * <p>
 * Has commonly used operations
 */
public class Utilities {

    private Utilities() {
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
}
