package com.lab1;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/welcome")
public class HelloServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hämta namnet från formuläret (JSP)
        String name = request.getParameter("name");

        // Lagra namnet som en attribut och skicka det till JSP
        request.setAttribute("name", name);

        // Vidarebefordra till JSP-sidan som visar hälsningsmeddelandet
        RequestDispatcher dispatcher = request.getRequestDispatcher("greeting.jsp");
        dispatcher.forward(request, response);
    }
}