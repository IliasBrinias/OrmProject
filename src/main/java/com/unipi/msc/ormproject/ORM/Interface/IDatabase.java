package com.unipi.msc.ormproject.ORM.Interface;

import com.unipi.msc.ormproject.ORM.Enum.ColumnType;

public interface IDatabase {
    void runQuery();
    void appendField(String line);
    void setTable(String table);
    String getDatabaseDataType(ColumnType columnType);
}
