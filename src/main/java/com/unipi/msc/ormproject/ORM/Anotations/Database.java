package com.unipi.msc.ormproject.ORM.Anotations;

import com.unipi.msc.ormproject.ORM.Enum.DatabaseType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Database {
    String name();
    DatabaseType dbType();
}
