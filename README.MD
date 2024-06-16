# Getter for Java (Java Compiler plugin)

@Getter alternative  to [Lombok](https://github.com/projectlombok/lombok) for Java 17.


## Usage

Example module is available.

#### Before
```java
package it.unldenis.getter4j.example;
import it.unldenis.getter4j.Getter;

public class Person {

    private String name;
    
    @Getter
    private int age;
    
    @Getter
    private String address;
}
```

#### After
```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package it.unldenis.getter4j.example;
public class Person {
    private String name;
    private int age;
    private String address;

    public Person() {
    }

    public int getAge() {
        return this.age;
    }

    public String getAddress() {
        return this.address;
    }
}
```


## Installation

### Maven configuration

You need to add `-Xplugin:Getter` in compiler arguments

```xml
<dependencies>
    <dependency>
        <groupId>com.github.unldenis.getter4j</groupId>
        <artifactId>api</artifactId>
        <version>0.1</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.8.1</version>
        <configuration>
            <source>17</source>
            <target>17</target>
            <compilerArgs>
                <arg>-Xplugin:Getter</arg>
            </compilerArgs>
    </configuration>
</plugin>
```

For JDK 16 you need to set `<fork>true</fork>` and add additional args in <compilerArgs> tag. Please refer
to [JSR](https://openjdk.java.net/jeps/396)

```xml
<arg>-J--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED</arg>
<arg>-J--add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED</arg>
<arg>-J--add-exports=jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED</arg>
<arg>-J--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED</arg>
<arg>-J--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED</arg>
```

### IntelliJ Idea

You need to add `-Xplugin:Getter` in compiler arguments
