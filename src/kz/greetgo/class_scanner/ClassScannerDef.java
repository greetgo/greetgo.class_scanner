package kz.greetgo.class_scanner;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Default scanner implementation
 *
 * @author pompei
 */
public class ClassScannerDef implements ClassScanner {

  private final List<ClassLoader> classLoaderList = new ArrayList<>();

  {
    classLoaderList.add(ClassLoader.getSystemClassLoader());
  }

  @Override
  public void addClassLoader(ClassLoader classLoader) {
    if (classLoader == null) {
      return;
    }

    classLoaderList.add(classLoader);

  }

  @Override
  public Set<Class<?>> scanPackage(String packageName) {

    if (packageName == null) {
      throw new IllegalArgumentException("packageName == null");
    }
    if (packageName.length() == 0) {
      throw new IllegalArgumentException("packageName is empty");
    }

    return classLoaderList
      .stream()
      .flatMap(loader -> loaderToUrl(packageName, loader))
      .flatMap(ClassLoaderWithUrl::toClasses)
      .collect(Collectors.toSet())
      ;

  }

  private static Stream<ClassLoaderWithUrl> loaderToUrl(String packageName, ClassLoader loader) {
    try {
      ArrayList<URL> urls = Collections.list(loader.getResources(packageName.replace('.', '/')));

      return urls
        .stream()
        .map(url -> new ClassLoaderWithUrl(loader, url, packageName));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


}
