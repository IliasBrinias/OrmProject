package com.unipi.msc.ormproject.ORM.Database;

import com.unipi.msc.ormproject.ORM.Enum.ColumnType;
import com.unipi.msc.ormproject.ORM.Interface.IDatabase;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DerbyDatabase implements IDatabase {
    private static final String DATABASE_URL = "jdbc:derby:";
    public static final String DRIVER = "org.apache.derby.jdbc.EmbededDriver";
    private final String DBName;
    private String table;
    private List<String> fields = new ArrayList<>();

    public DerbyDatabase(String dbName) {
        DBName = dbName;
    }
    private Connection getConnection() throws ClassNotFoundException {
//        Class.forName(DRIVER);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL+DBName+";create=true");
        } catch (SQLException ignore) {
            ignore.printStackTrace();
        }
        return connection;
    }

    @Override
    public void runQuery() {
        StringBuilder stringBuilderCreate = new StringBuilder();
        stringBuilderCreate.append("CREATE TABLE ").append(table).append(" (");
        stringBuilderCreate.append(" {FIELDS} ");
        stringBuilderCreate.append(")");

        StringBuilder stringBuilderFields = new StringBuilder();
        String lastItem = fields.get(fields.size()-1);
        fields.forEach(f->{
            if (f == null) return;
            stringBuilderFields.append(f);
            if (lastItem.equals(f)) return;
            stringBuilderFields.append(",");
        });
        String table_query = stringBuilderCreate.toString().replace("{FIELDS}",stringBuilderFields.toString());
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(table_query);
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void appendField(String line) {
        this.fields.add(line);
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
}
