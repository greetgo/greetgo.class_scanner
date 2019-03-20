package kz.greetgo.class_scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScanFilesRecursively {
  public static List<File> scanRecursively(File dir) {
    List<File> result = new ArrayList<>();
    appendFiles(result, dir);
    return result;
  }

  private static void appendFiles(List<File> result, File dir) {
    File[] files = dir.listFiles();
    if (files == null) {
      return;
    }

    for (File file : files) {

      if (file.isDirectory()) {
        appendFiles(result, file);
        continue;
      }

      if (file.isFile()) {
        result.add(file);
        continue;
      }

    }

  }
}
