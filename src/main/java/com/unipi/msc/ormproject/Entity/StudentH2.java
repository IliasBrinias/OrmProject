package com.unipi.msc.ormproject.Entity;

import com.unipi.msc.ormproject.ORM.Anotations.*;
import com.unipi.msc.ormproject.ORM.Controller;
import com.unipi.msc.ormproject.ORM.Enum.ColumnType;
import com.unipi.msc.ormproject.ORM.Enum.DatabaseType;
import com.unipi.msc.ormproject.ORM.Enum.Query;

import java.util.List;

@Database(name="UnipiH2", dbType = DatabaseType.H2)
@Table(name="Student")
public class StudentH2 {
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
    @DBMethod(type= Query.SELECT_ALL)
    public List<StudentH2> getAllStudents(){return (List<StudentH2>) Controller.runQuery(StudentH2.class);}
    @DBMethod(type= Query.SELECT)
    public StudentH2 getStudent(String am){
        return (StudentH2) Controller.runQuery(StudentH2.class,am);
    }
    @DBMethod(type=Query.DELETE)
    public int deleteStudents(String am){
        return (int) Controller.runQuery(StudentH2.class,am);
    }
    @DBMethod(type=Query.SAVE)
    public int save(){
        return Controller.runQuery(this);
    }

    public StudentH2() {
    }

    public StudentH2(String AM, String email, int yearOfStudies, String fullName, boolean postGraduate) {
        this.AM = AM;
        this.email = email;
        this.yearOfStudies = yearOfStudies;
        this.fullName = fullName;
        this.postGraduate = postGraduate;
    }

    @Override
    public String toString() {
        return "StudentH2{" +
                "AM='" + AM + '\'' +
                ", email='" + email + '\'' +
                ", yearOfStudies=" + yearOfStudies +
                ", fullName='" + fullName + '\'' +
                ", postGraduate=" + postGraduate +
                '}';
    }
}
