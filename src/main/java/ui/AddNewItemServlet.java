package ui;

import bo.Category;
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


@WebServlet("/addNewItem")
public class AddNewItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hämta data från formuläret
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Category category = Category.valueOf(request.getParameter("category").toUpperCase());

        if (UserHandler.addNewItem(name, description, price, quantity, category)) {
            // Omdirigera med framgångsmeddelande
            response.sendRedirect("addNewItem.jsp?success=Item added successfully!");
        } else {
            // Omdirigera med felmeddelande
            response.sendRedirect("addNewItem.jsp?error=Item already exists!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Omdirigera GET-anrop till JSP-sidan
        response.sendRedirect("addNewItem.jsp");
    }
}