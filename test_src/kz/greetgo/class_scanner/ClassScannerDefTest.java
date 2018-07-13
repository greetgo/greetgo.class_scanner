package kz.greetgo.class_scanner;

import org.testng.annotations.Test;

import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClassScannerDefTest {
  @Test
  public void scanPackage_myPackage() {
    ClassScannerDef classScanner = new ClassScannerDef();

    Set<Class<?>> classes = classScanner.scanPackage("kz.greetgo.class_scanner");

    assertThat(classes).isNotEmpty();
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void scanPackage_null() {
    ClassScannerDef classScanner = new ClassScannerDef();
    classScanner.scanPackage(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void scanPackage_empty() {
    ClassScannerDef classScanner = new ClassScannerDef();
    classScanner.scanPackage("");
  }
}
