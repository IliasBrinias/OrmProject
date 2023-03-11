package com.unipi.msc.ormproject;

import com.unipi.msc.ormproject.Entity.StudentDerby;
import com.unipi.msc.ormproject.Entity.StudentH2;
import com.unipi.msc.ormproject.Entity.StudentSQLite;
import com.unipi.msc.ormproject.ORM.OrmInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrmProjectApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(OrmProjectApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        OrmInitializer.init();
        System.out.println("---------------------------------------");
        System.out.println("        Welcome to Orm Project");
        System.out.println("---------------------------------------");
        sqliteTest();
        H2Test();
        derbyTest();
        System.out.println("---------------------------------------");
        System.out.println("                 Bye");
        System.out.println("---------------------------------------");
    }
    private void sqliteTest(){
        //        SQLite
        System.out.println("* SQLite");
        System.out.println("1) Save Object with id mpsp2221");
        StudentSQLite studentSQLite = new StudentSQLite("mpsp2221","mpsp2221@unipi.gr",1,"Ilias Brinias",false);
        System.out.println("Rows affected: "+studentSQLite.save());
        System.out.println("2) Get Object with id mpsp2221");
        System.out.println("- "+studentSQLite.getStudent("mpsp2221"));
        StudentSQLite studentSQLite2 = new StudentSQLite("mpsp2200","mpsp2200@unipi.gr",2,"Nikos Petrou",true);
        System.out.println("Rows affected: "+studentSQLite2.save());
        System.out.println("3) Get All Data");
        System.out.println(studentSQLite.getAllStudents());
        System.out.println("4) Delete Object with id mpsp2221");
        System.out.println("Rows affected: "+studentSQLite.deleteStudents("mpsp2221"));
        System.out.println("5) Get All Data after delete");
        System.out.println(studentSQLite.getAllStudents());
        System.out.println("----------------");
        System.out.println(" ");
    }
    private void H2Test(){
        //        H2
        System.out.println("----------------");
        System.out.println("* H2");
        System.out.println("1) Save Object with id mpsp2221");
        StudentH2 studentH2 = new StudentH2("mpsp2221","mpsp2221@unipi.gr",1,"Ilias Brinias",false);
        System.out.println("Rows affected: "+studentH2.save());
        System.out.println("2) Get Object with id mpsp2221");
        System.out.println("- "+studentH2.getStudent("mpsp2221"));
        StudentH2 studentH21 = new StudentH2("mpsp2200","mpsp2200@unipi.gr",2,"Nikos Petrou",true);
        System.out.println("Rows affected: "+studentH21.save());
        System.out.println("3) Get All Data");
        System.out.println(studentH2.getAllStudents());
        System.out.println("4) Delete Object with id mpsp2221");
        System.out.println("Rows affected: "+studentH2.deleteStudents("mpsp2221"));
        System.out.println("5) Get All Data after delete");
        System.out.println(studentH2.getAllStudents());
        System.out.println("----------------");
        System.out.println(" ");
    }
    private void derbyTest(){
        //        DERBY
        System.out.println("----------------");
        System.out.println("* Derby");
        System.out.println("1) Save Object with id mpsp2221");
        StudentDerby studentDerby = new StudentDerby("mpsp2221","mpsp2221@unipi.gr",1,"Ilias Brinias",false);
        System.out.println("Rows affected: "+studentDerby.save());
        System.out.println("2) Get Object with id mpsp2221");
        System.out.println("- "+studentDerby.getStudent("mpsp2221"));
        StudentDerby studentDerby1 = new StudentDerby("mpsp2200","mpsp2200@unipi.gr",2,"Nikos Petrou",true);
        System.out.println("Rows affected: "+studentDerby1.save());
        System.out.println("3) Get All Data");
        System.out.println(studentDerby1.getAllStudents());
        System.out.println("4) Delete Object with id mpsp2221");
        System.out.println("Rows affected: "+studentDerby.deleteStudents("mpsp2221"));
        System.out.println("5) Get All Data after delete");
        System.out.println(studentDerby.getAllStudents());
    }
}
