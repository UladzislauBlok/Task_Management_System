package com.blokdev.system.servlet;

import com.blokdev.system.dto.CreateTaskEventDTO;
import com.blokdev.system.dto.UserDTO;
import com.blokdev.system.exception.InvalidURLException;
import com.blokdev.system.service.TaskEventService;
import com.blokdev.system.util.JspPathUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/create-task-event")
public class CreateTaskEventServlet extends HttpServlet {
    private static final String USER_NAME_PATTERN = "%s %s | %s";
    private final TaskEventService taskEventService = TaskEventService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var taskId = req.getParameter("taskId");

        if (taskId == null) {
            throw new InvalidURLException();
        }

        req.setAttribute("taskId", taskId);

        req.getRequestDispatcher(JspPathUtil.getPath("create-task-event"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (UserDTO) req.getSession().getAttribute("user");
        var createTaskDTO = CreateTaskEventDTO.builder()
                .taskId(Long.valueOf(req.getParameter("taskId")))
                .description(req.getParameter("description"))
                .eventUserName(USER_NAME_PATTERN.formatted(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()
                ))
                .build();

        taskEventService.create(createTaskDTO);
        doGet(req, resp);
    }
}
