package com.codecool.shop.config;

import com.codecool.shop.service.ErrorLogging;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {

    private static Properties conProps;

    public static void setupApplication() {
//        String configFileName = "connection.properties";

//        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
//        String conConfigPath = rootPath + configFileName;
        String conConfigPath ="/Users/danutpuiu/Documents/codecool/code cool/oop/Week Pair 6/Codecool shop (sprint 2)/codecool-shop-2-java-victorpuiu/src/main/resources/connection.properties";


        conProps = new Properties();
        try {
            conProps.load(new FileInputStream(conConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
            ErrorLogging.log(e);
        }
    }

    public static Properties getConProps() {
        return conProps;
    }
}
