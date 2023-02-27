package com.unipi.msc.ormproject.ORM.Tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class LoadClasses {
    private static String ENTITY_PACKAGE = "com.unipi.msc.ormproject.Entity";
    public static List<Class> findAllClassesUsingClassLoader() {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(ENTITY_PACKAGE.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(LoadClasses::getClass)
                .collect(Collectors.toList());
    }

    private static Class getClass(String className) {
        try {
            return Class.forName(ENTITY_PACKAGE + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException ignore) {}
        return null;
    }

}
