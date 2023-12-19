package com.blokdev.system.servlet;

import com.blokdev.system.dto.CreateProjectDTO;
import com.blokdev.system.exception.ValidationException;
import com.blokdev.system.service.ProjectService;
import com.blokdev.system.util.JspPathUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/create-project")
public class CreateProjectServlet extends HttpServlet {
    private final ProjectService projectService = ProjectService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspPathUtil.getPath("create-project"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var createProjectDTO = CreateProjectDTO.builder()
                .name(req.getParameter("name"))
                .description(req.getParameter("project_description"))
                .startDate(req.getParameter("project_start_date"))
                .build();

        try {
            projectService.createProject(createProjectDTO);
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getValidationErrorList());
        } finally {
            doGet(req, resp);
        }
    }
}
