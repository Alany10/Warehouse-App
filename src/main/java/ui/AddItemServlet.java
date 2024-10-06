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

        // Hämta det valda itemId
        String itemIdParam = request.getParameter("itemId");

        // Kontrollera att itemIdParam inte är null eller tom
        if (itemIdParam == null || itemIdParam.isEmpty()) {
            // Om inget item valdes, omdirigera tillbaka med ett felmeddelande
            response.sendRedirect("addItem.jsp?error=No item selected");
            return; // Avbryt vidare bearbetning
        }

        int userId = UserHandler.getUserByUsername(username).getId();
        int itemId = Integer.parseInt(itemIdParam); // Konvertera till int efter kontroll

        // Lägg till objektet
        boolean success = UserHandler.addItem(userId, itemId);

        // Redirecta tillbaka till addItem.jsp med meddelande
        if (success) {
            response.sendRedirect("addItem.jsp?message=Item added successfully");
        } else {
            response.sendRedirect("addItem.jsp?error=Item already in shopping bag");
        }
    }

}
