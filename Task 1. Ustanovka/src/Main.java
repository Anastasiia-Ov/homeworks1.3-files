import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    static StringBuilder logStr = new StringBuilder();

    public static void main(String[] args) {
        List<String> pathDir = Arrays.asList(
                "/Users/Games",
                "/Users/Games/src",
                "/Users/Games/res",
                "/Users/Games/savegames",
                "/Users/Games/temp",
                "/Users/Games/src/main",
                "/Users/Games/src/test",
                "/Users/Games/res/drawables",
                "/Users/Games/res/vectors",
                "/Users/Games/res/icons"
        );

        log("Запуск создания директорий");
        for (int i = 0; i < pathDir.size(); i++) {
            String path = pathDir.get(i);
            createDirectory(path);
        }

        List<String> pathFile = Arrays.asList(
                "/Users/Games/src/main/Main.java",
                "/Users/Games/src/main/Utils.java",
                "/Users/Games/temp/temp.txt"
        );

        log("Запуск создания файлов");
        for (int i = 0; i < pathFile.size(); i++) {
            String path = pathFile.get(i);
            createFile(path);
        }

        try (FileWriter writer = new FileWriter("/Users/Games/temp/temp.txt", false)) {
            writer.write(logStr.toString());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static boolean createDirectory(String path) {
        File dir = new File(path);

        if (!dir.exists()) {
            if (dir.mkdir()) {
                log(path + " - Каталог создан");
                System.out.println("Каталог создан");
                return true;
            } else {
                log(path + " - Каталог НЕ создан");
                System.out.println("Каталог НЕ создан");
            }
        } else {
            log(path + " - Директория существует, повторно не создана");
            System.out.println("Директория существует");
        }

        return false;
    }

    public static boolean createFile(String path) {
        File file = new File(path);

        if (file.exists()) {
            log(path + " - Файл существует, повторно не создан");
            System.out.println("Файл существует");
            return false;
        }

        try {
            if (file.createNewFile()) {
                log(path + " - Файл был создан");
                System.out.println("Файл был создан");
                return true;
            } else {
                log(path + " - Файл НЕ был создан");
                System.out.println("Файл НЕ был создан");
            }
        } catch (IOException e) {
            log(path + " - Ошибка: " + e.getMessage());
            System.out.println(e.getMessage());
        }

        return false;
    }

    public static void log(String msg) {
        logStr.append(LocalDateTime.now() + " INFO: " + msg + "\n");
    }
}
