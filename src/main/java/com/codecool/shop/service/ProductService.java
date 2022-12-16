package com.codecool.shop.service;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.List;

public class ProductService{
    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;

    public ProductService(ProductDao productDao, ProductCategoryDao productCategoryDao, SupplierDao supplierDao) {
        this.productDao = productDao;
        this.productCategoryDao = productCategoryDao;
        this.supplierDao = supplierDao;
    }

    public Product getProductDaoById(int productId){
        if (productId > 0) return productDao.find(productId);
        else return null;
    }

    public ProductCategory getProductCategory(int categoryId){
        return productCategoryDao.find(categoryId);
    }

    public List<Product> getProductsForCategory(int categoryId){
        if (categoryId > 0) {
            var category = productCategoryDao.find(categoryId);
            return productDao.getBy(category);
        } else return null;
    }

    public List<ProductCategory> getProductCategories() {
        return productCategoryDao.getAll();
    }

    public List<Supplier> getProductSuppliers(int categoryId) {
        List<Product> productsByCategory = getProductsForCategory(categoryId);
        return supplierDao.getBy(productsByCategory);
    }

    public List<Product> getAllProduct() {
        return productDao.getAll();
    }

    public List<Product> getProductsForSupplierInCategory(int categoryId, int supplierId) {
        List<Product> productsByCategory = getProductsForCategory(categoryId);
        var supplier = supplierDao.find(supplierId);
        return productDao.getBy(supplier, productsByCategory);
    }
}
