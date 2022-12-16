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

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DaoRepository daoRepository = DaoRepository.getInstance();

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        ProductDao productDataStore = daoRepository.getProductDao();
        ProductCategoryDao productCategoryDataStore = daoRepository.getProductCategoryDao();
        SupplierDao supplierDataStore = daoRepository.getSupplierDao();
        ProductService productService = new ProductService(productDataStore,productCategoryDataStore, supplierDataStore);

        HttpSession session = req.getSession();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        if(session.getAttribute("user") != null){
            String userEmail = (String) session.getAttribute("user");
            context.setVariable("user", userEmail);
        }
        context.setVariable("products", productService.getAllProduct());
        context.setVariable("categories", productService.getProductCategories());
        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());
    }

}
