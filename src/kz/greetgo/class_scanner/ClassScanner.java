package kz.greetgo.class_scanner;

import java.util.Set;

/**
 * Package class scanner
 *
 * @author pompei
 */
public interface ClassScanner {

  /**
   * Adds class loader for scanning in
   *
   * @param classLoader class loader
   */
  void addClassLoader(ClassLoader classLoader);

  /**
   * Scans package and returns set of scanned classes
   *
   * @param packageName scanning package name
   * @return a set of scanned classes
   */
  Set<Class<?>> scanPackage(String packageName);

}
