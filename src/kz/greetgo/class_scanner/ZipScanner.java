package kz.greetgo.class_scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipScanner {
  public static List<String> scanForEntries(File zipFile) {
    List<String> ret = new ArrayList<>();

    try (FileInputStream fileInputStream = new FileInputStream(zipFile)) {
      try (ZipInputStream zipInputStream = new ZipInputStream(fileInputStream)) {

        appendEntries(ret, zipInputStream);

      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return ret;
  }

  private static void appendEntries(List<String> result, ZipInputStream zipInputStream) throws IOException {

    while (true) {

      ZipEntry nextEntry = zipInputStream.getNextEntry();
      if (nextEntry == null) {
        return;
      }

      result.add(nextEntry.getName());

      zipInputStream.closeEntry();
    }


  }
}
