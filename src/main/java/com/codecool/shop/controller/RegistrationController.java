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
import java.io.IOException;


@WebServlet(urlPatterns = {"/registration"})

public class RegistrationController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("registration.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        DaoRepository daoRepository = DaoRepository.getInstance();
        Customer customer = new Customer(request.getParameter("name"), request.getParameter("email"), request.getParameter("pw"));
        if (request.getParameter("address") != null) {
            customer.setAddress(request.getParameter("address"));
        }
        if (request.getParameter("city") != null) {
            customer.setCity(request.getParameter("city"));
        }
        if (request.getParameter("state") != null) {
            customer.setState(request.getParameter("state"));
        }
        if (request.getParameter("zip") != null) {
            customer.setZipCode(request.getParameter("zip"));
        }
        CustomerDao customerDao = daoRepository.getCustomerDao();
        CustomerService customerService = new CustomerService(customerDao);
        if (customerService.registration(customer)) {
            resp.sendRedirect(request.getContextPath() + "/");
        }else{
            System.out.println("email is not available");
        }
    }
}
