package com.codecool.shop.config;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.database.DatabaseManager;
import com.codecool.shop.dao.implementation.memory.*;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.service.ErrorLogging;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Properties;

@WebListener
public class Initializer implements ServletContextListener {

    private String dao;
    private String dbUserName;
    private String dbPassword;
    private String dbUrl;
    private String dbName;
    private static DatabaseManager databaseManager;

    @Override
    public void contextInitialized(ServletContextEvent sce){


        DatabaseConfig.setupApplication();
        Properties conProps = DatabaseConfig.getConProps();

        dao = conProps.getProperty("dao");
        dbUserName = conProps.getProperty("user");
        dbPassword = conProps.getProperty("password");
        dbUrl = conProps.getProperty("url");
        dbName = conProps.getProperty("database");

        try {
            if (dao.equals("memory")) {


                ProductDao productDataStore = ProductDaoMem.getInstance();
                ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
                SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
                CartDao cartDataStore = CartDaoMem.getInstance();
                ProductInCartDao productInCartDao = ProductInCartDaoMem.getInstance();

                Cart cart = new Cart(0);
                cartDataStore.add(cart);

                //setting up a new supplier
                Supplier jimShore = new Supplier("Jim Shore", "");
                supplierDataStore.add(jimShore);
                Supplier department56 = new Supplier("Department 56", "");
                supplierDataStore.add(department56);
                Supplier possibleDreams = new Supplier("Possible Dreams", "");
                supplierDataStore.add(possibleDreams);
                Supplier willowTree = new Supplier("Willow Tree", "");
                supplierDataStore.add(willowTree);
                Supplier fontanini = new Supplier("Fontanini", "");
                supplierDataStore.add(fontanini);
                Supplier christmasHeart = new Supplier("Christmas heart", "");
                supplierDataStore.add(christmasHeart);
                Supplier tails = new Supplier("Tails", "");
                supplierDataStore.add(tails);


                //setting up a new product category
                ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "");
                productCategoryDataStore.add(tablet);
                ProductCategory console = new ProductCategory("Console", "Hardware", "");
                productCategoryDataStore.add(console);
                ProductCategory cellPhone = new ProductCategory("Cell Phone", "Hardware", "");
                productCategoryDataStore.add(cellPhone);
                ProductCategory smartWatch = new ProductCategory("Smart Watch", "Hardware", "");
                productCategoryDataStore.add(smartWatch);
                ProductCategory computer = new ProductCategory("Computer", "Hardware", "");
                productCategoryDataStore.add(computer);
                ProductCategory camera = new ProductCategory("Camera", "Hardware", "");
                productCategoryDataStore.add(camera);
                ProductCategory television = new ProductCategory("Television", "Hardware", "");
                productCategoryDataStore.add(television);

                //setting up products and printing it
                productDataStore.add(new Product("ChuWi HeroBook Por+", new BigDecimal("812"), "USD", "", computer, jimShore));
                productDataStore.add(new Product("1Phone Smart Watch", new BigDecimal("580"), "USD", "", smartWatch, department56));
                productDataStore.add(new Product("Samsong Smart Watch", new BigDecimal("376"), "USD", "", smartWatch, department56));
                productDataStore.add(new Product("1Phone Fit Smart Watch", new BigDecimal("171"), "USD", "", smartWatch, fontanini));
                productDataStore.add(new Product("Samsong Tablet", new BigDecimal("412"), "USD", "", tablet, fontanini));
                productDataStore.add(new Product("Leioa Tablet", new BigDecimal("1353"), "USD", "", tablet, fontanini));
                productDataStore.add(new Product("1Phone Tablet", new BigDecimal("1792"), "USD", " ", tablet, willowTree));
                productDataStore.add(new Product("Game Station PXP", new BigDecimal("1270"), "USD", "", console, possibleDreams));
                productDataStore.add(new Product("Bintendo Retro Console", new BigDecimal("1631"), "USD", "", console, possibleDreams));
                productDataStore.add(new Product("Game Station PS1 Retro Console", new BigDecimal("1935"), "USD", "", console, possibleDreams));
                productDataStore.add(new Product("Bintendo Retro Hand Console", new BigDecimal("1556"), "USD", "", console, willowTree));
                productDataStore.add(new Product("Bintendo Switch", new BigDecimal("709"), "USD", "", console, willowTree));
                productDataStore.add(new Product("1Phone i13 Pro Max", new BigDecimal("2000"), "USD", "", cellPhone, christmasHeart));
                productDataStore.add(new Product("Samsong S30", new BigDecimal("1854"), "USD", ")", cellPhone, christmasHeart));
                productDataStore.add(new Product("Leioa P60 Pro", new BigDecimal("161"), "USD", "", cellPhone, christmasHeart));
                productDataStore.add(new Product("Samsong S21 U+", new BigDecimal("338"), "USD", "", cellPhone, tails));
                productDataStore.add(new Product("Samsong Note30 Plus", new BigDecimal("156"), "USD", "", cellPhone, tails));
                productDataStore.add(new Product("Game Station 4 pro", new BigDecimal("453"), "USD", "", console, tails));
                productDataStore.add(new Product("Fujigama TPT 16X Pro", new BigDecimal("862"), "USD", "", camera, jimShore));
                productDataStore.add(new Product("Fujigama ZMP 24X", new BigDecimal("108"), "USD", "", camera, jimShore));
                productDataStore.add(new Product("Samsong UHD Televison 65'", new BigDecimal("728"), "USD", "", television, department56));
                productDataStore.add(new Product("Samsong FULL HD Televison 45'", new BigDecimal("356"), "USD", "", television, christmasHeart));
            } else if (dao.equals("jdbc")) {
                databaseManager = new DatabaseManager(dbName, dbUserName, dbPassword);
                try {
                    databaseManager.setup();
                } catch (SQLException e){
                    e.printStackTrace();
                    ErrorLogging.log(e);
                }
            }
        }  catch (NullPointerException e) {
            ErrorLogging.log(e);
        }
            };

    // use this in controllers
    public static DatabaseManager getDatabaseManager() {
            return databaseManager;
        }

        }
