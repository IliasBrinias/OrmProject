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
    public void createTable() {
        StringBuilder stringBuilderCreate = new StringBuilder();
        stringBuilderCreate.append("CREATE TABLE ")
                .append(table)
                .append(" ( ")
                .append(getFormattedFields())
                .append(" )");
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(stringBuilderCreate.toString());
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                classData.add(setObjectFields(c,rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
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
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(stringBuilderQuery.toString());
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                o = setObjectFields(c,rs);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
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
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(stringBuilderQuery.toString());
            return statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
