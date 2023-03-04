package com.unipi.msc.ormproject.ORM.Database;

import com.unipi.msc.ormproject.ORM.Anotations.Column;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Database {
    private final String databaseUrl;
    private final String dbName;
    private final List<String> fields = new ArrayList<>();

    public Database(String databaseUrl, String dbName) {
        this.databaseUrl = databaseUrl;
        this.dbName = dbName;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDbName() {
        return dbName;
    }

    public List<String> getFields() {
        return fields;
    }
    public String getFormattedFields(){
        StringBuilder stringBuilderFields = new StringBuilder();
        String lastItem = getFields().get(getFields().size()-1);
        getFields().forEach(f->{
            if (f == null) return;
            stringBuilderFields.append(f);
            if (lastItem.equals(f)) return;
            stringBuilderFields.append(" , ");
        });
        return stringBuilderFields.toString();
    }
    public Object setObjectFields(Class c, ResultSet rs) throws SQLException, IllegalAccessException, InstantiationException {
        Object o = c.newInstance();
        for (Field f : o.getClass().getDeclaredFields()) {
            Annotation fieldAnnotation = Arrays.stream(f.getAnnotations()).filter(annotation -> annotation instanceof Column).findFirst().orElse(null);
            if (fieldAnnotation == null) continue;
            f.setAccessible(true);
            switch (f.getType().getSimpleName()){
                case "String" -> f.set(o,rs.getString(f.getName().toLowerCase()));
                case "int", "Integer" -> f.set(o,rs.getInt(f.getName().toLowerCase()));
                case "boolean", "Boolean" -> f.set(o,rs.getBoolean(f.getName().toLowerCase()));
            }
        }
        return o;
    }
}
