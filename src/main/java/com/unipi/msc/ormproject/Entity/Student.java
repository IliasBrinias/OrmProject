package com.unipi.msc.ormproject.Entity;

import com.unipi.msc.ormproject.ORM.Anotations.*;
import com.unipi.msc.ormproject.ORM.Controller;
import com.unipi.msc.ormproject.ORM.Enum.ColumnType;
import com.unipi.msc.ormproject.ORM.Enum.DatabaseType;
import com.unipi.msc.ormproject.ORM.Enum.Query;

import java.util.List;
@Database(name="UnipiDERBY", dbType = DatabaseType.DERBY)
@Table(name="Student")
public class Student {
    @PrimaryKey
    @Column(name="AM",type= ColumnType.TEXT, unique = true)
    private String AM;
    @Column(name="Email",type=ColumnType.TEXT, unique = true, notNull = true)
    private String email;
    @Column(name="yearOfStudies",type=ColumnType.INTEGER)
    private int yearOfStudies;
    @Column(name="FullName",type=ColumnType.TEXT)
    private String fullName;
    @Column(name="PostGraduate",type=ColumnType.BOOLEAN)
    boolean postGraduate;
    public String getAM() {return AM;}
    public String getEmail() {return email;}
    public int getYearOfStudies() {return yearOfStudies;}
    public String getFullName() {return fullName;}
    public boolean isPostGraduate() {return postGraduate;}
    @DBMethod(type= Query.SELECT_ALL)
    public List<Student> getAllStudents(){return (List<Student>) Controller.runQuery(Student.class);}
    @DBMethod(type= Query.SELECT)
    public Student getStudent(String am){
        return (Student) Controller.runQuery(Student.class,am);
    }
    @DBMethod(type=Query.DELETE)
    public int deleteStudents(String am){
        return (int) Controller.runQuery(Student.class,am);
    }
}
