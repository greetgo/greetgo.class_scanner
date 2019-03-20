package kz.greetgo.class_scanner;

import com.metapossum.utils.scanner.PackageScanner;
import com.metapossum.utils.scanner.reflect.ClassesInPackageScanner;

import java.util.Set;

/**
 * Default scanner implementation
 *
 * @author pompei
 */
public class ClassScannerDef implements ClassScanner {

  @Override
  public Set<Class<?>> scanPackage(final String packageName) {
    return scanPackage(packageName, null);
  }

  @Override
  public Set<Class<?>> scanPackage(String packageName, ClassLoader classLoader) {

    if (packageName == null || packageName.length() == 0) {
      throw new IllegalArgumentException("packageName cannot be empty or null");
    }

    try {
      return getPackageScanner(classLoader).scan(packageName);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  private ClassesInPackageScanner getPackageScanner(ClassLoader classLoader) {
    if (classLoader != null) {
      return new ClassesInPackageScanner(classLoader, true);
    }
    return new ClassesInPackageScanner(PackageScanner.getDefaultClassLoader(), true);
  }
}
