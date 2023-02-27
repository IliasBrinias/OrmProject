package com.unipi.msc.ormproject.ORM;

import com.unipi.msc.ormproject.ORM.Anotations.Column;
import com.unipi.msc.ormproject.ORM.Anotations.Database;
import com.unipi.msc.ormproject.ORM.Anotations.PrimaryKey;
import com.unipi.msc.ormproject.ORM.Anotations.Table;
import com.unipi.msc.ormproject.ORM.Database.SQLiteDB;
import com.unipi.msc.ormproject.ORM.Enum.ColumnType;
import com.unipi.msc.ormproject.ORM.Interface.Db;
import com.unipi.msc.ormproject.ORM.Tool.LoadClasses;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class OrmInitializer {
    public static void init(){
        LoadClasses.findAllClassesUsingClassLoader().forEach(c->{
            Db database = classAnnotation(c.getAnnotations());
            if (database == null) return;
            for (Field f: c.getDeclaredFields()){
                database.appendField(fieldAnnotation(f.getAnnotations()));
            }
            database.runQuery();
        });
    }
    private static String fieldAnnotation(Annotation[] annotations) {
        Column column = null;
        PrimaryKey primaryKey = null;
        for (Annotation a:annotations) {
            if (a instanceof Column){
                column = (Column) a;
            }else if (a instanceof PrimaryKey){
                primaryKey = (PrimaryKey) a;
            }
        }
        if (column == null) return null;
        StringBuilder stringBuilderColumn = new StringBuilder();
        stringBuilderColumn.append(column.name());
        stringBuilderColumn.append(" ");
        stringBuilderColumn.append(column.type());
        if (primaryKey!=null){
            stringBuilderColumn.append(" ");
            stringBuilderColumn.append("PRIMARY KEY");
            if (column.type() == ColumnType.INTEGER){
                stringBuilderColumn.append(" AUTOINCREMENT");
            }
        }
        return stringBuilderColumn.toString();
    }

    private static Db classAnnotation(Annotation[] annotations){
        Db db = null;
        Database database = null;
        Table table = null;
        for (Annotation a:annotations) {
            if (a instanceof Database){
                database = (Database) a;

            }else if (a instanceof Table){
                table = (Table) a;
            }
        }
        if (database == null || table == null) return null;
        switch (database.dbType()){
            case DERBY -> {}
            case SQLITE -> db = new SQLiteDB(database.name());
            case H2 -> {}
        }

        if (db == null) return db;
        db.setTable(table.name());
        return db;
    }
}
