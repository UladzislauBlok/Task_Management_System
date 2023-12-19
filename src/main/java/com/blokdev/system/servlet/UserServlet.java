package com.blokdev.system.servlet;

import com.blokdev.system.service.UserService;
import com.blokdev.system.util.JspPathUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") == null) {
            req.setAttribute("users", userService.getAllUsers());

            req.getRequestDispatcher(JspPathUtil.getPath("user-list"))
                    .forward(req, resp);
        } else {
            Long userId = Long.valueOf(req.getParameter("id"));
            req.setAttribute("user", userService.getUserById(userId));

            req.getRequestDispatcher(JspPathUtil.getPath("project"))
                    .forward(req, resp);
        }
    }
}
