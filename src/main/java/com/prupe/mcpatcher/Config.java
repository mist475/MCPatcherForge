package com.prupe.mcpatcher;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.logging.Level;

public class Config {

    private static final Config instance = new Config();

    public static final String MCPATCHER_PROPERTIES = "mcpatcher.properties";

    LinkedHashMap<String, String> logging = new LinkedHashMap<>();
    LinkedHashMap<String, LinkedHashMap<String, String>> profile = new LinkedHashMap<>();

    public static Config getInstance() {
        return instance;
    }

    static Level getLogLevel(String category) {
        Level level = Level.INFO;
        String value = instance.logging.get(category);
        if (value != null) {
            try {
                level = Level.parse(
                    value.trim()
                        .toUpperCase());
            } catch (Throwable e) {}
        }
        setLogLevel(category, level);
        return level;
    }

    static void setLogLevel(String category, Level level) {
        instance.logging.put(
            category,
            level.toString()
                .toUpperCase());
    }

    /**
     * Gets a value from the config
     *
     * @param tag          property name
     * @param defaultValue default value if not found in profile
     * @return String value
     */
    public static String getString(String mod, String tag, Object defaultValue) {
        LinkedHashMap<String, String> modConfig = instance.getModConfig(mod);
        String value = modConfig.get(tag);
        if (value == null) {
            modConfig.put(tag, defaultValue.toString());
            return defaultValue.toString();
        } else {
            return value;
        }
    }

    /**
     * Gets a value from the config.
     *
     * @param mod          name of mod
     * @param tag          property name
     * @param defaultValue default value if not found in profile
     * @return int value or 0
     */
    public static int getInt(String mod, String tag, int defaultValue) {
        int value;
        try {
            value = Integer.parseInt(getString(mod, tag, defaultValue));
        } catch (NumberFormatException e) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * Gets a value from the config
     *
     * @param mod          name of mod
     * @param tag          property name
     * @param defaultValue default value if not found in profile
     * @return boolean value
     */
    public static boolean getBoolean(String mod, String tag, boolean defaultValue) {
        String value = getString(mod, tag, defaultValue).toLowerCase();
        if (value.equals("false")) {
            return false;
        } else if (value.equals("true")) {
            return true;
        } else {
            return defaultValue;
        }
    }

    /**
     * Sets a value in mcpatcher.json.
     *
     * @param mod   name of mod
     * @param tag   property name
     * @param value property value (must support toString())
     */
    public static void set(String mod, String tag, Object value) {
        if (value == null) {
            remove(mod, tag);
            return;
        }
        instance.getModConfig(mod)
            .put(tag, value.toString());
    }

    /**
     * Remove a value from mcpatcher.json.
     *
     * @param mod name of mod
     * @param tag property name
     */
    public static void remove(String mod, String tag) {
        instance.getModConfig(mod)
            .remove(tag);
    }

    public static File getOptionsTxt(File dir, String name) {
        File origFile = new File(dir, name);
        if (name.endsWith(".txt")) {
            String version = MCPatcherUtils.getMinecraftVersion();
            while (!MCPatcherUtils.isNullOrEmpty(version)) {
                File newFile = new File(dir, name.replace(".txt", "." + version + ".txt"));
                if (newFile.isFile()) {
                    System.out.printf("Using %s instead of %s\n", newFile.getName(), name);
                    return newFile;
                }
                int dot = version.lastIndexOf('.');
                if (dot > 0) {
                    version = version.substring(0, dot);
                } else if (version.matches("\\d+w\\d+[a-z]")) {
                    version = version.substring(0, version.length() - 1);
                } else {
                    break;
                }
            }
        }
        return origFile;
    }

    private LinkedHashMap<String, String> getModConfig(String mod) {
        return this.profile.computeIfAbsent(mod, k -> new LinkedHashMap<>());
    }

}
