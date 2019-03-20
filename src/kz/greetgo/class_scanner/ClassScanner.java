package kz.greetgo.class_scanner;

import java.util.Set;

/**
 * Package class scanner
 *
 * @author pompei
 */
public interface ClassScanner {

  /**
   * Scans package and returns set of scanned classes
   *
   * @param packageName scanning package name
   * @return a set of scanned classes
   */
  Set<Class<?>> scanPackage(String packageName);


  /**
   * Scans package using specified class loader and returns set of scanned classes
   *
   * @param packageName scanning package name
   * @param classLoader class loader to scan classes
   * @return a set of scanned classes
   */
  Set<Class<?>> scanPackage(String packageName, ClassLoader classLoader);

}
