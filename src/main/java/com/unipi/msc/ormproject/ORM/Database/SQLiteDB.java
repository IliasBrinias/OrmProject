package com.unipi.msc.ormproject.ORM.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.unipi.msc.ormproject.ORM.Interface.Db;
import org.sqlite.SQLiteConfig;
public class SQLiteDB implements Db {
    private static final String DATABASE_URL = "jdbc:sqlite:"+System.getProperty("user.dir")+"/";
    private final String DBName;
    public static final String DRIVER = "org.sqlite.JDBC";
    private String table = null;
    private List<String> fields = new ArrayList<>();

    public SQLiteDB(String dbName) {
        this.DBName = dbName;
    }

    private Connection getConnection() throws ClassNotFoundException {
        Class.forName(DRIVER);
        Connection connection = null;
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(DATABASE_URL+DBName,config.toProperties());
        } catch (SQLException ignore) {}
        return connection;
    }
    public void runQuery(){
        StringBuilder stringBuilderCreate = new StringBuilder();
        stringBuilderCreate.append("CREATE TABLE IF NOT EXISTS ").append(table).append(" (");
        stringBuilderCreate.append(" {FIELDS} ");
        stringBuilderCreate.append(");");

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
        fields.add(line);
    }

    @Override
    public void setTable(String table) {
        this.table = table;
    }
}
