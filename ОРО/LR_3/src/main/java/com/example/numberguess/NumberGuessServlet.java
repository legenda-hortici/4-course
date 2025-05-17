package com.example.numberguess;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/guess")
public class NumberGuessServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (request.getParameter("min") != null && request.getParameter("max") != null) {
            try {
                int min = Integer.parseInt(request.getParameter("min"));
                int max = Integer.parseInt(request.getParameter("max"));
                if (min >= max) {
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
                session.setAttribute("min", min);
                session.setAttribute("max", max);
                session.setAttribute("guess", (min + max) / 2);
                request.getRequestDispatcher("/game.jsp").forward(request, response);// форвард на жсп без изменений
            } catch (NumberFormatException e) {
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            return;
        }

        int min = (int) session.getAttribute("min");
        int max = (int) session.getAttribute("max");
        int currentGuess = (int) session.getAttribute("guess");
        String answer = request.getParameter("answer");

        if ("equals".equals(answer)) {
            request.getRequestDispatcher("/win.jsp").forward(request, response);
        } else if ("greater".equals(answer)) {
            min = currentGuess + 1;
            if (min > max) {
                request.getRequestDispatcher("/cheat.jsp").forward(request, response);
                return;
            }
            session.setAttribute("min", min);
            session.setAttribute("guess", (min + max) / 2);
            request.getRequestDispatcher("/game.jsp").forward(request, response);
        } else if ("less".equals(answer)) {
            max = currentGuess - 1;
            if (max < min) {
                request.getRequestDispatcher("/cheat.jsp").forward(request, response);
                return;
            }
            session.setAttribute("max", max);
            session.setAttribute("guess", (min + max) / 2);
            request.getRequestDispatcher("/game.jsp").forward(request, response);
        }
    }
}