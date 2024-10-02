package ui;

import bo.UserHandler;
import bo.Item;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/addItem")
public class AddItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hämta username från sessionen
        HttpSession session = request.getSession();

        // Hämta alla objekt
        ArrayList<Item> items = UserHandler.getAllItems();

        session.setAttribute("items", items);

        // Skicka vidare till addItem.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("addItem.jsp");
        dispatcher.forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        int userId = UserHandler.getUserByUsername(username).getId();
        int itemId = Integer.parseInt(request.getParameter("itemId"));

        // Lägg till objektet
        boolean success = UserHandler.addItem(userId, itemId);

        // Redirecta tillbaka till dashboard eller annan sida
        if (success) {
            response.sendRedirect("dashboard.jsp?message=Item added successfully");
        } else {
            response.sendRedirect("addItem.jsp?error=Could not add item");
        }
    }
}
