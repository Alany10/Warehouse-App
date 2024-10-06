package ui;

import bo.Role;
import bo.User;
import bo.UserHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hämta namnet och lösenordet från formuläret (JSP)

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Role role = Role.valueOf(request.getParameter("role"));

        // Kontrollera användarens uppgifter
        if (UserHandler.Login(username, password, role)) {
            // Användaren är giltig, skicka välkomstmeddelande
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("role", role);

            if (role.equals(Role.CUSTOMER)){
                RequestDispatcher dispatcher = request.getRequestDispatcher("dashboardCustomer.jsp");
                dispatcher.forward(request, response);
            }
            else if (role.equals(Role.ADMIN)){
                RequestDispatcher dispatcher = request.getRequestDispatcher("dashboardAdmin.jsp");
                dispatcher.forward(request, response);
            }
            else if (role.equals(Role.WAREHOUSE)){
                List<User> customersWithCarts = UserHandler.getAllCustomersWithCarts();
                session.setAttribute("customersWithCarts", customersWithCarts);

                RequestDispatcher dispatcher = request.getRequestDispatcher("dashboardWarehouse.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            // Användarnamn eller lösenord är felaktigt
            request.setAttribute("errorMessage", "Wrong username, password or role");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp"); // Återgå till formuläret
            dispatcher.forward(request, response);
        }
    }
}

