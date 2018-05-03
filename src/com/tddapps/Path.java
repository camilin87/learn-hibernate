package com.tddapps;

import java.io.File;

public class Path {
    public static String combine(String... paths)
    {
        var file = new File(paths[0]);

        for (int i = 1; i < paths.length ; i++) {
            file = new File(file, paths[i]);
        }

        return file.getPath();
    }
}