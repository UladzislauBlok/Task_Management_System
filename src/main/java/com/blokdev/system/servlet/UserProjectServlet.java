package com.blokdev.system.servlet;

import com.blokdev.system.service.ProjectService;
import com.blokdev.system.service.UserService;
import com.blokdev.system.util.JspPathUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user-project")
public class UserProjectServlet extends HttpServlet {
    private final ProjectService projectService = ProjectService.getInstance();
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var projects = projectService.getAllProject();
        var users = userService.getAllUsersWithoutProject();

        req.setAttribute("projects", projects);
        req.setAttribute("users", users);

        req.getRequestDispatcher(JspPathUtil.getPath("user-project"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userId = Long.valueOf(req.getParameter("user"));
        var projectId = Long.valueOf(req.getParameter("project"));

        userService.addUserToProject(userId, projectId);

        doGet(req, resp);
    }
}
