package com.blokdev.system.filter;

import com.blokdev.system.dto.UserDTO;
import com.blokdev.system.entity.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/create-task")
public class CreateTaskFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var user = (UserDTO) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        var projectId = servletRequest.getParameter("projectId");

        if ((user.getRole().equals(Role.PM) || user.getRole().equals(Role.TL)) &&
            user.getProject().getId().equals(Long.valueOf(projectId))) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            var mainPage = ((HttpServletRequest) servletRequest).getContextPath() + "/";
            ((HttpServletResponse) servletResponse).sendRedirect(mainPage);
        }
    }
}
