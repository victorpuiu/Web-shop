package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.implementation.DaoRepository;
import com.codecool.shop.model.Customer;
import com.codecool.shop.service.CustomerService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DaoRepository daoRepository = DaoRepository.getInstance();
        String email = req.getParameter("email");
        Customer customer = new Customer(req.getParameter("email"), req.getParameter("pw"));
        CustomerDao customerDao = daoRepository.getCustomerDao();
        CustomerService customerService = new CustomerService(customerDao);
        if (customerService.loginSuccess(customer)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", email);
            resp.sendRedirect(req.getContextPath() + "/");
        }else{
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
