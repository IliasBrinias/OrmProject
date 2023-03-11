package com.unipi.msc.ormproject.ORM.Database;

import com.unipi.msc.ormproject.ORM.Enum.ColumnType;
import com.unipi.msc.ormproject.ORM.Interface.IDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DerbyDatabase extends Database implements IDatabase {
    private String table;
    public DerbyDatabase(String dbName) {
        super("jdbc:derby:", dbName);
    }
    private Connection getConnection() throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(getDatabaseUrl()+getDbName()+";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    @Override
    public int createTable() {
        StringBuilder stringBuilderCreate = new StringBuilder();
        stringBuilderCreate.append("CREATE TABLE ")
                .append(table)
                .append(" ( ")
                .append(getFormattedFields())
                .append(" )");
        try {
            return runUpdateStatement(getConnection(),stringBuilderCreate.toString());
        } catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    @Override
    public void appendField(String line) {
        getFields().add(line);
    }
    @Override
    public void setTable(String table) {
        this.table = table;
    }
    @Override
    public String getDatabaseDataType(ColumnType columnType) {
        switch (columnType){
            case INTEGER -> {return "INTEGER";}
            case TEXT -> {return "VARCHAR(255)";}
            case BOOLEAN -> {return "BOOLEAN";}
        }
        return null;
    }
    @Override
    public List<Object> selectQuery(Class c) {
        List<Object> classData = new ArrayList<>();
        String query = "SELECT * FROM "+table;
        try {
            classData = getQueryResultToList(c,getConnection(),query);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return classData;
    }
    @Override
    public Object selectQuery(Class c, String whereCause) {
        Object o = null;
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append("SELECT * FROM ")
                .append(table)
                .append(" WHERE ")
                .append(whereCause);
        try {
            o = getQueryResultToObject(c,getConnection(),stringBuilderQuery.toString());
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }
    @Override
    public int deleteQuery(Class c, String whereCause) {
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append("DELETE FROM ")
                .append(table)
                .append(" WHERE ")
                .append(whereCause);
        try {
            return runUpdateStatement(getConnection(),stringBuilderQuery.toString());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    @Override
    public int save(Object o) {
        try{
            return runUpdateStatement(getConnection(),getInsertQuery(table,o));
        } catch (ClassNotFoundException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return -1;

    }
}
