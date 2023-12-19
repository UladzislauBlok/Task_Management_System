package com.blokdev.system.servlet;

import com.blokdev.system.dto.CreateTaskDTO;
import com.blokdev.system.exception.InvalidURLException;
import com.blokdev.system.exception.ValidationException;
import com.blokdev.system.service.TaskService;
import com.blokdev.system.util.JspPathUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/create-task")
public class CreateTaskServlet extends HttpServlet {
    private final TaskService taskService = TaskService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var projectId = req.getParameter("projectId");

        if (projectId == null) {
            throw new InvalidURLException();
        }

        req.setAttribute("projectId", projectId);

        req.getRequestDispatcher(JspPathUtil.getPath("create-task"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var createTaskDTO = CreateTaskDTO.builder()
                .name(req.getParameter("name"))
                .description(req.getParameter("description"))
                .projectId(Long.valueOf(req.getParameter("projectId")))
                .build();

        try {
            taskService.create(createTaskDTO);
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getValidationErrorList());
        } finally {
            doGet(req, resp);
        }
    }
}
