package com.unipi.msc.ormproject.ORM;

import com.unipi.msc.ormproject.ORM.Anotations.Column;
import com.unipi.msc.ormproject.ORM.Anotations.Database;
import com.unipi.msc.ormproject.ORM.Anotations.PrimaryKey;
import com.unipi.msc.ormproject.ORM.Anotations.Table;
import com.unipi.msc.ormproject.ORM.Database.DerbyDatabase;
import com.unipi.msc.ormproject.ORM.Database.H2Database;
import com.unipi.msc.ormproject.ORM.Database.SQLiteIDatabase;
import com.unipi.msc.ormproject.ORM.Enum.ColumnType;
import com.unipi.msc.ormproject.ORM.Interface.IDatabase;
import com.unipi.msc.ormproject.ORM.Tool.LoadClasses;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class OrmInitializer {
    public static void init(){
        LoadClasses.dropDatabases();
        LoadClasses.findAllClassesUsingClassLoader().forEach(c->{
            IDatabase database = getDatabase(c.getAnnotations());
            if (database == null || c.getDeclaredFields().length == 0) return;
            for (Field f: c.getDeclaredFields()){
                addFields(database,f.getAnnotations());
            }
            database.createTable();
        });
    }

    /**
     * add field definition for the table creation
     * @param database
     * @param annotations
     */
    private static void addFields(IDatabase database, Annotation[] annotations) {
        StringBuilder stringBuilderColumn = new StringBuilder();
        Column column = null;
        PrimaryKey primaryKey = null;
        for (Annotation a:annotations) {
            if (a instanceof Column){
                column = (Column) a;
            }else if (a instanceof PrimaryKey){
                primaryKey = (PrimaryKey) a;
            }
        }
        if (column == null) return;
        stringBuilderColumn.append(column.name().toLowerCase())
                .append(" ")
                .append(database.getDatabaseDataType(column.type()));
        if (column.notNull()){
            stringBuilderColumn.append(" NOT NULL ");
        }
        if (primaryKey!=null){
            stringBuilderColumn.append(" ").append("PRIMARY KEY");
            if (column.type() == ColumnType.INTEGER){
                stringBuilderColumn.append(" ").append("AUTOINCREMENT");
            }
        }else if (column.unique()){
            stringBuilderColumn.append(" UNIQUE ");
        }
        database.appendField(stringBuilderColumn.toString());
    }

    /**
     * based on the annotations create the database instance and determine the table
     * @param annotations
     * @return
     */
    public static IDatabase getDatabase(Annotation[] annotations){
        IDatabase db = null;
        Database aDb = null;
        Table table = null;
        for (Annotation a:annotations) {
            if (a instanceof Database){
                aDb = (Database) a;

            }else if (a instanceof Table){
                table = (Table) a;
            }
        }
        if (aDb == null || table == null) return null;
        switch (aDb.dbType()){
            case DERBY  -> db = new DerbyDatabase("Databases/"+aDb.name());
            case SQLITE -> db = new SQLiteIDatabase("Databases/"+aDb.name());
            case H2     -> db = new H2Database("Databases/"+aDb.name());
        }
        if (db == null) return db;
        db.setTable(table.name());
        return db;
    }
}
