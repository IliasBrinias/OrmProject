package com.unipi.msc.ormproject.ORM.Tool;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    public static void dropDatabases() {
        // drop databases
        Arrays.stream(Objects.requireNonNull(new File(System.getProperty("user.dir")+"/Databases/").listFiles())).forEach(file -> {
            try {
                if (!file.exists()) return;
                if (file.isDirectory()) {
                    FileUtils.deleteDirectory(file);
                } else {
                    file.delete();
                }
            } catch (IOException ignore) {}
        });
    }
}
