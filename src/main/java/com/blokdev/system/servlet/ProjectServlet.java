package com.blokdev.system.servlet;

import com.blokdev.system.service.ProjectService;
import com.blokdev.system.util.JspPathUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/project")
public class ProjectServlet extends HttpServlet {
    private final ProjectService projectService = ProjectService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") == null) {
            req.setAttribute("projects", projectService.getAllProject());

            req.getRequestDispatcher(JspPathUtil.getPath("project-list"))
                    .forward(req, resp);
        } else {
            Long id = Long.valueOf(req.getParameter("id"));
            req.setAttribute("project", projectService.getProjectById(id));

            req.getRequestDispatcher(JspPathUtil.getPath("project"))
                    .forward(req, resp);
        }
    }
}
