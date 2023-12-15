package com.blokdev.system.servlet;

import com.blokdev.system.dto.CreateUserDTO;
import com.blokdev.system.entity.Role;
import com.blokdev.system.exception.ValidationException;
import com.blokdev.system.service.UserService;
import com.blokdev.system.util.JspPathUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@MultipartConfig
@WebServlet("/create-user")
public class CreateUserServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());

        req.getRequestDispatcher(JspPathUtil.getPath("create-user"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDTO = CreateUserDTO.builder()
                .firstName(req.getParameter("first_name"))
                .lastName(req.getParameter("last_name"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .role(req.getParameter("role"))
                .image(req.getPart("image"))
                .build();

        try {
            userService.create(userDTO);
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getValidationErrorList());
        } finally {
            doGet(req, resp);
        }
    }
}
