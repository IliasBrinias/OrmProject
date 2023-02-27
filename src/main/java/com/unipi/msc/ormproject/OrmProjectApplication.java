package com.unipi.msc.ormproject;

import com.unipi.msc.ormproject.ORM.OrmInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrmProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrmProjectApplication.class, args);
        OrmInitializer.init();
    }

}
