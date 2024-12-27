# java知识点

1. Java 中的字符串常量池（String Pool）是一个优化机制，用于存储常量字符串。每当你创建一个字符串常量（例如，直接使用字面量 `"abcd"`），JVM 会检查常量池中是否已经存在该字符串。如果存在，它将返回对该字符串的引用；如果不存在，它会将这个字符串添加到常量池中

2. 当你使用 `new String("abcd")` 来创建字符串时，Java 会做以下两件事：

   1. **查找常量池**：首先，JVM 会查看常量池中是否已经存在字符串 `"abcd"`，如果不存在，它会将其加入常量池。
   2. **创建新的字符串对象**：无论常量池中是否已经存在 `"abcd"`，JVM 会在 **堆内存** 中为 `new String("abcd")` 创建一个新的 `String` 对象，并返回该对象的引用。

3. 在不改变s的引用的情况下改变s的值。解决方法：反射

   ```java
   public class Main {
   
       public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
           // You can add more test cases here
           String s=new String("abc");
           Field value = s.getClass().getDeclaredField("value");;
           value.setAccessible(true);
           value.set(s,"abcd".toCharArray());
           System.out.println(s);
       }
   }
   ```

4. intern() 的工作机制

      1. 当调用intern()时，JVM 会检查字符串常量池中是否已有该字符串。
      2. 如果常量池中已有相同内容的字符串，则返回池中已有字符串的引用。
      3. 如果常量池中没有相同内容的字符串，则将当前字符串添加到常量池，并返回它的引用。

5. 在Interger类中，存在一个静态内部类integerCache，该类中存在一个integer cache，并且存在一个static块，会在加载类的时候执行，会将-128至127这些数字提前生成Integer对象，并缓存在cache数组中，当我们在定义integer数字时，会调用Integer的value0f方法，value0f方法会判断所定义的数字是否在-128至127之间，如果存在则直接从cache数组中获取Integer对象，如果超过，则生成一个新的Integer对象。
```java
public static void main(String[]args){
    Integer i1 = 100;
    Integer i2 =100;// i1 == i2? true
	Integer i3 =128;
	Integer i4 =128;
	// i3 == i4? false
}
