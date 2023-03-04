package com.unipi.msc.ormproject;

import com.unipi.msc.ormproject.Entity.Student;
import com.unipi.msc.ormproject.ORM.OrmInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class OrmProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrmProjectApplication.class, args);
        OrmInitializer.init();
        Student student = new Student();
        List<Student> studentList = student.getAllStudents();
        Student student1 = student.getStudent("P18112");
//        int row = student.deleteStudents("P18113");

        Student st = studentList.get(0);
    }

}
