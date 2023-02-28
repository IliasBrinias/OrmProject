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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class OrmInitializer {
    public static void init(){
        LoadClasses.findAllClassesUsingClassLoader().forEach(c->{
            IDatabase database = classAnnotation(c.getAnnotations());
            if (database == null) return;
            for (Field f: c.getDeclaredFields()){
                fieldAnnotation(database,f.getAnnotations());
            }
            database.runQuery();
        });
    }
    private static void fieldAnnotation(IDatabase database,Annotation[] annotations) {
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
        StringBuilder stringBuilderColumn = new StringBuilder();
        stringBuilderColumn.append(column.name().toLowerCase())
                .append(" ")
                .append(database.getDatabaseDataType(column.type()));
        if (primaryKey!=null){
            stringBuilderColumn.append(" ").append("PRIMARY KEY");
            if (column.type() == ColumnType.INTEGER){
                stringBuilderColumn.append(" ").append("AUTOINCREMENT");
            }
        }
        database.appendField(stringBuilderColumn.toString());
    }

    private static IDatabase classAnnotation(Annotation[] annotations){
        IDatabase IDatabase = null;
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
            case DERBY  -> IDatabase = new DerbyDatabase(database.name());
            case SQLITE -> IDatabase = new SQLiteIDatabase(database.name());
            case H2     -> IDatabase = new H2Database(database.name());
        }

        if (IDatabase == null) return IDatabase;
        IDatabase.setTable(table.name());
        return IDatabase;
    }
}
