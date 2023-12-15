package com.blokdev.system.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.Properties;


@UtilityClass
public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (var resource = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
