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

@WebServlet("/removeItem")
public class RemoveItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String username = (String) session.getAttribute("username");
        // Hämta alla objekt
        ArrayList<Item> MyItems = UserHandler.getMyItems(username);
        session.setAttribute("myItems", MyItems);

        // Skicka vidare till removeItem.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("removeItem.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        int userId = UserHandler.getUserByUsername(username).getId();
        int itemId = Integer.parseInt(request.getParameter("itemId"));

        // Ta bort objektet
        boolean success = UserHandler.removeItem(userId, itemId);

        // Redirecta tillbaka till removeItem.jsp med meddelande
        if (success) {
            response.sendRedirect("removeItem.jsp?message=Item removed successfully");
        } else {
            response.sendRedirect("removeItem.jsp?error=Could not remove item");
        }
    }
}
