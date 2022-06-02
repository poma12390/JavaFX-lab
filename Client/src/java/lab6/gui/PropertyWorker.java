package lab6.gui;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.PropertyResourceBundle;

public class PropertyWorker {

    private static PropertyResourceBundle engBundle;
    private static PropertyResourceBundle ruBundle;
    private static PropertyResourceBundle bundle;

    private static String language;

    public static PropertyResourceBundle getBundle() {
        return bundle;
    }

    private static PropertyResourceBundle getEngBundle() {
        return engBundle;
    }

    private static void setEngBundle(PropertyResourceBundle engBundle) {
        PropertyWorker.engBundle = engBundle;
    }

    private static void setBundle(PropertyResourceBundle curBundle) {
        PropertyWorker.bundle = curBundle;
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        PropertyWorker.language = language;
    }

    public static void setNewBundle(String language) {
        if (language.equals("ru")) {
            setBundle(getRuBundle());
            setLanguage("ru");
        } else if (language.equals("eng")) {
            setBundle(getEngBundle());
            setLanguage("eng");
        }
    }

    private static PropertyResourceBundle getRuBundle() {
        return ruBundle;
    }

    public static void setRuBundle(PropertyResourceBundle ruBundle) {
        PropertyWorker.ruBundle = ruBundle;
    }

    public static void init() {
        try {
            File file = new File("./src/java/lab6/gui/assets/gui.properties");
            InputStream inputStream = Files.newInputStream(file.toPath());
            PropertyResourceBundle bundle = new PropertyResourceBundle(inputStream);
            setEngBundle(bundle);

            file = new File("./src/java/lab6/gui/assets/gui_ru_RU.properties");
            inputStream = Files.newInputStream(file.toPath());
            bundle = new PropertyResourceBundle(inputStream);
            setRuBundle(bundle);
            setNewBundle("ru");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
