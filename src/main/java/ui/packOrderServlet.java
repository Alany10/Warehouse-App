package ui;

import bo.User;
import bo.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/packOrder")
public class packOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("dashboardWarehouse.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hämta customerId från JSP-sidan
        String customerIdParam = request.getParameter("customerId");

        if (customerIdParam != null && !customerIdParam.isEmpty()) {
            int customerId = Integer.parseInt(customerIdParam);

            // Anropa packOrder-metoden från UserHandler
            boolean success = UserHandler.packOrder(customerId);

            if (success) {
                // Om packOrder lyckades, hämta den uppdaterade listan
                List<User> updatedCustomersWithCarts = UserHandler.getAllCustomersWithCarts();
                request.getSession().setAttribute("customersWithCarts", updatedCustomersWithCarts);
                request.setAttribute("message", "Order packed successfully for customer");
            } else {
                // Om packOrder misslyckades (t.ex. användaren inte hittades)
                request.setAttribute("error", "Failed to pack order for customer");
            }
        }

        // Anropa doGet för att uppdatera listan
        doGet(request, response);
    }

}
