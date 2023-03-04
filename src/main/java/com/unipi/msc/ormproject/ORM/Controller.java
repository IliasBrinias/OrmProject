package com.unipi.msc.ormproject.ORM;

import com.unipi.msc.ormproject.ORM.Anotations.DBMethod;
import com.unipi.msc.ormproject.ORM.Anotations.PrimaryKey;
import com.unipi.msc.ormproject.ORM.Enum.Query;
import com.unipi.msc.ormproject.ORM.Interface.IDatabase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Controller {
    public static List<?> runQuery(Class c){
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[2];
        IDatabase database = OrmInitializer.getDatabase(c.getAnnotations());
        if (database == null) return null;
        Method m = Arrays.stream(c.getMethods()).filter(method -> method.getName().equals(stackTrace.getMethodName())).findFirst().orElse(null);
        if (m == null) return null;
        Annotation dbMethod = Arrays.stream(m.getAnnotations()).filter(annotation -> annotation instanceof DBMethod).findFirst().orElse(null);
        if (dbMethod == null) return null;
        return database.selectQuery(c);
    }
    public static Object runQuery(Class c, String am) {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[2];
        IDatabase database = OrmInitializer.getDatabase(c.getAnnotations());
        if (database == null) return null;
        Method m = Arrays.stream(c.getMethods()).filter(method -> method.getName().equals(stackTrace.getMethodName())).findFirst().orElse(null);
        if (m == null) return null;
        Annotation dbMethod = Arrays.stream(m.getAnnotations()).filter(annotation -> annotation instanceof DBMethod).findFirst().orElse(null);
        if (dbMethod == null) return null;
        Field f = Arrays.stream(c.getDeclaredFields()).filter(field -> Arrays.stream(field.getAnnotations()).anyMatch(annotation -> annotation instanceof PrimaryKey)).findFirst().orElse(null);
        if (f == null) return null;
        if (((DBMethod) dbMethod).type() == Query.SELECT){
            return database.selectQuery(c,f.getName().toLowerCase()+" = '"+am+"'");
        }else if (((DBMethod) dbMethod).type() == Query.DELETE){
            return database.deleteQuery(c,f.getName().toLowerCase()+" = '"+am+"'");
        }
        return null;
    }
}
