package kz.greetgo.class_scanner;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static kz.greetgo.class_scanner.ScanFilesRecursively.scanRecursively;

public class ClassLoaderWithUrl {

  public final ClassLoader classLoader;
  public final URL url;
  public final String packageName;

  ClassLoaderWithUrl(ClassLoader classLoader, URL url, String packageName) {
    this.classLoader = classLoader;
    this.url = url;
    this.packageName = packageName;
  }

  public Stream<Class<?>> toClasses() {
    switch (url.getProtocol()) {

      case "file":
        return fileToClasses();

      case "jar":
        return jarToClasses();

      default:
        throw new RuntimeException("Unknown protocol to scan files : " + url.getProtocol());

    }
  }

  private Stream<Class<?>> fileToClasses() {

    Path packagePath = Paths.get(String.join(File.separator, packageName.split("\\.")));

    Path baseDir = Paths.get(url.getFile());

//    System.out.println("h2b4hb25 :: FILE " + baseDir + " - packagePath - " + packagePath);

    List<File> fileList = scanRecursively(baseDir.toFile());

    List<Class<?>> ret = new ArrayList<>();

    for (File file : fileList) {

      String filePath = file.getPath();

      if (!filePath.toLowerCase().endsWith(".class")) {
        continue;
      }

      Path classPath = packagePath.resolve(baseDir.relativize(file.toPath()));

      String className = classPath.toString().replace(File.separatorChar, '.');
      className = className.substring(0, className.length() - ".class".length());

      try {
        ret.add(classLoader.loadClass(className));
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }

    }

    return ret.stream();
  }

  private Stream<Class<?>> jarToClasses() {

    String urlFile = url.getFile();

    if (!urlFile.startsWith("file:")) {
      throw new IllegalArgumentException("JAR-URL does not started from `file:` : " + urlFile);
    }

    String fileFullPath = urlFile.substring("file:".length());

    String jar = ".jar!";
    int jarIdx = fileFullPath.indexOf(jar);

    if (jarIdx < 0) {
      throw new IllegalArgumentException("JAR-URL does not contain `" + jar + "`");
    }

    File jarFile = new File(fileFullPath.substring(0, jarIdx) + ".jar");
    String pathInFile = fileFullPath.substring(jarIdx + jar.length());
    if (pathInFile.startsWith("/")) {
      pathInFile = pathInFile.substring(1);
    }
    if (!pathInFile.endsWith("/")) {
      pathInFile = pathInFile + "/";
    }

    if (!jarFile.exists()) {
      throw new RuntimeException("No file " + jarFile);
    }

    List<String> entities = ZipScanner.scanForEntries(jarFile);

    List<Class<?>> classes = new ArrayList<>();

    for (String entity : entities) {

      if (!entity.endsWith(".class")) {
        continue;
      }

      if (!entity.startsWith(pathInFile)) {
        continue;
      }

      String className = entity.substring(0, entity.length() - ".class".length()).replace('/', '.');

      try {
        classes.add(classLoader.loadClass(className));
      } catch (ClassNotFoundException | NoClassDefFoundError ignore) {
        //ignore unloading class
      }

    }


    return classes.stream();
  }
}
