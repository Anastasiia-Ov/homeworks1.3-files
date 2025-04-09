import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(99, 7, 3, 225);
        GameProgress gameProgress2 = new GameProgress(78, 9, 5, 310);
        GameProgress gameProgress3 = new GameProgress(80, 10, 6, 350);

        List<String> pathFile = Arrays.asList(
                "/Users/Games/savegames/save1.dat",
                "/Users/Games/savegames/save2.dat",
                "/Users/Games/savegames/save3.dat"
        );

        saveGame(gameProgress1, pathFile.get(0));
        saveGame(gameProgress2, pathFile.get(1));
        saveGame(gameProgress3, pathFile.get(2));

        String pathZip = "/Users/Games/savegames/progress.zip";
        zipFiles(pathZip, pathFile);

        for (String file : pathFile) {
            deleteFile(file);
        }
    }

    public static void saveGame(GameProgress gameProgress, String path) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String pathZip, List<String> pathFile) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathZip), StandardCharsets.UTF_8)) {

            for (int i = 0; i < pathFile.size(); i++) {
                String file = pathFile.get(i);

                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry("packed_save" + (i + 1) + ".dat");
                    zout.putNextEntry(entry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zout.write(buffer, 0, length);
                    }
                    zout.closeEntry();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }
}
