package com.unipi.msc.ormproject.ORM.Interface;

import com.unipi.msc.ormproject.ORM.Enum.ColumnType;

import java.util.List;

public interface IDatabase {
    void createTable();
    void appendField(String line);
    void setTable(String table);
    String getDatabaseDataType(ColumnType columnType);

    List<?> selectQuery(Class c);
    Object selectQuery(Class c, String whereCause);
    int deleteQuery(Class c, String whereCause);
}
