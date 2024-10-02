package ui;

import bo.Item;
import bo.UserHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/showItems")
public class ShowItemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hämta användarnamn
        String username = (String) request.getAttribute("name");

        // Hämta användarens items
        ArrayList<Item> myItems = UserHandler.getMyItems(username);
        request.setAttribute("myItems", myItems);

        // Skicka till en ny JSP-sida för att visa items
        RequestDispatcher dispatcher = request.getRequestDispatcher("showItems.jsp");
        dispatcher.forward(request, response);
    }
}
