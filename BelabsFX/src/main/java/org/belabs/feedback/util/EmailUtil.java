package org.belabs.feedback.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class EmailUtil {
    private EmailUtil() {
    }

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = EmailUtil.class.getClassLoader().getResourceAsStream("email.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("email.properties not found");
            }
            properties.load(inputStream);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load email.properties", exception);
        }
    }
}
