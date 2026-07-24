package support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Config {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                PROPS.load(in);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private Config() {
    }

    public static String baseUrl() {
        return PROPS.getProperty("base.url");
    }

    public static String username() {
        return extractCredField("username");
    }

    public static String password() {
        return extractCredField("password");
    }

    private static String extractCredField(String field) {
        String raw = System.getenv("MAESTRO_CREDS");
        if (raw == null || raw.isBlank()) {
            return null;
        }
        Matcher m = Pattern.compile("\"" + field + "\"\\s*:\\s*\"([^\"]*)\"").matcher(raw);
        return m.find() ? m.group(1) : null;
    }
}
