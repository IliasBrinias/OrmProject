package com.unipi.msc.ormproject.Entity;

import com.unipi.msc.ormproject.ORM.Anotations.Database;
import com.unipi.msc.ormproject.ORM.Anotations.Table;
import com.unipi.msc.ormproject.ORM.Enum.DatabaseType;

@Database(name="DERBY", dbType = DatabaseType.SQLITE)
@Table(name="StudentV2")
public class StudentV2 {
}
