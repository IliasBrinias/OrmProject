package com.unipi.msc.ormproject.ORM.Anotations;

import com.unipi.msc.ormproject.ORM.Enum.ColumnType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String name() default "";
    ColumnType type() default ColumnType.TEXT;
    boolean notNull() default false;
    boolean unique() default false;
}
