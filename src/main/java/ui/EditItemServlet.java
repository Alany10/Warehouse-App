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

@WebServlet("/editItem")
public class EditItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Category category = Category.valueOf(request.getParameter("category"));

        session.setAttribute("id",id);
        session.setAttribute("name",name);
        session.setAttribute("description",description);
        session.setAttribute("price",price);
        session.setAttribute("quantity",quantity);
        session.setAttribute("category",category);

        RequestDispatcher dispatcher = request.getRequestDispatcher("pickEditItem.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Category category = Category.valueOf(request.getParameter("category").toUpperCase());

        if (UserHandler.editItem(id, name, description, price, quantity, category)) {
            response.sendRedirect("editItem.jsp?message=Item updated successfully!");
        } else {
            response.sendRedirect("editItem.jsp?error=Failed to update item!");
        }
    }
}
