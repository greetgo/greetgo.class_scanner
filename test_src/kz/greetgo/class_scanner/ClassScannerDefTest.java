package kz.greetgo.class_scanner;

import com.test.Hello;
import com.test.Hello2;
import com.test.hello.Hello3;
import com.test.hi.Hello1;
import org.testng.annotations.Test;

import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClassScannerDefTest {

  @Test
  public void scanPackage_myPackage() {
    ClassScannerDef classScanner = new ClassScannerDef();

    Set<Class<?>> classes = classScanner.scanPackage(ACoolClass.class.getPackage().getName());

    assertThat(classes).isNotEmpty();

    for (Class<?> aClass : classes) {
      System.out.println(aClass);
    }

    assertThat(classes).contains(ACoolClass.class);
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

  @Test
  public void scanCom() {

    ClassScannerDef classScanner = new ClassScannerDef();

    Set<Class<?>> classes = classScanner.scanPackage("com");

//    for (Class<?> aClass : classes) {
//      System.out.println("h2b4bh :: aClass = " + aClass);
//    }

    assertThat(classes).isNotEmpty();
    assertThat(classes).contains(Hello.class);
    assertThat(classes).contains(Hello2.class);
    assertThat(classes).contains(Hello1.class);
    assertThat(classes).contains(Hello3.class);

  }
}
