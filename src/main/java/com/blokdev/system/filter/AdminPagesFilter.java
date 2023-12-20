package com.blokdev.system.filter;

import com.blokdev.system.dto.UserDTO;
import com.blokdev.system.entity.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/create-user", "/create-project", "/user-project"})
public class AdminPagesFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var user = (UserDTO) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        if (user.getRole().equals(Role.ADMIN)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            var mainPageURL = ((HttpServletRequest) servletRequest).getContextPath() + "/";
            ((HttpServletResponse) servletResponse).sendRedirect(mainPageURL);
        }
    }
}
