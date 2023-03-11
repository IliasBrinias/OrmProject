package com.unipi.msc.ormproject.ORM.Database;

import com.unipi.msc.ormproject.ORM.Anotations.Column;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    /**
     * Instantiate object class with field data
     * @param c
     * @param rs
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object assignFieldValueToObject(Class c, ResultSet rs) throws SQLException, IllegalAccessException, InstantiationException {
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

    /**
     * Insert query builder
     * @param tableName
     * @param o
     * @return
     * @throws IllegalAccessException
     */
    public String getInsertQuery(String tableName, Object o) throws IllegalAccessException {
        StringBuilder stringBuilderFields = new StringBuilder();
        StringBuilder stringBuilderValues = new StringBuilder();
        stringBuilderFields.append("INSERT INTO ").append(tableName.toUpperCase()).append(" (");
        stringBuilderValues.append(" VALUES (");
        Field lastField = o.getClass().getDeclaredFields()[o.getClass().getDeclaredFields().length-1];
        for (Field f:o.getClass().getDeclaredFields()){
            f.setAccessible(true);
            if (Arrays.stream(f.getAnnotations()).anyMatch(annotation -> annotation instanceof Column)){
                if (f.get(o) == null) continue;
                stringBuilderFields.append(f.getName().toLowerCase());
                if (f.getType().getSimpleName().equals("String")){
                    stringBuilderValues.append("'").append(f.get(o)).append("'");
                }else {
                    stringBuilderValues.append(f.get(o));
                }
                if (!lastField.getName().equals(f.getName())) {
                    stringBuilderFields.append(", ");
                    stringBuilderValues.append(", ");
                }
            }
        }
        stringBuilderFields.append(" )");
        stringBuilderValues.append(" )");
        stringBuilderFields.append(stringBuilderValues);
        String query = stringBuilderFields.toString();
        return query;
    }

    public void runExecuteStatement(Connection conn, String query) throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException ignore) {
            ignore.printStackTrace();
        }finally {
            conn.close();
        }
    }
    public int runUpdateStatement(Connection conn, String query) throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conn.close();
        }
        return -1;
    }

    /**
     * run the query and return the List of returned data
     * @param c
     * @param conn
     * @param query
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public List<Object> getQueryResultToList(Class c,Connection conn, String query) throws SQLException, IllegalAccessException, InstantiationException {
        List<Object> objectList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                objectList.add(assignFieldValueToObject(c,rs));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objectList;
    }

    /**
     * run the query and return an object of returned data
     * @param c
     * @param conn
     * @param query
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object getQueryResultToObject(Class c, Connection conn, String query) throws SQLException, IllegalAccessException, InstantiationException {
        Object o = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                o = assignFieldValueToObject(c, rs);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o;
    }
}
