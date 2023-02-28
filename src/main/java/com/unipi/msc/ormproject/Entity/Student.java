package com.unipi.msc.ormproject.Entity;

import com.unipi.msc.ormproject.ORM.Anotations.*;
import com.unipi.msc.ormproject.ORM.Enum.ColumnType;
import com.unipi.msc.ormproject.ORM.Enum.DatabaseType;

import java.util.List;
@Database(name="UnipiDERBY", dbType = DatabaseType.DERBY)
@Table(name="Student")
public class Student {
    @PrimaryKey
    @Column(name="AM",type= ColumnType.TEXT)
    String AM;
    @Column(name="Email",type=ColumnType.TEXT)
    String email;
    @Column(name="yearOfStudies",type=ColumnType.INTEGER)
    int yearOfStudies;
    @Column(name="FullName",type=ColumnType.TEXT)
    String fullName;
    @Column(name="PostGraduate",type=ColumnType.BOOLEAN)
    boolean postGraduate;
    //Για τη μέθοδο αυτή μπορείτε να δοκιμάστε να επιστρέφετε
//    List<Student>
    @DBMethod(type="SelectAll")
    public List<String> getAllStudents(){
        return null;
    }
    //Ο επιστρεφόμενος ακέραιος υποδηλώνει τον αριθμό των εγγραφών
//    που διαγράφηκαν
    @DBMethod(type="DeleteOne")
    public int deleteStudents(String AM){
        return 0;
    }
}
