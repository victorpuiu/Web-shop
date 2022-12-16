package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.DaoRepository;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/tablet", "/console", "/smart watch", "/cell phone", "/computer", "/camera", "/television"})
public class ProductsByCategoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        DaoRepository daoRepository = DaoRepository.getInstance();

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        ProductDao productDataStore = daoRepository.getProductDao();
        ProductCategoryDao productCategoryDataStore = daoRepository.getProductCategoryDao();
        SupplierDao supplierDataStore = daoRepository.getSupplierDao();
        ProductService productService = new ProductService(productDataStore, productCategoryDataStore, supplierDataStore);

        String requestURI = request.getRequestURI();
        int categoryId = getCategoryId(requestURI);
        String supplier = request.getParameter("supplier");
        int supplierId = 0;
        if (supplier != null) {
            supplierId = Integer.parseInt(supplier);
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        setupContext(context, supplierId, productService, categoryId, session);

        engine.process("product/products.html", context, response.getWriter());
    }

    private int getCategoryId(String requestURI) {
        switch (requestURI) {
            case "/tablet":
                return 1;
            case "/console":
                return 2;
            case "/cell%20phone":
                return 3;
            case "/smart%20watch":
                return 4;
            case "/computer":
                return 5;
            case "/camera":
                return 6;
            case "/television":
                return 7;
        }
        return 0;
    }

    private void setupContext(WebContext context, int supplierId, ProductService productService, int categoryId, HttpSession session) {
        context.setVariable("categories", productService.getProductCategories());
        context.setVariable("suppliers", productService.getProductSuppliers(categoryId));
        context.setVariable("category", productService.getProductCategory(categoryId));
        if (supplierId == 0) {
            context.setVariable("products", productService.getProductsForCategory(categoryId));
        } else {
            context.setVariable("products", productService.getProductsForSupplierInCategory(categoryId, supplierId));
        }
        if (session.getAttribute("user") != null) {
            String user = session.getAttribute("user").toString();
            context.setVariable("user", user);
        }
    }
}
