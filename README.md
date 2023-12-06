Unchecked Java
===============
Java Streams and lambda expressions that are free from checked exceptions side effects.

Overview
--------

[Functional Interfaces](https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html) provided in Java 8+ do not throw checked exceptions. As a consequence handling checked exceptions in lambda expressions can be overly cumbersome and verbose.

For example:

```java
Stream.of("https://www.google.com").map(URL::new);
```

Must be coded as:

```java
Stream.of("https://www.google.com").map(t -> {
    try {
        return new URL(t);
    } catch (final MalformedURLException e) {
        throw new RuntimeException(e);
    }
});
```

Besides the extra boiler-plate code, we are also required to obfuscate the original exception by wrapping it in a `RuntimeException` which adds unnecessary bloat to the stack trace. Using Unchecked Java we can again write concise code afforded to us by lambda expressions, without wrapping checked exceptions in runtime exceptions:

```java
import static software.leonov.common.util.function.CheckedFunction.evalUnchecked;
...
Stream.of("https://www.google.com").map(unchecked(URL::new));
```

**Please refer to the [Wiki](https://github.com/zleonov/unchecked-java/wiki) for details, specifications, API examples, and FAQ.**

Documentation
-------------
The latest API documentation can be accessed [here](https://zleonov.github.io/unchecked-java/api/latest).

WARNING
=======
Unchecked Java circumvents Java's exception handling mechanisms and can lead to horrible, often very hard to debug errors, when misused.

As Brian Goetz (Java Language Architect) put it:

> Just because you don’t like the rules, doesn’t mean its a good idea to take the law into your own hands. Your advice is irresponsible because it places the convenience of the code writer over the far more important considerations of transparency and maintainability of the program.

**Please refer to the [Safety Guide](https://github.com/zleonov/unchecked-java/wiki/Safety-Guide) before using Unchecked Java**.

Similar Libraries
-----------------
- [SneakyThrow](https://github.com/rainerhahnekamp/sneakythrow)
- [throwing-function](https://github.com/pivovarit/throwing-function)
- [jOOλ](https://github.com/jOOQ/jOOL) (org.jooq.lambda.Unchecked)
- [Project Lombok](https://projectlombok.org/features/SneakyThrows)
