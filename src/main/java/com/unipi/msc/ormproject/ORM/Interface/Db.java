package com.unipi.msc.ormproject.ORM.Interface;

import java.util.ArrayList;
import java.util.List;

public interface Db {
    void runQuery();
    void appendField(String line);
    void setTable(String table);
}
