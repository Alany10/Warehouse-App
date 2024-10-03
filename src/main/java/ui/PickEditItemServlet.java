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

@WebServlet("/pickEditItem")
public class PickEditItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Hämta alla objekt
        ArrayList<Item> items = UserHandler.getAllItems();
        session.setAttribute("items", items);

        RequestDispatcher dispatcher = request.getRequestDispatcher("pickEditItem.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Hämta itemId från formuläret
        String itemIdParam = request.getParameter("itemId");
        if (itemIdParam == null || itemIdParam.isEmpty()) {
            request.setAttribute("error", "No item selected for editing.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("pickEditItem.jsp");
            dispatcher.forward(request, response);
            return;
        }

        int itemId;
        try {
            itemId = Integer.parseInt(itemIdParam);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid item ID.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("pickEditItem.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Hämta objektet baserat på itemId
        Item item = UserHandler.getItemById(itemId); // Assumera att denna metod existerar
        if (item == null) {
            request.setAttribute("error", "Item not found.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("pickEditItem.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Spara objektets attribut i sessionen
        session.setAttribute("id", item.getId());
        session.setAttribute("name", item.getName());
        session.setAttribute("description", item.getDescription());
        session.setAttribute("price", item.getPrice());
        session.setAttribute("quantity", item.getBalance());
        session.setAttribute("category", item.getCategory());

        // Vidarebefordra till editItem.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("editItem.jsp");
        dispatcher.forward(request, response);
    }

}