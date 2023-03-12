package com.unipi.msc.ormproject.ORM.Database;

import com.unipi.msc.ormproject.ORM.Enum.ColumnType;
import com.unipi.msc.ormproject.ORM.Interface.IDatabase;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class SQLiteIDatabase extends Database implements IDatabase {
    private String table = null;
    public SQLiteIDatabase(String dbName) {
        super("jdbc:sqlite:"+System.getProperty("user.dir")+"/", dbName+".sqlite");
    }
    private Connection getConnection() throws ClassNotFoundException {
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            return DriverManager.getConnection(getDatabaseUrl()+getDbName(),config.toProperties());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int createTable(){
        StringBuilder stringBuilderCreate = new StringBuilder();
        stringBuilderCreate.append("CREATE TABLE IF NOT EXISTS ")
                .append(table)
                .append(" ( ")
                .append(getFormattedFields())
                .append(" );");
        try {
            return runUpdateStatement(getConnection(),stringBuilderCreate.toString());
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
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
            case TEXT -> {return "TEXT";}
            case BOOLEAN -> {return "BOOLEAN";}
        }
        return null;
    }
    @Override
    public List<Object> selectQuery(Class c) {
        String query = "SELECT * FROM "+table;
        try {
            return getQueryResultToList(c,getConnection(),query);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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
            return runUpdateStatement(getConnection(),stringBuilderQuery.toString());
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int save(Object o) {
        try{
            return runUpdateStatement(getConnection(),getInsertQuery(table,o));
        } catch (ClassNotFoundException | IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
