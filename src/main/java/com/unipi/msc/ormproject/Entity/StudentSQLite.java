package com.unipi.msc.ormproject.Entity;

import com.unipi.msc.ormproject.ORM.Anotations.*;
import com.unipi.msc.ormproject.ORM.Controller;
import com.unipi.msc.ormproject.ORM.Enum.ColumnType;
import com.unipi.msc.ormproject.ORM.Enum.DatabaseType;
import com.unipi.msc.ormproject.ORM.Enum.Query;

import java.util.List;
@Database(name="UnipiSQLite", dbType = DatabaseType.SQLITE)
@Table(name="Student")
public class StudentSQLite {
    @PrimaryKey
    @Column(name="AM",type= ColumnType.TEXT, unique = true)
    private String AM;
    @Column(name="Email",type=ColumnType.TEXT, unique = true, notNull = true)
    private String email;
    @Column(type = ColumnType.INTEGER)
    private int yearOfStudies;
    @Column(name="FullName",type=ColumnType.TEXT)
    private String fullName;
    @Column(name="PostGraduate",type=ColumnType.BOOLEAN)
    boolean postGraduate;
    public StudentSQLite(){}
    public StudentSQLite(String AM, String email, int yearOfStudies, String fullName, boolean postGraduate) {
        this.AM = AM;
        this.email = email;
        this.yearOfStudies = yearOfStudies;
        this.fullName = fullName;
        this.postGraduate = postGraduate;
    }
    @DBMethod(type= Query.SELECT_ALL)
    public List<StudentSQLite> getAllStudents(){return (List<StudentSQLite>) Controller.runQuery(StudentSQLite.class);}
    @DBMethod(type= Query.SELECT)
    public StudentSQLite getStudent(String am){return (StudentSQLite) Controller.runQuery(StudentSQLite.class,am);}
    @DBMethod(type=Query.DELETE)
    public int deleteStudents(String am){
        return (int) Controller.runQuery(StudentSQLite.class,am);
    }
    @DBMethod(type=Query.SAVE)
    public int save(){
        return Controller.runQuery(this);
    }
    @Override
    public String toString() {
        return "Student{" +
                "AM='" + AM + '\'' +
                ", email='" + email + '\'' +
                ", yearOfStudies=" + yearOfStudies +
                ", fullName='" + fullName + '\'' +
                ", postGraduate=" + postGraduate +
                '}';
    }
}
