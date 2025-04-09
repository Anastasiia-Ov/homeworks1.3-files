import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String pathZip = "/Users/Games/savegames/progress.zip";
        String pathDir = "/Users/Games/savegames/";

        openZip(pathZip, pathDir);

        String pathFile = "/Users/Games/savegames/new packed_save1.dat";
        openProgress(pathFile);
    }

    public static void openZip(String pathZip, String pathDir) {
        try (ZipInputStream zip = new ZipInputStream(new FileInputStream(pathZip))) {
            ZipEntry entry;
            String name;

            while ((entry = zip.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(pathDir + "new " + name);
                for (int c = zip.read(); c != -1; c = zip.read()) {
                    fout.write(c);
                }
                fout.flush();
                zip.closeEntry();
                fout.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static public GameProgress openProgress(String pathFile) {
        GameProgress gameProgress = null;

        try (FileInputStream fis = new FileInputStream(pathFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(gameProgress);
        return gameProgress;
    }
}
