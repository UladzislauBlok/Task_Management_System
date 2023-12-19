package com.blokdev.system.servlet;

import com.blokdev.system.exception.InvalidURLException;
import com.blokdev.system.service.TaskService;
import com.blokdev.system.util.JspPathUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/task")
public class TaskServlet extends HttpServlet {
    private final TaskService taskService = TaskService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var projectId = req.getParameter("projectId");

        if (projectId == null) {
            throw new InvalidURLException();
        }

        req.setAttribute("task", taskService.getTaskById(Long.valueOf(projectId)));
        req.getRequestDispatcher(JspPathUtil.getPath("task"))
                .forward(req, resp);
    }
}
