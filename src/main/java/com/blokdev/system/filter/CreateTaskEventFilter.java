package com.blokdev.system.filter;

import com.blokdev.system.dto.UserDTO;
import com.blokdev.system.service.TaskService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/create-task-event")
public class CreateTaskEventFilter implements Filter {
    private final TaskService taskService = TaskService.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var user = (UserDTO) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        var taskId = Long.valueOf(servletRequest.getParameter("taskId"));
        var task = taskService.getTaskById(taskId);

        if (user.getProject().getId().equals(task.getProjectId())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            var mainPage = ((HttpServletRequest) servletRequest).getContextPath() + "/";
            ((HttpServletResponse) servletResponse).sendRedirect(mainPage);
        }
    }
}
